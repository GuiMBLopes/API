package br.org.serratec.FinalAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.serratec.FinalAPI.domain.Usuario;
import br.org.serratec.FinalAPI.dto.UsuarioIdadeDTO;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
	
	Usuario findByNomeAndSobrenome(String Nome,String Sobrenome);
	
	@Query("SELECT u FROM Usuario u WHERE UPPER (u.nome) LIKE UPPER(CONCAT('%', :nome, '%'))")
	Page<Usuario> buscarPorNome(String nome, Pageable pageable);
	
	@Query(value = """
			SELECT
				timestampdiff(YEAR, data_nascimento, current_date) as idade,
				nome,
				sobrenome
			FROM usuario
			GROUP BY idade, nome, sobrenome
			ORDER BY idade DESC
			""", nativeQuery = true)
	List<UsuarioIdadeDTO> ListarPorIdade();

}
