package br.org.serratec.FinalAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.FinalAPI.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
