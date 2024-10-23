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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;
	
	@Autowired
	private PostService postService;
	
	@Operation(summary = "Lista todos os comentarios.", description = "Retorna uma lista de comentarios contendo "
			+ "id do comenatario, texto, id de quem fez, id do post.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Comentario.class), mediaType = "application/json") }, description = "Retorna todos os post"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Vocẽ não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Post não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
	
	@GetMapping
	public ResponseEntity<List<ComentarioDTO>> listar() {
		return ResponseEntity.ok(comentarioService.listar());
	}
	
	@Operation(summary = "Mostra um comentario", description = "Mostra os dados do comentario buscado:  "
			+ "id do comenatario, texto, id de quem fez, id do post.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Comentario.class), mediaType = "application/json") }, description = "Retorna o post procurado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Você não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))

	
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
	
	@Operation(summary = "Insere um novo comentario", description = "A resposta é um objeto "
			+ "id do comentario, texto ,conteudo, id de quem fez, id do post")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Comentario adicionado "),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "400", description = "Requisição invalida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Esta requisção não é valida\", \"message\": \"Por favor altere sua requisição.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))

	
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
	
	@Operation(summary = "Altera um comentario existente", description = "A resposta é um objeto "
			+ "com os dados alterados")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "comentario alterado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "400", description = "Requisição invalida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Esta requisção não é valida\", \"message\": \"Por favor altere sua requisição.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))

	
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
		Optional<Comentario> comentarioOpt = comentarioService.buscar(id);
		if (comentarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		comentarioService.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
}