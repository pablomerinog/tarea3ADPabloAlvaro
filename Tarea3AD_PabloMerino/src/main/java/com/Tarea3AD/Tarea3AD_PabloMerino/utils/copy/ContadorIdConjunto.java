package com.Tarea3AD.Tarea3AD_PabloMerino.utils.copy;

public class ContadorIdConjunto {

	private long ultimoId;

	public ContadorIdConjunto() {
		this.ultimoId = 0L;
	}

	public long getSiguienteId() {
		return ++ultimoId;
	}

	public long getUltimoId() {
		return ultimoId;
	}

	public void setUltimoId(long ultimoId) {
		this.ultimoId = ultimoId;
	}
}
