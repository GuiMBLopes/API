package br.org.serratec.FinalAPI.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.org.serratec.FinalAPI.domain.Comentario;

public class ComentarioDTO {
	
	private Long id;
	private String texto;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCriacao;
	
	private NomeUsuarioDTO nomeUsuarioDTO;
	private PostDTO postDTO;
	
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
		this.nomeUsuarioDTO = new NomeUsuarioDTO(comentario.getUsuario());
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

	public NomeUsuarioDTO getNomeUsuarioDTO() {
		return nomeUsuarioDTO;
	}

	public void setNomeUsuarioDTO(NomeUsuarioDTO nomeUsuarioDTO) {
		this.nomeUsuarioDTO = nomeUsuarioDTO;
	}

	public PostDTO getPostDTO() {
		return postDTO;
	}

	public void setPostDTO(PostDTO postDTO) {
		this.postDTO = postDTO;
	}

}
