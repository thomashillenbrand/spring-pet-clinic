package learn.spring.springpetclinic.services;

import learn.spring.springpetclinic.model.Owner;
import learn.spring.springpetclinic.model.Vet;

import java.util.Set;

public interface VetService {

    Vet findById(Long id);

    Vet findByLastName(String lastName);

    Vet save(Owner owner);

    Set<Vet> findAll();
}
