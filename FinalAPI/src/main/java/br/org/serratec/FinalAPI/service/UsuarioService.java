package br.org.serratec.FinalAPI.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.serratec.FinalAPI.domain.Relationship;
import br.org.serratec.FinalAPI.domain.Usuario;
import br.org.serratec.FinalAPI.dto.NomeUsuarioDTO;
import br.org.serratec.FinalAPI.dto.UsuarioDTO;
import br.org.serratec.FinalAPI.dto.UsuarioInserirDTO;
import br.org.serratec.FinalAPI.exception.CadastroException;
import br.org.serratec.FinalAPI.exception.FollowException;
import br.org.serratec.FinalAPI.repository.RelationshipRepository;
import br.org.serratec.FinalAPI.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private RelationshipRepository relationshipRepository;
	
	@Autowired
	private FotoService fotoService;
	

	public List<UsuarioDTO> listar() {
		return usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
	}

	public Optional<Usuario> buscar(Long id) {
		return usuarioRepository.findById(id);
	}

	public void deletarPorId(Long id) {
		usuarioRepository.deleteById(id);
	}

	@Transactional
	public UsuarioDTO inserir(UsuarioInserirDTO usuarioInserirDTO/*, MultipartFile file*/) throws CadastroException, IOException {
		if (!usuarioInserirDTO.getSenha().equals(usuarioInserirDTO.getConfirmaSenha())) {
			throw new CadastroException("As Senhas não iguais");
		}
		if (usuarioRepository.findByEmail(usuarioInserirDTO.getEmail()).isPresent()) {
			throw new CadastroException("O email já existente");
		}
		Period periodo = Period.between(usuarioInserirDTO.getDataNascimento(), LocalDate.now());
		if (periodo.getYears() < 16) {
			throw new CadastroException("Menor que 16 anos não pode se cadastrar");
		}

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioInserirDTO.getNome());
		usuario.setSobrenome(usuarioInserirDTO.getSobrenome());
		usuario.setEmail(usuarioInserirDTO.getEmail());
		usuario.setSenha(encoder.encode(usuarioInserirDTO.getSenha()));
		usuario.setDatasNascimento(usuarioInserirDTO.getDataNascimento());

		usuarioRepository.save(usuario);
		
		//fotoService.inserir(usuario, file);
		
		//return adicionarImagemUri(usuario);
		
		return new UsuarioDTO(usuario);
	}
	
//	public UsuarioDTO adicionarImagemUri(Usuario usuario) {
//		URI uri = ServletUriComponentsBuilder
//				.fromCurrentContextPath()
//				.path("/usuario/{id}/foto")
//				.buildAndExpand(usuario.getId())
//				.toUri();
//		UsuarioDTO dto = new UsuarioDTO();
//		dto.setId(usuario.getId());
//		dto.setNome(usuario.getNome());
//		dto.setSobrenome(usuario.getSobrenome());
//		dto.setEmail(usuario.getEmail());
//		dto.setDataNascimento(usuario.getDatasNascimento());
//		dto.setUrl(uri.toString());
//		return dto;
//	}

	public NomeUsuarioDTO seguir(Long id) {
		Optional<Usuario> logado = usuarioRepository.findByEmail(idUsuarioLogado());

		if (id.equals(logado.get().getId())) {
			throw new FollowException("Não pode seguir a si mesmo");
		}
		if (usuarioRepository.findById(id).isEmpty()) {
			throw new FollowException("Esse usuario não existe");
		}
		for (Relationship relationship : logado.get().getRelationships()) {
			if (id.equals(relationship.getId().getUsuario().getId())) {
				throw new FollowException("Você já segue esse usuario");
			}
			
		}
		
		relationshipRepository.save(new Relationship(usuarioRepository.findById(id).get(), logado.get(), LocalDate.now()));
		
		return new NomeUsuarioDTO(usuarioRepository.findById(id).get());
	}

	public String idUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}
		throw new RuntimeException("Usuario não Autenticado");
	}
	
	public Page<UsuarioDTO> listarPorIdade(Pageable pageable){
		Page<Usuario> pagUsuarios = usuarioRepository.ListarPorIdade(pageable);
		Page<UsuarioDTO> pagUsuariosDTO = pagUsuarios.map(u -> new UsuarioDTO(u));
		return pagUsuariosDTO;
	}
	
	public Page<UsuarioDTO> buscarPorNome(String nome, Pageable pageable){
		Page<Usuario> pagUsuarios = usuarioRepository.buscarPorNome(nome, pageable);
		Page<UsuarioDTO> pagUsuariosDTO = pagUsuarios.map(u -> new UsuarioDTO(u));
		
		return pagUsuariosDTO;
	}

}
