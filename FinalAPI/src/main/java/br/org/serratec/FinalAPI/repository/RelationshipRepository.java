package br.org.serratec.FinalAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.serratec.FinalAPI.domain.Relationship;
import br.org.serratec.FinalAPI.domain.RelationshipPK;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipPK> {
}
