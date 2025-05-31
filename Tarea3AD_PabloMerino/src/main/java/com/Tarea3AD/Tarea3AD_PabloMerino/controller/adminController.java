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
	private Button btnAñadir;

	@FXML
	private Button cancelarServicio;

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
	private TextField nombreServicio;

	@FXML
	private TextField precioServicio;

	@FXML
	private TextField idServicio;

	@FXML
	private TableView<Servicio> tablaServicios;

	@FXML
	private TableColumn<Servicio, Long> colId;

	@FXML
	private TableColumn<Servicio, String> colNombre;

	@FXML
	private TableColumn<Servicio, Double> colPrecio;

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

	private ObservableList<Servicio> serviciosList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
		colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

		cargarServicios();
	}

	private void cargarServicios() {
		serviciosList.clear();

		serviciosList.addAll(db4oService.listarServicios());

		tablaServicios.setItems(serviciosList);
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

	public Long getIdServicio() {
		String id = idServicio.getText();
		if (id != null && !id.isEmpty()) {
			try {
				return Long.parseLong(id);
			} catch (NumberFormatException e) {
				System.out.println("Error: el ID no es un número válido.");
				return null;
			}
		}
		return null;
	}

	public Double getPrecioServicio() {
		String precioStr = precioServicio.getText();
		if (precioStr != null && !precioStr.isEmpty()) {
			try {
				return Double.parseDouble(precioStr);
			} catch (NumberFormatException e) {
				System.out.println("Error: el precio no es un número válido.");
				return null;
			}
		}
		return null;
	}

	public String getNombreServicio() {
		return nombreServicio.getText();
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

	}

	@FXML
	private void registrarServicio(ActionEvent event) throws IOException {
//falta añadir el servicio a una parada
		String nombreServicio = getNombreServicio();
		Long id = getIdServicio();
		Double precio = getPrecioServicio();

		Servicio nuevoServicio = new Servicio(id, nombreServicio, precio);
		db4oService.guardarServicio(nuevoServicio);
		
		serviciosList.add(nuevoServicio);
		
	}
}
