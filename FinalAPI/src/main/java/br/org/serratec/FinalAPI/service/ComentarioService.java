package br.org.serratec.FinalAPI.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.FinalAPI.domain.Comentario;
import br.org.serratec.FinalAPI.dto.ComentarioDTO;
import br.org.serratec.FinalAPI.dto.ComentarioInserirDTO;
import br.org.serratec.FinalAPI.dto.PostDTO;
import br.org.serratec.FinalAPI.repository.ComentarioRepository;
import br.org.serratec.FinalAPI.repository.UsuarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

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
		comentario.setPost(comentarioInserirDTO.getPost());
		comentario.setDataCriacao(LocalDate.now());
		
		return new ComentarioDTO(comentarioRepository.save(comentario));
	}
	
}