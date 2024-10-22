package br.org.serratec.FinalAPI.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.org.serratec.FinalAPI.dto.UsuarioInserirDTO;
import br.org.serratec.FinalAPI.exception.CadastroException;
import br.org.serratec.FinalAPI.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar() {
		return ResponseEntity.ok(usuarioService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioService.buscar(id);
		if(usuarioOpt.isPresent()) {
			return ResponseEntity.ok(new UsuarioDTO(usuarioOpt.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping/*(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})*/
	public ResponseEntity<UsuarioDTO> salvar(@Valid /*@RequestPart*/ @RequestBody UsuarioInserirDTO usuarioInserirDTO/*, @RequestPart MultipartFile file*/) throws CadastroException, IOException {
		UsuarioDTO usuarioDTO = usuarioService.inserir(usuarioInserirDTO /*,file*/);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuarioDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO) throws CadastroException, IOException {
		Optional<Usuario> usuarioOpt = usuarioService.buscar(id);
		if (usuarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		UsuarioDTO usuarioDTO = usuarioService.inserir(usuarioInserirDTO/*, null*/);
		usuarioDTO.setId(id);
		return ResponseEntity.ok(usuarioDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagar(@PathVariable Long id) {
		Optional<Usuario> usuarioOpt = usuarioService.buscar(id);
		if (usuarioOpt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		usuarioService.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/seguir/{id}")
	public ResponseEntity<NomeUsuarioDTO> seguir(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.seguir(id));
	}
	
	@GetMapping("/pagina-por-idade")
	public ResponseEntity<Page<UsuarioDTO>> listarPorIdade(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return ResponseEntity.ok(usuarioService.listarPorIdade(pageable)); 
	}
	
	@GetMapping("/pagina-nomes")
	public ResponseEntity<Page<UsuarioDTO>> buscarPorNome(String nome, @PageableDefault(page = 0, size = 10) Pageable pageable){
		return ResponseEntity.ok(usuarioService.buscarPorNome(nome, pageable));
	}
}