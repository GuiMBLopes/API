package br.org.serratec.FinalAPI.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.FinalAPI.domain.Post;
import br.org.serratec.FinalAPI.dto.PostDTO;
import br.org.serratec.FinalAPI.dto.PostInserirDTO;
import br.org.serratec.FinalAPI.repository.PostRepository;
import br.org.serratec.FinalAPI.repository.UsuarioRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<PostDTO> listar() {
		return postRepository.findAll().stream().map(PostDTO::new).toList();
	}

	public PostDTO buscar(Long id) {
		return new PostDTO(postRepository.findById(id).get());
	}

	public void deletarPorId(Long id) {
		postRepository.deleteById(id);
	}
	
	public List<PostDTO> postPorUsuario(Long id){
		List<PostDTO> postDTO = new ArrayList<>();
		for (Post post : usuarioRepository.findById(id).get().getPosts()) {
			postDTO.add(new PostDTO(post));
		}
		return postDTO;
	}
	
	public PostDTO inserirPost (PostInserirDTO postInserirDTO) {
		Post post = new Post();
		post.setConteudo(postInserirDTO.getConteudo());
		post.setDataCriacao(postInserirDTO.getDataCriacao());
		
		return new PostDTO(postRepository.save(post));
	}
	
}
