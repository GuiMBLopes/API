package br.org.serratec.FinalAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.FinalAPI.domain.Comentario;
import br.org.serratec.FinalAPI.domain.Usuario;


@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
	
	List<Comentario> findByUsuario(Usuario usuario);
}
