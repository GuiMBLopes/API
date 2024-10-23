package br.org.serratec.FinalAPI.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.serratec.FinalAPI.domain.Foto;
import br.org.serratec.FinalAPI.domain.Usuario;
import br.org.serratec.FinalAPI.repository.FotoRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;
	
	public Foto inserir(Usuario usuario, MultipartFile file ) throws IOException {
		Foto foto = new Foto();
		
		foto.setNome(file.getName());
		foto.setTipo(file.getContentType());
		foto.setDados(file.getBytes());
		foto.setUsuario(usuario);
		return fotoRepository.save(foto);
	}
	

	
	public Foto buscar(Long id) {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		Optional<Foto> fotoOpt = fotoRepository.findByUsuario(usuario);
		if(fotoOpt.isEmpty()) {
			return null;
		}
		return fotoOpt.get();
	}
	
}
