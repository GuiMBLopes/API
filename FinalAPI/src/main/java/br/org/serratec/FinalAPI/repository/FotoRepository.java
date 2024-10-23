package br.org.serratec.FinalAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.FinalAPI.domain.Foto;
import br.org.serratec.FinalAPI.domain.Usuario;


@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

	Optional<Foto> findByUsuario(Usuario usuario);
	
}
