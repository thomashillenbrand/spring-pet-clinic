package learn.spring.springpetclinic.bootstrap;

import learn.spring.springpetclinic.model.Owner;
import learn.spring.springpetclinic.model.Vet;
import learn.spring.springpetclinic.services.OwnerService;
import learn.spring.springpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // since it implements command lin runer, when the app is started , it will run this method
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstname("Michael");
        owner1.setLastname("Weston");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstname("Fionna");
        owner2.setLastname("Glenanne");
        ownerService.save(owner2);

        System.out.println("Loaded owners . . . ");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstname("Sam");
        vet1.setLastname("Axe");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstname("John");
        vet2.setLastname("Saw");
        vetService.save(vet2);

        System.out.println("Loaded Vets . . . ");
    }
}