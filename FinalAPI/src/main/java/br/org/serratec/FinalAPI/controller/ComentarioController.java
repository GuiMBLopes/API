package br.org.serratec.FinalAPI.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.FinalAPI.domain.Comentario;
import br.org.serratec.FinalAPI.dto.ComentarioDTO;
import br.org.serratec.FinalAPI.dto.ComentarioInserirDTO;
import br.org.serratec.FinalAPI.dto.PostDTO;
import br.org.serratec.FinalAPI.service.ComentarioService;
import br.org.serratec.FinalAPI.service.PostService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping
	public ResponseEntity<List<ComentarioDTO>> listar() {
		return ResponseEntity.ok(comentarioService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ComentarioDTO> buscar(@PathVariable Long id) {
		Optional<Comentario> comentarioOpt = comentarioService.buscar(id);
		if(comentarioOpt.isPresent()) {
			ComentarioDTO comentario = new ComentarioDTO(comentarioOpt.get());
			comentario.setPostDTO(new PostDTO(comentarioOpt.get().getPost()));
			return ResponseEntity.ok(comentario);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<ComentarioDTO> salvar(@Valid @RequestBody ComentarioInserirDTO comentarioInserirDTO) {
		ComentarioDTO comentarioDTO = comentarioService.inserirComentario(comentarioInserirDTO);
		comentarioDTO.setPostDTO(new PostDTO(postService.buscar(comentarioInserirDTO.getPost().getId()).get()));
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(comentarioDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(comentarioDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ComentarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ComentarioInserirDTO comentarioInserirDTO) {
		Optional<Comentario> comentarioOpt = comentarioService.buscar(id);
		if (comentarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		ComentarioDTO comentarioDTO = comentarioService.inserirComentario(comentarioInserirDTO);
		comentarioDTO.setId(id);
		return ResponseEntity.ok(comentarioDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id) {
		Optional<Comentario> comentarioOpt = comentarioService.buscar(id);
		if (comentarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		comentarioService.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
}