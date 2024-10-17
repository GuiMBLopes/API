package br.org.serratec.FinalAPI.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class RelationshipPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "id_usuario" )
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_seguidor")
	private Usuario seguidor;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getSeguidor() {
		return seguidor;
	}

	public void setSeguidor(Usuario seguidor) {
		this.seguidor = seguidor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(seguidor, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelationshipPK other = (RelationshipPK) obj;
		return Objects.equals(seguidor, other.seguidor) && Objects.equals(usuario, other.usuario);
	}
	
	
	
}
