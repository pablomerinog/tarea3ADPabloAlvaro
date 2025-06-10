package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.ExistDBConfig;
import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ConjuntoContratado;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ParadaFX;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ServicioFX;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.MongoDBService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.ParadaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.UserService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.db4oService;
import com.Tarea3AD.Tarea3AD_PabloMerino.utils.copy.ContadorID;
import com.Tarea3AD.Tarea3AD_PabloMerino.vistas.FxmlView;
import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

@Controller
public class adminController implements Initializable {

	@FXML
	private Button btnCerrarSesion;
	
	@FXML
	private Button btnBackup;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnAñadir;

	@FXML
	private Button btnAsignarServicios;

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
	private TableView<ServicioFX> tablaServicios;

	@FXML
	private TableColumn<ServicioFX, Long> colId;

	@FXML
	private TableColumn<ServicioFX, String> colNombre;

	@FXML
	private TableColumn<ServicioFX, Double> colPrecio;

	@FXML
	private TableColumn<ServicioFX, Void> colEditar;

	@FXML
	private TableColumn<ServicioFX, Boolean> colSeleccionar;

	@FXML
	private TableView<ParadaFX> tablaParadas;

	@FXML
	private TableColumn<ParadaFX, Long> colIdParada;

	@FXML
	private TableColumn<ParadaFX, String> colNombreParada;

	@FXML
	private TableColumn<ParadaFX, String> colRegion;

	@FXML
	private TableColumn<ParadaFX, Boolean> colCheck;

	@Autowired
	private UserService userService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private MongoDBService mongoDBService;
	
	@Autowired
	private db4oService db4oService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	private ContadorID contador;

	private static ObjectContainer db;

	private ObservableList<ServicioFX> serviciosList = FXCollections.observableArrayList();
	private ObservableList<ParadaFX> paradasList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
		colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		colSeleccionar.setCellValueFactory(cellData -> cellData.getValue().seleccionadoProperty());
		colSeleccionar.setCellFactory(CheckBoxTableCell.forTableColumn(colSeleccionar));
		colEditar.setCellFactory(param -> new TableCell<>() {
			private final Button btn = new Button("Editar");

			{
				btn.setOnAction(event -> {
					ServicioFX servicioFX = getTableView().getItems().get(getIndex());
					editarServicio(servicioFX);
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(btn);
				}
			}
		});

		colIdParada.setCellValueFactory(new PropertyValueFactory<>("idParada"));
		colNombreParada.setCellValueFactory(new PropertyValueFactory<>("nombreParada"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));

		colCheck.setCellValueFactory(cellData -> cellData.getValue().seleccionadoProperty());

		colCheck.setCellFactory(tc -> new CheckBoxTableCell<>());

		colCheck.setCellFactory(tc -> {
			CheckBoxTableCell<ParadaFX, Boolean> cell = new CheckBoxTableCell<>();
			cell.setSelectedStateCallback(index -> {
				ParadaFX parada = tablaParadas.getItems().get(index);
				return parada.seleccionadoProperty();
			});
			return cell;
		});

		cargarServicios();
		cargarParadas();
		tablaServicios.setEditable(true);
		colSeleccionar.setEditable(true);
		tablaParadas.setEditable(true);
		colCheck.setEditable(true);
		mostrarDb4o();


	}

	private void cargarParadas() {
		List<Parada> paradas = paradaService.findAll();
		paradasList.clear();

		for (Parada p : paradas) {
			ParadaFX paradaFX = new ParadaFX(p);

			paradaFX.seleccionadoProperty().addListener((obs, wasSelected, isNowSelected) -> {
				actualizarServiciosSegunParadasSeleccionadas();
			});

			paradasList.add(paradaFX);
		}

		tablaParadas.setItems(paradasList);
	}

	private List<Parada> getParadasSeleccionadas() {
		return paradasList.stream().filter(ParadaFX::isSeleccionado).map(ParadaFX::getParada)
				.collect(Collectors.toList());
	}

	private void actualizarServiciosSegunParadasSeleccionadas() {
		List<Long> idsParadasSeleccionadas = getParadasSeleccionadas().stream().map(Parada::getId)
				.collect(Collectors.toList());

		for (ServicioFX servicioFX : serviciosList) {
			Servicio servicio = servicioFX.getServicio();
			List<Long> idsParadasDelServicio = servicio.getIdParadas();

			boolean tieneAlguna = idsParadasDelServicio.stream().anyMatch(idsParadasSeleccionadas::contains);

			servicioFX.setSeleccionado(tieneAlguna);
		}

		tablaServicios.refresh();
	}

	private void editarServicio(ServicioFX servicioFX) {
		Servicio servicio = servicioFX.getServicio();

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Editar Servicio");
		dialog.setHeaderText("Editar nombre y precio del servicio");

		ButtonType botonGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
		ButtonType botonEliminar = new ButtonType("Eliminar", ButtonBar.ButtonData.LEFT);
		dialog.getDialogPane().getButtonTypes().addAll(botonGuardar, botonEliminar, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField nombreField = new TextField(servicio.getNombreServicio());
		TextField precioField = new TextField(String.valueOf(servicio.getPrecio()));

		grid.add(new Label("Nombre:"), 0, 0);
		grid.add(nombreField, 1, 0);
		grid.add(new Label("Precio:"), 0, 1);
		grid.add(precioField, 1, 1);

		dialog.getDialogPane().setContent(grid);

		Button botonEliminarControl = (Button) dialog.getDialogPane().lookupButton(botonEliminar);
		botonEliminarControl.addEventFilter(ActionEvent.ACTION, event -> {

			Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
			confirmacion.setTitle("Confirmar eliminación");
			confirmacion.setHeaderText("¿Estás seguro de que quieres eliminar este servicio?");
			confirmacion.setContentText(servicio.getNombreServicio());

			Optional<ButtonType> respuesta = confirmacion.showAndWait();
			if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
				eliminarServicio(servicio);
				tablaServicios.getItems().remove(servicioFX);
				tablaServicios.refresh();
				dialog.close();
			}
			event.consume();
		});

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == botonGuardar) {
				return new Pair<>(nombreField.getText(), precioField.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> resultado = dialog.showAndWait();

		resultado.ifPresent(nombrePrecio -> {
			String nuevoNombre = nombrePrecio.getKey().trim();
			String nuevoPrecioStr = nombrePrecio.getValue().trim();

			if (!nuevoNombre.isEmpty() && !nuevoPrecioStr.isEmpty()) {
				try {
					double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);

					servicio.setNombreServicio(nuevoNombre);
					servicio.setPrecio(nuevoPrecio);

					actualizarServicio(servicio);

					tablaServicios.refresh();

				} catch (NumberFormatException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Precio inválido");
					alert.setContentText("Por favor, introduce un número válido para el precio.");
					alert.showAndWait();
				}
			}
		});
	}

	private void actualizarServicio(Servicio servicioUpdate) {
		db4oService.actualizarServicio(servicioUpdate);
	}

	private void eliminarServicio(Servicio servicio) {
		db4oService.eliminarServicio(servicio);
	}

	private void cargarServicios() {
		List<Servicio> listaServicios = db4oService.listarServicios();

		serviciosList.clear();

		for (Servicio s : listaServicios) {
			serviciosList.add(new ServicioFX(s));
		}

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
		
		String nombreFinalParada = nuevaParada.getNombre()+","+nuevaParada.getRegion(); 
		
		ExistDBConfig.getInstance().crearColeccionParada(nombreFinalParada);
		
		limpiarCampos();
		alertaInfo("Registro correcto", "La parada se ha registrado con éxito.");

		paradasList.add(new ParadaFX(nuevaParada));

		tablaParadas.refresh();
	}

	@FXML
	private void registrarServicio(ActionEvent event) throws IOException {

		String nombreServicio = getNombreServicio();

		Double precio = getPrecioServicio();

		if (nombreServicio == null || nombreServicio.isBlank() || precio == null) {
			alertaError("Datos inválidos", "Debes completar correctamente todos los campos del servicio.");
			return;
		}

		Long idNuevo = db4oService.getNuevoId();

		Servicio nuevoServicio = new Servicio(idNuevo, nombreServicio, precio);
		db4oService.guardarServicio(nuevoServicio);

		serviciosList.add(new ServicioFX(nuevoServicio));

		limpiarCamposServicio();
	}

	private void limpiarCamposServicio() {
		nombreServicio.clear();
		precioServicio.clear();
	}

	@FXML
	private void asignarServicios(ActionEvent event) {
		List<Servicio> serviciosSeleccionados = obtenerServiciosSeleccionados();
		List<Parada> paradasSeleccionadas = obtenerParadasSeleccionadas();

		if (paradasSeleccionadas == null || paradasSeleccionadas.isEmpty()) {
			alertaError("Error", "Debes seleccionar al menos una parada.");
			return;
		}

		for (Parada parada : paradasSeleccionadas) {
			Long idParada = parada.getId();

			List<Servicio> serviciosAntiguos = db4oService.listarServiciosPorIdParada(idParada);

			for (Servicio servicioAntiguo : serviciosAntiguos) {
				if (!serviciosSeleccionados.contains(servicioAntiguo)) {
					Servicio servicioBD = db4oService.buscarServicioPorId(servicioAntiguo.getIdServicio());
					if (servicioBD != null && servicioBD.getIdParadas() != null) {
						List<Long> idParadas = new ArrayList<>(servicioBD.getIdParadas());
						idParadas.remove(idParada);
						servicioBD.setIdParadas(idParadas);
						db4oService.guardarServicio(servicioBD);
					}
				}
			}

			for (Servicio servicioSeleccionado : serviciosSeleccionados) {
				Servicio servicioBD = db4oService.buscarServicioPorId(servicioSeleccionado.getIdServicio());
				if (servicioBD != null) {
					List<Long> idParadas = servicioBD.getIdParadas() != null
							? new ArrayList<>(servicioBD.getIdParadas())
							: new ArrayList<>();
					if (!idParadas.contains(idParada)) {
						idParadas.add(idParada);
						servicioBD.setIdParadas(idParadas);
						db4oService.guardarServicio(servicioBD);
					}
				}
			}
		}

		alertaInfo("Asignación actualizada", "Las asociaciones se han actualizado correctamente.");

		serviciosList.forEach(s -> s.setSeleccionado(false));
		paradasList.forEach(p -> p.setSeleccionado(false));
		tablaServicios.refresh();
		tablaParadas.refresh();
	}

	

	private List<Servicio> obtenerServiciosSeleccionados() {
		List<Servicio> seleccionados = new ArrayList<>();
		for (ServicioFX servicioFX : serviciosList) {
			if (servicioFX.isSeleccionado()) {
				seleccionados.add(servicioFX.getServicio());
			}
		}
		return seleccionados;
	}

	private List<Parada> obtenerParadasSeleccionadas() {
		List<Parada> seleccionadas = new ArrayList<>();
		for (ParadaFX paradaFX : paradasList) {
			if (paradaFX.isSeleccionado()) {
				seleccionadas.add(paradaFX.getParada());
			}
		}
		return seleccionadas;
	}

	public ObservableList<Servicio> getServiciosSeleccionados() {
		ObservableList<Servicio> seleccionados = FXCollections.observableArrayList();
		for (ServicioFX servicioFX : serviciosList) {
			if (servicioFX.isSeleccionado()) {
				seleccionados.add(servicioFX.getServicio());
			}
		}
		return seleccionados;
	}

	public void mostrarDb4o() {
		List<Servicio> listaServicios = db4oService.listarServicios();

		List<ConjuntoContratado> listaConjuntos = db4oService.listarConjuntos();

		System.out.println("====== LISTADO DE SERVICIOS ======");
		for (Servicio s : listaServicios) {
			System.out.println("Id: " + s.getIdServicio());
			System.out.println("Nombre: " + s.getNombreServicio());
			System.out.println("Precio: " + s.getPrecio());
			System.out.println("Paradas: " + s.getIdParadas());
			System.out.println("Conjuntos: " + s.getConjuntoContratado());
			System.out.println("----------------------------------");
		}

		System.out.println("\n====== LISTADO DE CONJUNTOS CONTRATADOS ======");
		for (ConjuntoContratado c : listaConjuntos) {
			System.out.println("ID: " + c.getId());
			System.out.println("Precio: " + c.getPrecioTotal());
			System.out.println("Modo de pago: " + c.getModoPago());
			System.out.println("Extra: " + c.getExtra());
			System.out.println("Servicios: " + c.getServicios());
			System.out.println("----------------------------------");

		}
	}
	
	@FXML
	public void backup() {
		if(mongoDBService.backupAllCarnets())
			alertaInfo("Backup", "Backup correcto de los carnets.");
			
		else
			alertaError("Backup", "Error al hacer el backup.");
	}
}
