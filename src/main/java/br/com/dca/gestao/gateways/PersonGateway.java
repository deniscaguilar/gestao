package br.com.dca.gestao.gateways;

import br.com.dca.gestao.entities.Person;
import java.util.Optional;

public interface PersonGateway {

  Person save(Person person);

  Optional<Person> findByDocument(String document);
}
