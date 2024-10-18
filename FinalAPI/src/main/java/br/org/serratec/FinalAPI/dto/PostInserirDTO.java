package br.org.serratec.FinalAPI.dto;

import java.time.LocalDate;

public class PostInserirDTO {
	private String conteudo;
	private LocalDate dataCriacao;

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

}
