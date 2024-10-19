package br.org.serratec.FinalAPI.dto;

import java.time.LocalDate;

import br.org.serratec.FinalAPI.domain.Usuario;

public class PostInserirDTO {
	
	private String conteudo;
	
	private LocalDate dataCriacao;
	
	private Usuario usuario;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}