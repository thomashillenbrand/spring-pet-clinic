package learn.spring.springpetclinic.bootstrap;

import learn.spring.springpetclinic.model.Owner;
import learn.spring.springpetclinic.model.Pet;
import learn.spring.springpetclinic.model.PetType;
import learn.spring.springpetclinic.model.Specialty;
import learn.spring.springpetclinic.model.Vet;
import learn.spring.springpetclinic.model.Visit;
import learn.spring.springpetclinic.services.OwnerService;
import learn.spring.springpetclinic.services.PetTypeService;
import learn.spring.springpetclinic.services.SpecialtyService;
import learn.spring.springpetclinic.services.VetService;
import learn.spring.springpetclinic.services.VisitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component // since it implements command lin runer, when the app is started , it will run this method
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;


    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) loadData();
        else System.out.println("Bootstrap data already loaded");
    }

    private void loadData() {
        // Set PetTypes
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatType = petTypeService.save(cat);

        // create owners
        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
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
        owner2.setFirstName("Fionna");
        owner2.setLastName("Glenanne");
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

        Visit catVisit = new Visit();
        catVisit.setPet(fionnasPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Cat");
        visitService.save(catVisit);

        System.out.println("Loaded owners . . . ");

        // Set Specialties
        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        // create vets
        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialties().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("John");
        vet2.setLastName("Saw");
        vet2.getSpecialties().add(savedSurgery);
        vetService.save(vet2);

        System.out.println("Loaded Vets . . . ");
    }
}
