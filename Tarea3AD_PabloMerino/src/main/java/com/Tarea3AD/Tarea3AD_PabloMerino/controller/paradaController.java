package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Carnet;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Sesion;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.CarnetService;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private ComboBox<String> cmbxPeregrinos;

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
	@Lazy
	@Autowired
	private StageManager stageManager;

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
	}

	public void cargarParadas() {
		paradaList.clear();
		paradaList.addAll(paradasService.findAll());
		tablaParadas.setItems(paradaList);

	}

	private void cargarPeregrinos() {
		List<Peregrino> peregrinos= pereService.findAll();
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

		Usuario usuario = sesion.getUsuIniciado();

		System.out.println("Usuario: " + usuario.getnombreUsuario());

		Optional<Parada> paradaOpt = paradasService.findByNombre(usuario.getnombreUsuario());
		if (paradaOpt.isEmpty()) {
			System.out.println("El responsable no tiene paradas " + usuario.getnombreUsuario());
			return;
		}

		String pere = cmbxPeregrinos.getValue();
		Optional<Peregrino> paradaInicialOpt = pereService.findBynombrePeregrino(pere);
		
		boolean isVip = false;

		if (checkVIP.isSelected()) {
			isVip = true;
		} else {
			isVip = false;
		}

	}

}
