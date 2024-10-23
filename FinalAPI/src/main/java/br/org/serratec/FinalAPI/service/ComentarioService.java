package br.org.serratec.FinalAPI.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.FinalAPI.domain.Comentario;
import br.org.serratec.FinalAPI.domain.Post;
import br.org.serratec.FinalAPI.domain.Relationship;
import br.org.serratec.FinalAPI.domain.RelationshipPK;
import br.org.serratec.FinalAPI.dto.ComentarioDTO;
import br.org.serratec.FinalAPI.dto.ComentarioInserirDTO;
import br.org.serratec.FinalAPI.dto.PostDTO;
import br.org.serratec.FinalAPI.exception.FollowException;
import br.org.serratec.FinalAPI.repository.ComentarioRepository;
import br.org.serratec.FinalAPI.repository.RelationshipRepository;
import br.org.serratec.FinalAPI.repository.UsuarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RelationshipRepository relationshipRepository;
	
	@Autowired
	private PostService postService;

	public List<ComentarioDTO> listar() {
		List<Comentario> comentarios = comentarioRepository.findAll();
		List<ComentarioDTO> comentariosDTO = new ArrayList<>();
		for (Comentario comentario : comentarios) {
			ComentarioDTO comentarioDTO = new ComentarioDTO(comentario);
			comentarioDTO.setPostDTO(new PostDTO(comentario.getPost()));
			comentariosDTO.add(comentarioDTO);
		}
		return comentariosDTO;
	}

	public Optional<Comentario> buscar(Long id) {
		return comentarioRepository.findById(id);
	}

	public void deletarPorId(Long id) {
		comentarioRepository.deleteById(id);
	}
	
	public List<ComentarioDTO> comentarioPorUsuario(Long id){
		List<ComentarioDTO> comentariosDTO = new ArrayList<>();
		for (Comentario comentario : usuarioRepository.findById(id).get().getComentarios()) {
			comentariosDTO.add(new ComentarioDTO(comentario));
		}
		return comentariosDTO;
	}
	
	public ComentarioDTO inserirComentario (ComentarioInserirDTO comentarioInserirDTO) {
		Comentario comentario = new Comentario();
		comentario.setUsuario(usuarioService.buscar(usuarioRepository.findByEmail(usuarioService.idUsuarioLogado()).get().getId()).get());
		comentario.setTexto(comentarioInserirDTO.getTexto());
		comentario.setDataCriacao(LocalDate.now());		
		comentario.setPost(postService.buscar(comentarioInserirDTO.getPost().getId()).get());
		
		if(usuarioRepository.findByEmail(usuarioService.idUsuarioLogado()).get().getId().equals(comentario.getPost().getUsuario().getId())) {
			return new ComentarioDTO(comentarioRepository.save(comentario)); 
		}
		
		for (Relationship relationship: comentario.getPost().getUsuario().getRelationships()) {
			if(usuarioRepository.findByEmail(usuarioService.idUsuarioLogado()).get().getId().equals(relationship.getId().getSeguidor().getId())) {
				return new ComentarioDTO(comentarioRepository.save(comentario));
			}
		}	
		throw new FollowException("Você não segue esse usuario");
		
		
	}
	
}