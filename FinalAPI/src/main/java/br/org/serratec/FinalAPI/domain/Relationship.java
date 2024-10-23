package br.org.serratec.FinalAPI.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Relationship {

	@EmbeddedId
	private RelationshipPK id = new RelationshipPK();

	@Column(name = "data_inicio_seguimento")
	private LocalDate dataInicioSeguimento;

	public Relationship() {
	}

	public Relationship(Usuario usuario, Usuario seguidor, LocalDate dataInicioSeguimento) {
		this.id.setUsuario(usuario);
		this.id.setSeguidor(seguidor);
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

	public RelationshipPK getId() {
		return id;
	}

	public void setId(RelationshipPK id) {
		this.id = id;
	}

	public LocalDate getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}

	public void setDataInicioSeguimento(LocalDate dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

}
