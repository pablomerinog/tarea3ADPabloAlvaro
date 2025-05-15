package com.Tarea3AD.Tarea3AD_PabloMerino.controller;

import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

import com.Tarea3AD.Tarea3AD_PabloMerino.config.StageManager;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Estancia;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Parada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.PereParada;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Peregrino;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Sesion;
import com.Tarea3AD.Tarea3AD_PabloMerino.modelo.Usuario;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.PeregrinoService;
import com.Tarea3AD.Tarea3AD_PabloMerino.services.UserService;
import com.Tarea3AD.Tarea3AD_PabloMerino.vistas.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
public class peregrinoController implements Initializable {

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private Button btnExportar;

	@FXML
	private TextField nombreCompleto;

	@FXML
	private TextField usuario;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtFecha;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtNacionalidad;
	@FXML
	private TextField txtHoy;
	@FXML
	private TextField txtDistancia;

	@Autowired
	private UserService userService;

	@Autowired
	private PeregrinoService pereService;
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Sesion sesion;

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
	private void logout(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private boolean exportarCarnet(ActionEvent event) throws IOException {
		Usuario usuario = sesion.getUsuIniciado();
//		String nombrePeregrino= usuario.getnombreUsuario();
//		Long idPeregrino = usuario.getId();
//		System.out.println(idPeregrino);
//		System.out.println(nombrePeregrino);
		Optional<Peregrino> peregrinoOpt = pereService.findByUsuario(usuario);
//		Optional<Peregrino> peregrinoOpt = pereService.findBynombrePeregrino(nombrePeregrino);
//		System.out.println("Peregrino"+peregrinoOpt.get());

		if (!peregrinoOpt.isPresent()) {
			alertaError("ERROR", "No se ha encontrado el peregrino.");
			return false;
		}
		Peregrino peregrino = peregrinoOpt.get();
		boolean correcto = false;
		try {

			DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
			DOMImplementation implementacion = constructorDocumento.getDOMImplementation();
			Document documento = implementacion.createDocument(null, "carnet", null);
			Element carnet = documento.getDocumentElement();
			documento.setXmlVersion("1.0");

			ProcessingInstruction ip = documento.createProcessingInstruction("xml-stylesheet",
					"type=\"text/xml\" href=\"test.xsl\"");
			documento.insertBefore(ip, carnet);

			Element id = documento.createElement("id");
			id.appendChild(documento.createTextNode(String.valueOf(peregrino.getId())));
			carnet.appendChild(id);

			Element fechaexp = documento.createElement("fechaexp");
			LocalDate fechaExpLocalDate = peregrino.getCarnet().getFechaexp();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String fechaExp = fechaExpLocalDate.format(formatter);
			fechaexp.appendChild(documento.createTextNode(fechaExp));
			carnet.appendChild(fechaexp);

			Element expedidoen = documento.createElement("expedidoen");
			expedidoen.appendChild(documento.createTextNode(peregrino.getCarnet().getParadaIncial().getNombre()));
			carnet.appendChild(expedidoen);

			Element peregrinoElement = documento.createElement("peregrino");
			Element nombre = documento.createElement("nombre");
			nombre.appendChild(documento.createTextNode(peregrino.getNombrePeregrino()));
			peregrinoElement.appendChild(nombre);

			Element nacionalidad = documento.createElement("nacionalidad");
			nacionalidad.appendChild(documento.createTextNode(peregrino.getNacionalidad()));
			peregrinoElement.appendChild(nacionalidad);
			carnet.appendChild(peregrinoElement);

			Element hoy = documento.createElement("hoy");
			LocalDate fechaHoyLocalDate = LocalDate.now();
			String fechaHoy = fechaHoyLocalDate.format(formatter);
			hoy.appendChild(documento.createTextNode(fechaHoy));
			carnet.appendChild(hoy);

			Element distanciatotal = documento.createElement("distanciatotal");
			distanciatotal
					.appendChild(documento.createTextNode(String.format("%.1f", peregrino.getCarnet().getDistancia())));
			carnet.appendChild(distanciatotal);

			Element paradas = documento.createElement("paradas");
			int ordenParada = 1;

			Set<PereParada> cjtoparadas = peregrino.getPereParadas();
			for (PereParada pp : cjtoparadas) {
				Parada parada = pp.getParada();
				Element paradaElement = documento.createElement("parada");

				Element orden = documento.createElement("orden");
				orden.appendChild(documento.createTextNode("" + ordenParada));/// String.valueOf(parada.getId())));
				paradaElement.appendChild(orden);

				Element nombreParada = documento.createElement("nombre");
				nombreParada.appendChild(documento.createTextNode(parada.getNombre()));
				paradaElement.appendChild(nombreParada);

				Element region = documento.createElement("region");
				region.appendChild(documento.createTextNode(Character.toString(parada.getRegion())));
				paradaElement.appendChild(region);

				paradas.appendChild(paradaElement);
				ordenParada++;
			}
			carnet.appendChild(paradas);

			Element estancias = documento.createElement("estancias");
			for (Estancia estancia : peregrino.getEstancias()) {

				Element estanciaElement = documento.createElement("estancia");

				Element idEstancia = documento.createElement("id");
				idEstancia.appendChild(documento.createTextNode(String.valueOf(estancia.getId())));
				estanciaElement.appendChild(idEstancia);

				Element fechaEstancia = documento.createElement("fecha");
				fechaEstancia.appendChild(documento.createTextNode(estancia.getFecha().format(formatter)));
				estanciaElement.appendChild(fechaEstancia);

				Element paradaEstancia = documento.createElement("parada");
				paradaEstancia.appendChild(documento.createTextNode(estancia.getParada().getNombre()));
				estanciaElement.appendChild(paradaEstancia);

				Element vip = documento.createElement("vip");
				vip.appendChild(documento.createTextNode(estancia.isVip() ? "true" : "false"));
				estanciaElement.appendChild(vip);

				estancias.appendChild(estanciaElement);
			}
			carnet.appendChild(estancias);

			File directorio = new File("carnets");
			if (!directorio.exists()) {
				directorio.mkdirs();
			}
			Source fuente = new DOMSource(documento);
			File fichero = new File("carnets/" + peregrino.getNombrePeregrino() + ".xml");
			Result resultado = new StreamResult(fichero);
			TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
			Transformer transformador = fabricaTransformador.newTransformer();
			transformador.transform(fuente, resultado);

			System.out.println("Se ha exportado el carnet en XML.");

			correcto = true;
		} catch (Exception ex) {
			System.out.println("Error al generar el archivo XML: " + ex.getMessage());
			ex.printStackTrace();
		}
		return correcto;
	}

}
