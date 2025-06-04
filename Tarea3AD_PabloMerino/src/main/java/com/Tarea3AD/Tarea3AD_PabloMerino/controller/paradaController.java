package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Carnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.ConjuntoContratado;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Estancia;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.PereParada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Servicio;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Sesion;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.CarnetService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.EstanciaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.ParadaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.PereParadaService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.PeregrinoService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.UserService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.db4oService;
import com.Tarea3AD.Tarea3AD_PabloMerino.utils.copy.ContadorIdConjunto;
import com.Tarea3AD.Tarea3AD_PabloMerino.vistas.FxmlView;
import com.db4o.ObjectSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

@Controller
public class paradaController implements Initializable {

	@FXML
	private Button btnCerrarSesion;

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
	private PeregrinoService pereService;
	@Autowired
	private CarnetService carnetService;
	@Autowired
	private ParadaService paradasService;
	@Autowired
	private PereParadaService pereParadaService;
	@Autowired
	private EstanciaService estanciaService;
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
		alertaInfo("INFO", "Se ha alojado a: "+peregrino.getNombrePeregrino());
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

//	public void cargarServicios() {
//		Usuario usuario = sesion.getUsuIniciado();
//
//		Parada parada = paradasService.findByIdUsuario(usuario.getId());
//
//		List<Servicio> serviciosParada = db4oService.listarServicios().stream()
//				.filter(servicio -> servicio.getIdParadas().contains(parada.getId())).collect(Collectors.toList());
//		List<String> nombresServicios = new ArrayList<>();
//		for (Servicio s : serviciosParada) {
//			nombresServicios.add(s.getNombreServicio());
//		}
//		cmbxServicios.setItems(FXCollections.observableArrayList(nombresServicios));
//	}

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

}
