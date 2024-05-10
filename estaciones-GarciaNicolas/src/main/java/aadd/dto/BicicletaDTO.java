package aadd.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BicicletaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String modelo;
	private LocalDate fechaAlta;
	private LocalDate fechaBaja;
	private String motivo;

	private List<IncidenciaDTO> incidencias;

	public BicicletaDTO(String id, String modelo, LocalDate fechaAlta, LocalDate fechaBaja, String motivo) {
		this.id = id;
		this.modelo = modelo;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
		this.motivo = motivo;
		this.incidencias = new ArrayList<IncidenciaDTO>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public List<IncidenciaDTO> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<IncidenciaDTO> incidencias) {
		this.incidencias = incidencias;
	}

	public void addIncidencia(IncidenciaDTO incidenciaDTO) {

		this.incidencias.add(incidenciaDTO);
	}

}
