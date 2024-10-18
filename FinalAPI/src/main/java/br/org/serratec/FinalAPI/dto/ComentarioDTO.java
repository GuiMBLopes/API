package br.org.serratec.FinalAPI.dto;

import java.time.LocalDate;

import br.org.serratec.FinalAPI.domain.Comentario;

public class ComentarioDTO {
	
	private Long id;
	private String texto;
	private LocalDate dataCriacao;
	
	public ComentarioDTO() {
		
	}

	public ComentarioDTO(Long id, String texto, LocalDate dataCriacao) {
		super();
		this.id = id;
		this.texto = texto;
		this.dataCriacao = dataCriacao;
	}
	
	public ComentarioDTO (Comentario comentario) {
		this.id = comentario.getId();
		this.texto = comentario.getTexto();
		this.dataCriacao = comentario.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
}
