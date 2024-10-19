package br.org.serratec.FinalAPI.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.serratec.FinalAPI.domain.Relationship;
import br.org.serratec.FinalAPI.domain.Usuario;
import br.org.serratec.FinalAPI.dto.UsuarioDTO;
import br.org.serratec.FinalAPI.dto.UsuarioInserirDTO;
import br.org.serratec.FinalAPI.dto.UsuarioSeguidoDTO;
import br.org.serratec.FinalAPI.exception.CadastroException;
import br.org.serratec.FinalAPI.exception.FollowException;
import br.org.serratec.FinalAPI.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

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
	public UsuarioDTO inserir(UsuarioInserirDTO usuarioInserirDTO) throws CadastroException {
		if (!usuarioInserirDTO.getSenha().equals(usuarioInserirDTO.getConfirmaSenha())) {
			throw new CadastroException("As Senhas não iguais");
		}
		if (usuarioRepository.findByEmail(usuarioInserirDTO.getEmail()) != null) {
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

		return new UsuarioDTO(usuarioRepository.save(usuario));

	}

	public UsuarioSeguidoDTO seguir(Long id) {
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
		return new UsuarioSeguidoDTO(usuarioRepository.findById(id).get());
	}

	public String idUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}
		throw new RuntimeException("Usuario não Autenticado");
	}

}
