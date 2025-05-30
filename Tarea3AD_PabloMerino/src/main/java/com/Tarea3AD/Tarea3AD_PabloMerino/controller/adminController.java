package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ConjuntoContratado;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.ParadaService;

import com.Tarea3AD.Tarea3AD_PabloMerino.services.UserService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.db4oService;
import com.Tarea3AD.Tarea3AD_PabloMerino.vistas.FxmlView;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import com.db4o.Db4oEmbedded;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class adminController implements Initializable {

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnRegistrar;

	@FXML
	private TextField nombreParada;

	@FXML
	private TextField regionParada;

	@FXML
	private TextField usuarioParada;

	@FXML
	private TextField nombreResp;

	@FXML
	private TextField contrasenaParada;

	@FXML
	private TableView<Parada> tablaParadas;

	@FXML
	private TableColumn<Parada, Long> colId;

	@FXML
	private TableColumn<Parada, String> colNombre;

	@FXML
	private TableColumn<Parada, String> colRegion;
	@FXML
	private TableColumn<Parada, Long> colResp;
	@FXML
	private TableColumn<Usuario, String> colUsuario;

	@FXML
	private TableColumn<Parada, Void> colEditar;

	@Autowired
	private UserService userService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private db4oService db4oService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	private ObservableList<Parada> paradasList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colResp.setCellValueFactory(new PropertyValueFactory<>("responsable"));
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));

		cargarParadas();
	}

	private void cargarParadas() {
		paradasList.clear();

		paradasList.addAll(paradaService.findAll());

		tablaParadas.setItems(paradasList);
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
	private void limpiarCampos() {
		nombreParada.clear();
		regionParada.clear();
		usuarioParada.clear();
		contrasenaParada.clear();
		nombreResp.clear();
	}

	public String getNombreParada() {
		return nombreParada.getText();
	}

	public char getRegion() {
		String texto = regionParada.getText();
		if (texto != null && !texto.isEmpty()) {
			return texto.charAt(0);
		} else {
			return '\0';
		}
	}

	public String getNombreUsuario() {
		return usuarioParada.getText();
	}

	public String getResponsable() {
		return nombreResp.getText();
	}

	public String getContrasena() {
		return contrasenaParada.getText();
	}

	@FXML
	private void registrarParada(ActionEvent event) throws IOException {

		String nombreUsuario = getNombreUsuario();
		String contrasena = getContrasena();
		String nombreParada = getNombreParada();
		char region = getRegion();
		String responsable = getResponsable();

		if (nombreUsuario.contains(" ") || contrasena.contains(" ")) {
			alertaError("Datos inválidos", "El nombre de usuario y la contraseña no deben contener espacios.");
			return;
		}

		Usuario usuarioExistente = userService.findByNombreUsuario(nombreUsuario);
		if (usuarioExistente != null) {
			alertaError("Usuario ya existe", "El nombre de usuario ya está en uso. Elige otro.");
			return;
		}

		Parada paradaExistente = paradaService.findByNombreAndRegion(nombreParada, region);
		if (paradaExistente != null) {
			alertaError("La parada ya existe", "Ya hay una parada con este nombre. Elige otro.");
			return;
		}

		if (nombreUsuario.isBlank() || contrasena.isBlank() || nombreParada == null || region == ' '
				|| responsable == null) {
			alertaError("Campos incompletos", "Por favor, rellena todos los campos.");
			return;
		}

		Usuario usuario = new Usuario();
		usuario.setnombreUsuario(getNombreUsuario());
		usuario.setContrasena(getContrasena());
		usuario.setPerfil("PARADA");
		Usuario nuevoUsuario = userService.save(usuario);

		Parada parada = new Parada();
		parada.setNombre(getNombreParada());
		parada.setRegion(getRegion());
		parada.setResponsable(getResponsable());
		parada.setIdUsuario(nuevoUsuario.getId());

		Parada nuevaParada = paradaService.save(parada);

		limpiarCampos();
		alertaInfo("Registro correcto", "La parada se ha registrado con éxito.");

		paradasList.add(nuevaParada);
	//*************************************//
///////////PRUEBA DE DB4O
		// Crear y guardo el servicio
		Servicio nuevoServicio = new Servicio(1L, "Limpieza", 25.50);
		db4oService.guardarServicio(nuevoServicio);
		
		//Creo y guardo el cc
		ConjuntoContratado cc = new ConjuntoContratado(1L, 3.5, 'B',null);
		db4oService.guardarConjunto(cc);
		
		// Buscarlo de nuevo para asegurarnos que está guardado
		Servicio servicioGuardado = db4oService.buscarServicioPorId(nuevoServicio.getIdServicio());
		// Mostrar por consola
		if (servicioGuardado != null) {
			System.out.println("Servicio guardado:");
			System.out.println("ID: " + servicioGuardado.getIdServicio());
			System.out.println("Nombre: " + servicioGuardado.getNombreServicio());
			System.out.println("Precio: " + servicioGuardado.getPrecio());
		} else {
			System.out.println("No se encontró el servicio después de guardarlo.");
		}
		
		// Buscarlo de nuevo para asegurarnos que está guardado
			ConjuntoContratado ccc = db4oService.buscarConjuntoPorId(cc.getId());
				// Mostrar por consola
				if (servicioGuardado != null) {
					System.out.println("Servicio guardado:");
					System.out.println("ID: " + ccc.getId());
					System.out.println("Precio: " + ccc.getPrecioTotal());
					System.out.println("Modo de pago: " + ccc.getModoPago());
					System.out.println("Extra: " + ccc.getExtra());
				} else {
					System.out.println("No se encontró el servicio después de guardarlo.");
				}

	}

}
