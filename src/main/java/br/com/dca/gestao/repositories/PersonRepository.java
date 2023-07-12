package br.com.dca.gestao.repositories;

import br.com.dca.gestao.entities.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByDocument(final String document);
}
