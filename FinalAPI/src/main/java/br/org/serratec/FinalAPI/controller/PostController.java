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

import br.org.serratec.FinalAPI.domain.Post;
import br.org.serratec.FinalAPI.dto.PostDTO;
import br.org.serratec.FinalAPI.dto.PostInserirDTO;
import br.org.serratec.FinalAPI.service.PostService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@GetMapping
	public ResponseEntity<List<PostDTO>> listar() {
		return ResponseEntity.ok(postService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> buscar(@PathVariable Long id) {
		Optional<Post> postOpt = postService.buscar(id);
		if(postOpt.isPresent()) {
			return ResponseEntity.ok(new PostDTO(postOpt.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<PostDTO> salvar(@Valid @RequestBody PostInserirDTO postInserirDTO) {
		PostDTO postDTO = postService.inserirPost(postInserirDTO);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(postDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(postDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PostInserirDTO postInserirDTO) {
		Optional<Post> postOpt = postService.buscar(id);
		if (postOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		PostDTO postDTO = postService.inserirPost(postInserirDTO);
		postDTO.setId(id);
		return ResponseEntity.ok(postDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id) {
		Optional<Post> postOpt = postService.buscar(id);
		if (postOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		postService.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
}