package br.org.serratec.FinalAPI.dto;

import br.org.serratec.FinalAPI.domain.Usuario;

public class NomeUsuarioDTO {

	private String nome;
	private String sobrenome;
	
	public NomeUsuarioDTO(Usuario usuario) {
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
}
