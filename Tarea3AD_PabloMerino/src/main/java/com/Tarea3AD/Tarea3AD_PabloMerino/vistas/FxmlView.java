package com.Tarea3AD.Tarea3AD_PabloMerino.vistas;

import java.util.ResourceBundle;

public enum FxmlView {
	ADMIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/principalAdmin.fxml";
		}

	},

	PEREGRINO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("pere.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/indexPeregrinoMODIFICADO.fxml";
		}
	},

	PARADA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("parada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/exportarParada.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/index.fxml";
		}

	},

	REGISTRO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("registro.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/registrarPeregrino.fxml";
		}

	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
