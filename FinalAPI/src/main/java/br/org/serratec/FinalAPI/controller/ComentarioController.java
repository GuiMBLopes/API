package br.org.serratec.FinalAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.FinalAPI.domain.Comentario;
import br.org.serratec.FinalAPI.repository.ComentarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@GetMapping
	public ResponseEntity<List<Comentario>> listar() {
		return ResponseEntity.ok(comentarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Comentario> buscar(@PathVariable Long id) {
		Optional<Comentario> usuarioOpt = comentarioRepository.findById(id);
		if(usuarioOpt.isPresent()) {
			return ResponseEntity.ok(usuarioOpt.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/pagina")
	public ResponseEntity<Page<Comentario>> listarPaginas(
			@PageableDefault(sort="id", direction=Sort.Direction.ASC, page=0, size=5)
			Pageable pageable
			) {
		Page<Comentario> comentariosPg = comentarioRepository.findAll(pageable);
		return ResponseEntity.ok(comentariosPg);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Comentario salvar(@Valid @RequestBody Comentario comentario) {
		comentario = comentarioRepository.save(comentario);
		return comentario;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Comentario> atualizar(@PathVariable Long id, @Valid @RequestBody Comentario comentario) {
		if(!comentarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		comentario.setId(id);
		comentario = comentarioRepository.save(comentario);
		return ResponseEntity.ok(comentario);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id) {
		if (!comentarioRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		comentarioRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}