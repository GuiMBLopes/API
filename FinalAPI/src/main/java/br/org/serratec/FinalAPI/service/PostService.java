package br.org.serratec.FinalAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.FinalAPI.domain.Post;
import br.org.serratec.FinalAPI.dto.PostDTO;
import br.org.serratec.FinalAPI.dto.PostInserirDTO;
import br.org.serratec.FinalAPI.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public List<PostDTO> listar() {
		return postRepository.findAll().stream().map(PostDTO::new).toList();
	}

	public Optional<Post> buscar(Long id) {
		return postRepository.findById(id);
	}

	public void deletarPorId(Long id) {
		postRepository.deleteById(id);
	}
	
	public List<PostDTO> postPorUsuario(Long id){
		List<PostDTO> postDTO = new ArrayList<>();
		for (Post post : usuarioService.buscar(id).get().getPosts()) {
			postDTO.add(new PostDTO(post));
		}
		return postDTO;
	}
	
	public PostDTO inserirPost (PostInserirDTO postInserirDTO) {
		Post post = new Post();
		post.setConteudo(postInserirDTO.getConteudo());
		post.setDataCriacao(postInserirDTO.getDataCriacao());
		//post.setUsuario(usuarioService.buscar(usuarioService.idUsuarioLogado()).get());
		
		return new PostDTO(postRepository.save(post));
	}
	
}
