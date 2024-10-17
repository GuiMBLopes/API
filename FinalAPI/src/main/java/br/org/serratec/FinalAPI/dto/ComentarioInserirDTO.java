package br.org.serratec.FinalAPI.dto;

import java.time.LocalDate;

public class ComentarioInserirDTO {
	private String texto;
	private LocalDate dataCricao;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDate getDataCricao() {
		return dataCricao;
	}

	public void setDataCricao(LocalDate dataCricao) {
		this.dataCricao = dataCricao;
	}

}
