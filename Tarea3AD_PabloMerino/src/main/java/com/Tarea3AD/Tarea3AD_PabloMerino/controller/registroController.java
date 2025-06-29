package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Carnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Estancia;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.PereParada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.CarnetService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.ExistDBService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.ParadaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.PereParadaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.PeregrinoService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.UserService;
import com.Tarea3AD.Tarea3AD_PabloMerino.vistas.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class registroController implements Initializable {

	@FXML
	private Button btnRegistrar;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField nombreCompleto;

	@FXML
	private TextField usuario;

	@FXML
	private PasswordField contrasena1;

	@FXML
	private PasswordField contrasena2;

	@FXML
	private ComboBox<String> comboNacionalidad;

	@FXML
	private ComboBox<String> comboParadas;

	@Autowired
	private UserService userService;

	@Autowired
	private CarnetService carnetService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private PeregrinoService peregrinoService;
	@Autowired
	private PereParadaService pereParadaService;
	@Autowired
	private peregrinoController peregrinoController;
	
	@Autowired
	private ExistDBService existDBService;
	
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> nacionalidades = cargarNacionalidadesDesdeXML();
		
		comboNacionalidad.setItems(nacionalidades);
		cargarParadas();
	}

	public void alertaInfo(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public void alertaError(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	private void logout(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void registrar(ActionEvent event) throws IOException {

		String paradaInicialNombre = comboParadas.getValue();

		String[] partes = paradaInicialNombre.split(",");

		String nombre = partes[0];
		char region = partes[1].charAt(0);

		Parada paradaInicial = paradaService.findByNombreAndRegion(nombre, region);

		boolean valido = false;

		String nombreUsuario = getNombreUsuario();
		String contrasena = getContrasena1();

		String nombreCompletoTexto = getNombreCompleto();
		String nacionalidad = comboNacionalidad.getValue();

		if (nombreCompletoTexto.isBlank() || nombreUsuario.isBlank() || contrasena.isBlank() || nacionalidad == null
				|| paradaInicialNombre == null) {
			alertaError("Campos incompletos", "Por favor, rellena todos los campos.");
			return;
		}

		if (nombreUsuario.contains(" ") || contrasena.contains(" ")) {
			alertaError("Datos inválidos", "El nombre de usuario y la contraseña no deben contener espacios.");
			return;
		}

		Usuario usuarioExistente = userService.findByNombreUsuario(nombreUsuario);
		if (usuarioExistente != null) {
			alertaError("Usuario ya existe", "El nombre de usuario ya está en uso. Elige otro.");
			return;
		}

		Usuario usuario = new Usuario();
		usuario.setnombreUsuario(nombreUsuario);
		usuario.setContrasena(contrasena);
		usuario.setPerfil("PEREGRINO");

		Carnet carnet = new Carnet();
		carnet.setDistancia(0.0);
		carnet.setFechaexp(LocalDate.now());
		carnet.setNvips(0);
		carnet.setParadaIncial(paradaInicial);

		Peregrino peregrino = new Peregrino();
		peregrino.setNombrePeregrino(getNombreCompleto());
		peregrino.setNacionalidad(nacionalidad);
		peregrino.setCarnet(carnet);
		peregrino.setUsuario(usuario);
		
		
		peregrinoService.save(peregrino);
		
		PereParada pereparada = new PereParada();
		pereparada.setPeregrino(peregrino);
		pereparada.setParada(paradaInicial);
		pereparada.setFecha(LocalDate.now());

		pereParadaService.save(pereparada);
		Set<PereParada> pp = new HashSet<PereParada>();
		pp.add(pereparada);
		
		peregrino.setPereParadas(pp);
		Peregrino nuevoPeregrino = peregrinoService.save(peregrino);
		
		peregrinoController.exportarCarnet2(peregrino);
		
		File ficheroCarnet = new File("carnets/" + peregrino.getNombrePeregrino() + ".xml");
		
		existDBService.guardarCarnetExistDB(paradaInicialNombre, ficheroCarnet);
		
		limpiarCampos();
		alertaInfo("Registro correcto", "El peregrino se ha registrado con éxito.");

		stageManager.switchScene(FxmlView.LOGIN);

	}

	public String getNombreUsuario() {
		return usuario.getText();
	}

	public String getNombreCompleto() {
		return nombreCompleto.getText();
	}

	public String getContrasena1() {
		return contrasena1.getText();
	}

	public String getContrasena2() {
		return contrasena2.getText();
	}

	@FXML
	private void limpiarCampos() {
		nombreCompleto.clear();
		usuario.clear();
		contrasena1.clear();
		contrasena2.clear();
	}

	public ObservableList<String> cargarNacionalidadesDesdeXML() {
		ObservableList<String> nacionalidades = FXCollections.observableArrayList();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(getClass().getResourceAsStream("/paises.xml"));

			NodeList paises = document.getElementsByTagName("pais");

			for (int i = 0; i < paises.getLength(); i++) {
				Node pais = paises.item(i);

				if (pais.getNodeType() == Node.ELEMENT_NODE) {
					Element paisElement = (Element) pais;

					String nombrePais = paisElement.getElementsByTagName("nombre").item(0).getTextContent();

					nacionalidades.add(nombrePais);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nacionalidades;
	}

	private void cargarParadas() {

		List<Parada> paradas = paradaService.findAll();
		List<String> nombresParadas = new ArrayList<>();
		List<String> regionesParadas = new ArrayList<>();
		for (Parada p : paradas) {
			nombresParadas.add(p.getNombre() + "," + Character.toString(p.getRegion()));

		}
		comboParadas.setItems(FXCollections.observableArrayList(nombresParadas));

	}
}
