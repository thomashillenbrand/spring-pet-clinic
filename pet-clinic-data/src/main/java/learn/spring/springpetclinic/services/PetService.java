package learn.spring.springpetclinic.services;

import learn.spring.springpetclinic.model.Owner;
import learn.spring.springpetclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long id);

    Pet save(Owner owner);

    Set<Pet> findAll();
}
