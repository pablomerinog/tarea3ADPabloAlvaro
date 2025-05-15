package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Sesion;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.UserService;
import com.Tarea3AD.Tarea3AD_PabloMerino.vistas.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class IndexController implements Initializable {

	@FXML
	private Button btnLogin;

	@FXML
	private PasswordField contrasenaField;

	@FXML
	private TextField usuarioField;

	@FXML
	private Label lblLogin;

	@Autowired
	private UserService userService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Sesion sesion;

	@Autowired
	private Environment env;

	@FXML
	private void login(ActionEvent event) throws IOException {

		String adminUser = env.getProperty("usuarioadmin");
		String adminPass = env.getProperty("passwadmin");

		if (getUsername().equals(adminUser) && getContrasena().equals(adminPass)) {
			// alertaInfo("Has iniciado sesión como ADMIN", "Bienvenido: " + getUsername() +
			// "!");
			stageManager.switchScene(FxmlView.ADMIN);
			return;
		}

		if (userService.authenticate(getUsername(), getContrasena())) {

			Usuario usuario = userService.findByNombreUsuarioAndContrasena(getUsername(), getContrasena());
			sesion.setUsuIniciado(usuario);
			// alertaInfo("Has iniciado sesion como: " +
			// usuario.getnombreUsuario(),"Bienvenido: " + usuario.getnombreUsuario() +
			// "!");

			switch (usuario.getPerfil().toUpperCase()) {
			case "ADMIN" -> stageManager.switchScene(FxmlView.ADMIN);
			case "PEREGRINO" -> stageManager.switchScene(FxmlView.PEREGRINO);
			case "PARADA" -> stageManager.switchScene(FxmlView.PARADA);
			}

		} else {
			alertaError("Error al iniciar sesión", "Usuario o contraseña incorrectos");
		}
	}

	public String getContrasena() {
		return contrasenaField.getText();
	}

	public String getUsername() {
		return usuarioField.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
	private void registro(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.REGISTRO);
	}

}
