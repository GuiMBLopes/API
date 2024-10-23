package br.org.serratec.FinalAPI.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.org.serratec.FinalAPI.domain.Post;

public class PostDTO {

	private Long id;
	private String conteudo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCriacao;
	
	private NomeUsuarioDTO nomeUsuarioDTO;

	public PostDTO() {

	}

	public PostDTO(Long id, String conteudo, LocalDate dataCriacao) {
		super();
		this.id = id;
		this.conteudo = conteudo;
		this.dataCriacao = dataCriacao;
	}

	public PostDTO(Post post) {
		this.id = post.getId();
		this.conteudo = post.getConteudo();
		this.dataCriacao = post.getDataCriacao();
		this.nomeUsuarioDTO = new NomeUsuarioDTO(post.getUsuario());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public NomeUsuarioDTO getPostUsuarioDTO() {
		return nomeUsuarioDTO;
	}

	public void setPostUsuarioDTO(NomeUsuarioDTO nomeUsuarioDTO) {
		this.nomeUsuarioDTO = nomeUsuarioDTO;
	}

}
