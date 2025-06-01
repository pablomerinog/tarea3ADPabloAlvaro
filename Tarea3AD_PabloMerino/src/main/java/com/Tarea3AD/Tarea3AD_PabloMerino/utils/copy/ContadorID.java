package com.Tarea3AD.Tarea3AD_PabloMerino.utils.copy;

public class ContadorID {
	private long ultimoId;

	public ContadorID() {
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