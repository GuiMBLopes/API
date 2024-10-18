package br.org.serratec.FinalAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.org.serratec.FinalAPI.domain.Post;
import br.org.serratec.FinalAPI.repository.PostRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostRepository postRepository;
	
	@GetMapping
	public ResponseEntity<List<Post>> listar() {
		return ResponseEntity.ok(postRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> buscar(@PathVariable Long id) {
		Optional<Post> postOpt = postRepository.findById(id);
		if(postOpt.isPresent()) {
			return ResponseEntity.ok(postOpt.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Post salvar(@Valid @RequestBody Post post) {
		post = postRepository.save(post);
		return post;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Post> atualizar(@PathVariable Long id, @Valid @RequestBody Post post) {
		if(!postRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		post.setId(id);
		post = postRepository.save(post);
		return ResponseEntity.ok(post);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id) {
		if (!postRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		postRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}