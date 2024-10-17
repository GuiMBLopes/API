package br.org.serratec.FinalAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.FinalAPI.domain.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
