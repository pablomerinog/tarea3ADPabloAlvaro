package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.ObjectDBConfig;
import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Carnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ConjuntoContratado;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Direccion;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.EnvioACasa;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Estancia;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.PereParada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Sesion;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.CarnetService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.EnvioACasaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.EstanciaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.ExistDBService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.ParadaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.PereParadaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.PeregrinoService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.UserService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.db4oService;
import com.Tarea3AD.Tarea3AD_PabloMerino.vistas.FxmlView;

import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Controller
public class paradaController implements Initializable {

	@FXML
	private Button btnListarCarnets;

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Button btnVerPedidos;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnAlojar;

	@FXML
	private Button btnBuscar;

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
	private TableColumn<Parada, Void> colContrasena;

	@FXML
	private TableView<Peregrino> tablaPeregrinos;

	@FXML
	private TableColumn<Peregrino, Long> colIdPere;

	@FXML
	private TableColumn<Peregrino, String> colNombrePere;

	@FXML
	private TableColumn<Peregrino, String> colNacionalidadPere;
	@FXML
	private TableColumn<Carnet, String> colCarnetPere;
	@FXML
	private TableColumn<Usuario, String> colUsuarioPere;

	@FXML
	private TableView<EnvioACasa> tablaEnvios;

	@FXML
	private TableColumn<EnvioACasa, Long> colIdEnvio;

	@FXML
	private TableColumn<EnvioACasa, Double> colPeso;

	@FXML
	private TableColumn<EnvioACasa, String> colVolumen;
	@FXML
	private TableColumn<EnvioACasa, Boolean> colUrgente;
	@FXML
	private TableColumn<EnvioACasa, String> colDireccion;

	@FXML
	private DatePicker fechaIni;

	@FXML
	private DatePicker fechaFin;

	@FXML
	private CheckBox checkVIP;

	@FXML
	private CheckBox checkAlojar;

	@FXML
	private ComboBox<String> cmbxPeregrinos;

	@FXML
	private ComboBox<String> cmbxModoPago;

	@FXML
	private TextField extra;

	@FXML
	private ListView<String> listaServicios;

	@Autowired
	private UserService userService;
	@Autowired
	private EnvioACasaService envioService;

	@Autowired
	private PeregrinoService pereService;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private ParadaService paradasService;
	@Autowired
	private PereParadaService pereParadaService;
	@Autowired
	private EstanciaService estanciaService;
	@Autowired
	private ExistDBService existDBService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private db4oService db4oService;

	private ObservableList<Parada> paradaList = FXCollections.observableArrayList();

	@Autowired
	private Sesion sesion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colResp.setCellValueFactory(new PropertyValueFactory<>("responsable"));
		colUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));

		cargarPeregrinos();
		cargarParadas();
		cargarServicios();

		checkVIP.setDisable(true);
		listaServicios.setDisable(true);
		cmbxModoPago.setDisable(true);
		extra.setDisable(true);
		checkAlojar.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			checkVIP.setDisable(!isNowSelected);
			listaServicios.setDisable(!isNowSelected);
			cmbxModoPago.setDisable(!isNowSelected);
			extra.setDisable(!isNowSelected);
			if (!isNowSelected) {
				checkVIP.setSelected(false);
				listaServicios.getSelectionModel().clearSelection();
				cmbxModoPago.getSelectionModel().clearSelection();
				extra.getText();
			}
		});

		cmbxModoPago.setItems(FXCollections.observableArrayList("E - Efectivo", "T - Tarjeta", "B - Bizum"));
		cmbxModoPago.getSelectionModel().selectFirst();

		listaServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		listaServicios.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) change -> {
			ObservableList<String> seleccionados = listaServicios.getSelectionModel().getSelectedItems();
			boolean ventanaEnvioAbierta = false;
			while (change.next()) {
				if (change.wasAdded()) {
					for (String servicio : change.getAddedSubList()) {

						if (seleccionados.contains("envío a casa") && !ventanaEnvioAbierta) {
							ventanaEnvioAbierta = true;
							mostrarVentanaEnvioCasa();
							ventanaEnvioAbierta = false;
						}
					}
				}
			}
		});

		colIdEnvio.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

		colVolumen.setCellValueFactory(cellData -> {
			int[] vol = cellData.getValue().getVolumen();
			String volString = Arrays.toString(vol);
			return new SimpleStringProperty(volString);
		});

		colUrgente.setCellValueFactory(new PropertyValueFactory<>("urgente"));
		colDireccion.setCellValueFactory(cellData -> {
			Direccion dir = cellData.getValue().getDireccion();
			String direccionCompleta = dir.getDireccion() + ", " + dir.getLocalidad();
			return new SimpleStringProperty(direccionCompleta);
		});
		verEnviosParada();
	}

	public void cargarParadas() {
		paradaList.clear();
		paradaList.addAll(paradasService.findAll());
		tablaParadas.setItems(paradaList);

	}

	private void cargarPeregrinos() {
		List<Peregrino> peregrinos = pereService.findAll();
		List<String> nombresPeregrinos = new ArrayList<>();
		for (Peregrino p : peregrinos) {
			nombresPeregrinos.add(p.getNombrePeregrino());
		}
		cmbxPeregrinos.setItems(FXCollections.observableArrayList(nombresPeregrinos));
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
	private void listarCarnetsParada(ActionEvent event) throws IOException {
		Usuario usuario = sesion.getUsuIniciado();
		Parada parada = paradasService.findByIdUsuario(usuario.getId());

		if (parada == null) {
			alertaError("Error", "No se encontró la parada asociada al usuario.");
			return;
		}

		String nombreParada = parada.getNombre() + "," + parada.getRegion();

		List<String> carnets = existDBService.listarCarnetsPorParada(nombreParada);

		if (carnets == null || carnets.isEmpty()) {
			alertaInfo("Información", "No hay carnets expedidos en esta parada.");
			return;
		}

		System.out.println(carnets);

		ListView<String> listaCarnetsView = new ListView<>();
		listaCarnetsView.setItems(FXCollections.observableArrayList(carnets));

		VBox vbox = new VBox(listaCarnetsView);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(5);

		Scene scene = new Scene(vbox, 400, 300);
		Stage ventanaCarnets = new Stage();
		ventanaCarnets.setTitle("Carnets expedidos en " + parada.getNombre());
		ventanaCarnets.setScene(scene);
		ventanaCarnets.initModality(Modality.APPLICATION_MODAL);
		ventanaCarnets.showAndWait();

	}

	@FXML
	private void sellarParada(ActionEvent event) throws IOException {
		boolean isVip = false;

		Usuario usuario = sesion.getUsuIniciado();

		System.out.println("Usuario: " + usuario.getnombreUsuario() + usuario.getId());

		Parada parada = paradasService.findByIdUsuario(usuario.getId());

		String pere = cmbxPeregrinos.getValue();
		Optional<Peregrino> peregrinoOpt = pereService.findBynombrePeregrino(pere);
		if (peregrinoOpt.isEmpty()) {
			System.out.println("No se encuentran peregrinos");
			return;
		}
		Peregrino peregrino = peregrinoOpt.get();

		PereParada pereParada = new PereParada();
		pereParada.setParada(parada);
		pereParada.setPeregrino(peregrino);
		pereParada.setFecha(LocalDate.now());
		pereParadaService.save(pereParada);

		Carnet carnetPere = peregrino.getCarnet();
		double distanciaCarnet = carnetPere.getDistancia();

		Carnet carnet = carnetService.find(carnetPere.getId());
		carnet.setDistancia(distanciaCarnet + 10);

		if (checkAlojar.isSelected()) {

			Estancia estancia = new Estancia();
			estancia.setParada(parada);
			estancia.setPeregrino(peregrino);
			estancia.setFecha(LocalDate.now());

			if (checkVIP.isSelected()) {
				isVip = true;
				carnet.setNvips(carnetPere.getNvips() + 1);
				estancia.setVip(isVip);
			} else {
				isVip = false;
				carnet.setNvips(carnetPere.getNvips());
				estancia.setVip(isVip);
			}

			estanciaService.save(estancia);

			ObservableList<String> serviciosSeleccionados = listaServicios.getSelectionModel().getSelectedItems();

			if (!serviciosSeleccionados.isEmpty()) {
				ConjuntoContratado conjunto = new ConjuntoContratado();

				List<Servicio> serviciosContratados = db4oService.listarServicios().stream()
						.filter(s -> serviciosSeleccionados.contains(s.getNombreServicio()))
						.collect(Collectors.toList());

				List<Long> idsServicios = serviciosContratados.stream().map(Servicio::getIdServicio)
						.collect(Collectors.toList());

				conjunto.setServicios(idsServicios);

				char modoPago = cmbxModoPago.getValue().charAt(0);
				conjunto.setModoPago(modoPago);

				String infoExtra = extra.getText();
				if (infoExtra != null && !infoExtra.isBlank()) {
					conjunto.setExtra(infoExtra);
				}

				double precioTotal = serviciosContratados.stream().mapToDouble(Servicio::getPrecio).sum();
				conjunto.setPrecioTotal(precioTotal);

				Long idNuevo = db4oService.getNuevoIdConjunto();
				conjunto.setId(idNuevo);

				db4oService.guardarConjunto(conjunto);

				for (Servicio s : serviciosContratados) {
					if (s.getConjuntoContratado() == null) {
						s.setConjuntoContratado(new ArrayList<>());
					}
					s.getConjuntoContratado().add(idNuevo);
					db4oService.actualizarServicio(s);
				}

				System.out.println(conjunto);
			}

		}

		carnetService.update(carnet);
		System.out.println("Se ha sellado el carnet");
		alertaInfo("INFO", "Se ha alojado a: " + peregrino.getNombrePeregrino());
	}

	@FXML
	private void exportarDatosParada(ActionEvent event) {
		Usuario usuario = sesion.getUsuIniciado();

		Parada parada = paradasService.findByIdUsuario(usuario.getId());

		if (fechaIni.getValue() == null || fechaFin.getValue() == null) {
			alertaError("Error", "Debe seleccionar un rango de fechas válido.");
			return;
		}

		if (fechaFin.getValue().isBefore(fechaIni.getValue())) {
			alertaError("Error", "La fecha fin no puede ser anterior a la fecha inicio.");
			return;
		}

		List<Estancia> estancias = estanciaService.findByParadaAndFechaBetween(parada, fechaIni.getValue(),
				fechaFin.getValue());

		if (estancias.isEmpty()) {
			alertaInfo("Información", "No hay estancias en el rango de fechas seleccionado.");
			return;
		}

		StringBuilder contenido = new StringBuilder();
		contenido.append("Datos de la Parada\n");
		contenido.append("ID: ").append(parada.getId()).append("\n");
		contenido.append("Nombre: ").append(parada.getNombre()).append("\n");
		contenido.append("Región: ").append(parada.getRegion()).append("\n\n");

		contenido.append("Rango de fechas: ").append(fechaIni.getValue()).append(" a ").append(fechaFin.getValue())
				.append("\n\n");

		contenido.append("Estancias:\n");
		contenido.append("ID Estancia, Nombre Peregrino, Fecha, VIP\n");

		for (Estancia estancia : estancias) {
			contenido.append(estancia.getId()).append(", ");
			contenido.append(estancia.getPeregrino().getNombrePeregrino()).append(", ");
			contenido.append(estancia.getFecha()).append(", ");
			contenido.append(estancia.isVip() ? "Sí" : "No").append("\n");
		}

		try {
			Path carpeta = Path.of("datosParadas");
			if (!Files.exists(carpeta)) {
				Files.createDirectories(carpeta);
			}

			String nombreArchivo = "export_parada_" + parada.getNombre() + " " + parada.getRegion() + ".txt";
			Path rutaArchivo = carpeta.resolve(nombreArchivo);

			Files.writeString(rutaArchivo, contenido.toString());

			alertaInfo("Exportación correcta", "Archivo exportado en: " + rutaArchivo.toAbsolutePath());

		} catch (Exception e) {
			alertaError("Error", "No se pudo exportar el archivo: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void cargarServicios() {
		Usuario usuario = sesion.getUsuIniciado();
		Parada parada = paradasService.findByIdUsuario(usuario.getId());

		List<Servicio> serviciosParada = db4oService.listarServicios().stream()
				.filter(servicio -> servicio.getIdParadas().contains(parada.getId())).collect(Collectors.toList());

		List<String> nombresServicios = serviciosParada.stream().map(Servicio::getNombreServicio)
				.collect(Collectors.toList());

		ObservableList<String> items = FXCollections.observableArrayList(nombresServicios);

		listaServicios.setItems(items);
		listaServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	private void mostrarVentanaEnvioCasa() {
		Usuario usuario = sesion.getUsuIniciado();
		Parada parada = paradasService.findByIdUsuario(usuario.getId());

		Stage stage = new Stage();
		stage.setTitle("Datos de Envío a Casa");

		TextField tfDireccion = new TextField();
		tfDireccion.setPromptText("Dirección completa");

		TextField tfLocalidad = new TextField();
		tfLocalidad.setPromptText("Localidad");

		TextField tfPeso = new TextField();
		tfPeso.setPromptText("Peso (kg)");

		TextField tfDimensiones = new TextField();
		tfDimensiones.setPromptText("Dimensiones (largo x ancho x alto)");

		CheckBox cbUrgente = new CheckBox("Envío urgente");
		cbUrgente.setSelected(false);

		Button btnEnviar = new Button("Enviar");

		btnEnviar.setOnAction(e -> {

			String direccion = tfDireccion.getText();
			String localidad = tfLocalidad.getText();
			String peso = tfPeso.getText();

			if (direccion.isEmpty() || localidad.isEmpty() || direccion.isEmpty() || peso.isEmpty()) {
				alertaError("ERROR", "Tienes que rellenar todos los campos");
				return;
			}

			if (!peso.matches("\\d+(\\.\\d+)?")) {
				alertaError("Error", "El peso debe ser un número válido");
				return;
			}

			double pesoBn = Double.parseDouble(peso);
			String dimensiones = tfDimensiones.getText();

			if (!dimensiones.matches("\\d+\\s*x\\s*\\d+\\s*x\\s*\\d+")) {
				alertaError("Error", "Las dimensiones deben tener el formato 'largo x ancho x alto' con números.");

				return;
			}

			String[] partes = dimensiones.split("x");
			boolean urgente = cbUrgente.isSelected();
			int[] arrayDimensiones = new int[3];

			Direccion dire = new Direccion(direccion, localidad);

			EnvioACasa envio = new EnvioACasa();

			envio.setDireccion(dire);
			envio.setUrgente(urgente);
			envio.setPeso(pesoBn);
			envio.setIdParada(parada.getId());
			if (partes.length == 3) {
				try {
					for (int i = 0; i < 3; i++) {
						arrayDimensiones[i] = Integer.parseInt(partes[i].trim());
					}

					envio.setVolumen(arrayDimensiones);

				} catch (NumberFormatException ex) {
					System.out.println("Error: uno de los valores no es un número válido.");
				}
			} else {
				System.out.println("Error: el formato debe ser 'número x número x número' (ej: 3x6x4)");
			}

			if (direccion.isEmpty() || localidad.isEmpty() || peso.isEmpty() || dimensiones.isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, rellena todos los campos.");
				alert.showAndWait();
				return;
			}

			EntityManager em = ObjectDBConfig.getEntityManager();
			try {
				em.getTransaction().begin();
				envioService.saveEnvio(envio);
				em.getTransaction().commit();

			} catch (Exception ex) {
				em.getTransaction().rollback();
			} finally {
				em.close();
			}

			System.out.println("Dirección: " + direccion);
			System.out.println("Localidad: " + localidad);
			System.out.println("Peso: " + peso);
			System.out.println("Dimensiones: " + dimensiones);
			System.out.println("Urgente: " + urgente);

			stage.close();

		});

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(15));
		layout.getChildren().addAll(new Label("Dirección:"), tfDireccion, new Label("Localidad:"), tfLocalidad,
				new Label("Peso (kg):"), tfPeso, new Label("Dimensiones:"), tfDimensiones, cbUrgente, btnEnviar);

		Scene scene = new Scene(layout, 350, 400);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

	}

//	@FXML
	private void verEnviosParada() {
		Usuario usuario = sesion.getUsuIniciado();
		Parada parada = paradasService.findByIdUsuario(usuario.getId());

		List<EnvioACasa> envios = envioService.findByIdParada(parada.getId());
		for (EnvioACasa envio : envios) {
			System.out.println("ID: " + envio.getId());
			System.out.println("Peso: " + envio.getPeso());
			System.out.println("Volumen: " + Arrays.toString(envio.getVolumen()));
			System.out.println("Urgente: " + envio.isUrgente());

			Direccion d = envio.getDireccion();
			System.out.println("Dirección: " + d.getDireccion() + ", " + d.getLocalidad() + ". ");
			System.out.println("-----");
		}

		ObservableList<EnvioACasa> envio = FXCollections.observableArrayList(envios);
		tablaEnvios.setItems(envio);

	}

}
