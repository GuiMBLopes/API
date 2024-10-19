package br.org.serratec.FinalAPI.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.FinalAPI.domain.Comentario;
import br.org.serratec.FinalAPI.dto.ComentarioDTO;
import br.org.serratec.FinalAPI.dto.ComentarioInserirDTO;
import br.org.serratec.FinalAPI.repository.ComentarioRepository;
import br.org.serratec.FinalAPI.repository.UsuarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	public List<ComentarioDTO> listar() {
		return comentarioRepository.findAll().stream().map(ComentarioDTO::new).toList();
	}

	public ComentarioDTO buscar(Long id) {
		return new ComentarioDTO(comentarioRepository.findById(id).get());
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
		comentario.setTexto(comentarioInserirDTO.getTexto());
		comentario.setDataCriacao(comentarioInserirDTO.getDataCricao());
		
		return new ComentarioDTO(comentarioRepository.save(comentario));
	}
	
}