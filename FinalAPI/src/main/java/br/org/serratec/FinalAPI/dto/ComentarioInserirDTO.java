package br.org.serratec.FinalAPI.dto;

import br.org.serratec.FinalAPI.domain.Post;

public class ComentarioInserirDTO {
	
	private String texto;
	private Post post;

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
