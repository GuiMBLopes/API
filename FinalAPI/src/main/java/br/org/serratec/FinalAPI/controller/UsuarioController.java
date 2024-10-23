package br.org.serratec.FinalAPI.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.org.serratec.FinalAPI.domain.Usuario;
import br.org.serratec.FinalAPI.dto.NomeUsuarioDTO;
import br.org.serratec.FinalAPI.dto.UsuarioDTO;
import br.org.serratec.FinalAPI.dto.UsuarioIdadeDTO;
import br.org.serratec.FinalAPI.dto.UsuarioInserirDTO;
import br.org.serratec.FinalAPI.exception.CadastroException;
import br.org.serratec.FinalAPI.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Operation(summary = "Lista todos os usuarios cadastrados.", description = "Retorna uma lista de usuarios contendo "
			+ "id do usuario, nome, sobrenome, email.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }, description = "Retorna todos os usuarios"),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Usuario não encontrado"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))})
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar() {
		return ResponseEntity.ok(usuarioService.listar());
	}
	
	@Operation(summary = "Mostra um usuario", description = "Mostra os dados do usuario buscado:  "
			+ "id do usuario, nome, sobrenome, email.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }, description = "Retorna o usuario procurado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Você não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))

	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioService.buscar(id);
		if(usuarioOpt.isPresent()) {
			return ResponseEntity.ok(new UsuarioDTO(usuarioOpt.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@Operation(summary = "Insere um novo usuario", description = "A resposta é um objeto "
			+ "com os dados cadastrados do servidor")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuario adicionado "),
			@ApiResponse(responseCode = "400", description = "Requisição invalida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Esta requisção não é valida\", \"message\": \"Por favor altere sua requisição.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))})
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<UsuarioDTO> salvar(@Valid @RequestPart  UsuarioInserirDTO usuarioInserirDTO, @RequestPart MultipartFile file) throws CadastroException, IOException {
		UsuarioDTO usuarioDTO = usuarioService.inserir(usuarioInserirDTO ,file);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuarioDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDTO);
	}
	
	@Operation(summary = "Altera um usuario existente", description = "A resposta é um objeto "
			+ "com os dados alterados")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuario alterado"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "400", description = "Requisição invalida", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Esta requisção não é valida\", \"message\": \"Por favor altere sua requisição.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO) throws CadastroException, IOException {
		Optional<Usuario> usuarioOpt = usuarioService.buscar(id);
		if (usuarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		UsuarioDTO usuarioDTO = usuarioService.atualizar(id, usuarioInserirDTO);
		usuarioDTO.setId(id);
		return ResponseEntity.ok(usuarioDTO);
	}
	
	@Operation(summary = "Exclui um usuario", description = "A resposta é o objeto " + "excluido")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", content = {
			@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Exclui um objeto pelo id"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Credenciais inválidas.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioService.buscar(id);
		if (usuarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		usuarioService.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Segue um usuario", description = "Retorna o usuario seguido com:"
			+ "id do usuario, nome, sobrenome")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }, description = "Retorna todos os usuarios"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Vocẽ não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Usuario não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
	@GetMapping("/seguir/{id}")
	public ResponseEntity<NomeUsuarioDTO> seguir(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.seguir(id));
	}
	
	@Operation(summary = "Retorna uma lista de usuarios", description = "Retorna uma lista de usuarios com:"
			+ " nome, sobrenome, idade")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }, description = "Retorna todos os usuarios"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Vocẽ não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Usuario não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
	@GetMapping("/pagina-por-idade")
	public ResponseEntity<List<UsuarioIdadeDTO>> listarPorIdade (@PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable pageable){
		return ResponseEntity.ok(usuarioService.listarPorIdade()); 
	}
	
	@Operation(summary = "Retorna uma pagina com os usuarios que contenham o nome ou letra inserida", description = "Retorna o usuario com:"
			+ "id do usuario, nome, sobrenome, email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json") }, description = "Retorna todos os usuarios"),
			@ApiResponse(responseCode = "401", description = "Erro na autenticação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Não autorizado\", \"message\": \"Vocẽ não tem acesso.\"}"))),
			@ApiResponse(responseCode = "404", content = {
					@Content(schema = @Schema(type = "object", nullable = true), mediaType = "application/json") }, description = "Usuario não encontrado"),
			@ApiResponse(responseCode = "500", description = "Token expirado", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Token expirado\", \"message\": \"O seu token expirou.\"}"))) })
			@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", example = "{\"error\": \"Internal Server Error\", \"message\": \"Ocorreu um erro inesperado.\"}")))
	
	@GetMapping("/pagina-nomes")
	public ResponseEntity<Page<UsuarioDTO>> buscarPorNome(String nome, @PageableDefault(page = 0, size = 10) Pageable pageable){
		return ResponseEntity.ok(usuarioService.buscarPorNome(nome, pageable));
	}
}
