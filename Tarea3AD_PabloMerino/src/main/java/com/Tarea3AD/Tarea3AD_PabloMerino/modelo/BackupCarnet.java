package com.Tarea3AD.Tarea3AD_PabloMerino.modelo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document(collection = "backups")
public class BackupCarnet {
	@Id
	private String id;
	private String nombreFichero;
	private List<CarnetBackupJson> carnets;

	public BackupCarnet() {
		this.nombreFichero = "backupscarnet";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public List<CarnetBackupJson> getCarnets() {
		return carnets;
	}

	public void setCarnets(List<CarnetBackupJson> carnets) {
		this.carnets = carnets;
	}

	public static class CarnetBackupJson {

		private String id;
		private Long idPeregrino;
		private String nombre;
		private String nacionalidad;
		private LocalDate fechaExp;
		private String expedidoEn;
		private LocalDate hoy;
		private double distancia;
		private List<ParadasJson> paradas;
		private List<EstanciasJson> estancias;
		private LocalDate backupFecha;

		public CarnetBackupJson() {
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Long getIdPeregrino() {
			return idPeregrino;
		}

		public void setIdPeregrino(Long idPeregrino) {
			this.idPeregrino = idPeregrino;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getNacionalidad() {
			return nacionalidad;
		}

		public void setNacionalidad(String nacionalidad) {
			this.nacionalidad = nacionalidad;
		}

		public LocalDate getFechaExp() {
			return fechaExp;
		}

		public void setFechaExp(LocalDate fechaExp) {
			this.fechaExp = fechaExp;
		}

		public String getExpedidoEn() {
			return expedidoEn;
		}

		public void setExpedidoEn(String expedidoEn) {
			this.expedidoEn = expedidoEn;
		}

		public LocalDate getHoy() {
			return hoy;
		}

		public void setHoy(LocalDate hoy) {
			this.hoy = hoy;
		}

		public double getDistancia() {
			return distancia;
		}

		public void setDistancia(double distancia) {
			this.distancia = distancia;
		}

		public List<ParadasJson> getParadas() {
			return paradas;
		}

		public void setParadas(List<ParadasJson> paradas) {
			this.paradas = paradas;
		}

		public List<EstanciasJson> getEstancias() {
			return estancias;
		}

		public void setEstancias(List<EstanciasJson> estancias) {
			this.estancias = estancias;
		}

		public LocalDate getBackupFecha() {
			return backupFecha;
		}

		public void setBackupFecha(LocalDate backupFecha) {
			this.backupFecha = backupFecha;
		}

		public static class ParadasJson {
			private int orden;
			private String nombre;
			private String region;

			public ParadasJson() {
			}

			public int getOrden() {
				return orden;
			}

			public void setOrden(int orden) {
				this.orden = orden;
			}

			public String getNombre() {
				return nombre;
			}

			public void setNombre(String nombre) {
				this.nombre = nombre;
			}

			public String getRegion() {
				return region;
			}

			public void setRegion(String region) {
				this.region = region;
			}
		}

		public static class EstanciasJson {
			private Long id;
			private LocalDate fechaEstancia;
			private String parada;
			private boolean vip;

			public EstanciasJson() {
			}

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public LocalDate getFechaEstancia() {
				return fechaEstancia;
			}

			public void setFechaEstancia(LocalDate fechaEstancia) {
				this.fechaEstancia = fechaEstancia;
			}

			public String getParada() {
				return parada;
			}

			public void setParada(String parada) {
				this.parada = parada;
			}

			public boolean isVip() {
				return vip;
			}

			public void setVip(boolean vip) {
				this.vip = vip;
			}
		}
	}
}