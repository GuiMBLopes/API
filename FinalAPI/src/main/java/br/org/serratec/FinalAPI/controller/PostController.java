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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Operation(summary = "Lista todos os posts.", description = "Retorna uma lista de posts contendo "
			+ "id do post, conteudo, id de quem fez.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }, description = "Retorna todos os post"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Vocẽ não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Post não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
	@GetMapping
	public ResponseEntity<List<PostDTO>> listar() {
		return ResponseEntity.ok(postService.listar());
	}
	
	@Operation(summary = "Mostra um post", description = "Mostra os dados do post buscado:  "
			+ "id do post, conteudo, id de quem fez.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json") }, description = "Retorna o post procurado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Você não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))

	
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> buscar(@PathVariable Long id) {
		Optional<Post> postOpt = postService.buscar(id);
		if(postOpt.isPresent()) {
			return ResponseEntity.ok(new PostDTO(postOpt.get()));
		}
		return ResponseEntity.notFound().build();
	}
	

	@Operation(summary = "Insere um novo post", description = "A resposta é um objeto "
			+ "id do post, conteudo, id de quem fez.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Post adicionado "),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "400", description = "Requisição invalida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Esta requisção não é valida\", \"message\": \"Por favor altere sua requisição.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
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
	
	@Operation(summary = "Altera um post existente", description = "A resposta é um objeto "
			+ "com os dados alterados")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Post alterado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "400", description = "Requisição invalida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Esta requisção não é valida\", \"message\": \"Por favor altere sua requisição.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))

	
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
	
	@Operation(summary = "Exclui um post", description = "A resposta é o objeto " + "excluido")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", content = {
			@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Exclui um objeto pelo id"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
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