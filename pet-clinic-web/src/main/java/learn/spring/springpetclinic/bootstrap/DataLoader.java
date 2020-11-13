package learn.spring.springpetclinic.bootstrap;

import learn.spring.springpetclinic.model.Owner;
import learn.spring.springpetclinic.model.Pet;
import learn.spring.springpetclinic.model.PetType;
import learn.spring.springpetclinic.model.Vet;
import learn.spring.springpetclinic.services.OwnerService;
import learn.spring.springpetclinic.services.PetTypeService;
import learn.spring.springpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component // since it implements command lin runer, when the app is started , it will run this method
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        dog.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstname("Michael");
        owner1.setLastname("Weston");
        owner1.setAddress("3320 W Altgeld St");
        owner1.setCity("Chicago");
        owner1.setTelephone("1111111111");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");

        owner1.getPets().add(mikesPet);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstname("Fionna");
        owner2.setLastname("Glenanne");
        owner2.setAddress("3320 W Altgeld St");
        owner2.setCity("Chicago");
        owner2.setTelephone("1111111111");

        Pet fionnasPet = new Pet();
        fionnasPet.setPetType(savedCatType);
        fionnasPet.setOwner(owner2);
        fionnasPet.setBirthDate(LocalDate.now());
        fionnasPet.setName("Just Cat");

        owner2.getPets().add(fionnasPet);
        ownerService.save(owner2);

        System.out.println("Loaded owners . . . ");

        Vet vet1 = new Vet();
        vet1.setFirstname("Sam");
        vet1.setLastname("Axe");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstname("John");
        vet2.setLastname("Saw");
        vetService.save(vet2);

        System.out.println("Loaded Vets . . . ");
    }
}
