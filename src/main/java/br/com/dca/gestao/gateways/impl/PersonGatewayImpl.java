package br.com.dca.gestao.gateways.impl;

import br.com.dca.gestao.entities.Person;
import br.com.dca.gestao.gateways.PersonGateway;
import br.com.dca.gestao.repositories.PersonRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonGatewayImpl implements PersonGateway {

  private PersonRepository personRepository;

  @Override
  public Person save(final Person person) {
    return personRepository.save(person);
  }

  @Override
  public Optional<Person> findByDocument(final String document) {
    return personRepository.findByDocument(document);
  }
}
