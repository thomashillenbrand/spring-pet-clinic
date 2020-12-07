package learn.spring.springpetclinic.controllers;

import learn.spring.springpetclinic.model.Owner;
import learn.spring.springpetclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
@Slf4j
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model) {
        log.trace("Enter findOwners()");
        model.addAttribute("owner", Owner.builder().build());
        log.trace("Exit findOwners()");
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        log.trace("Enter processFindForm()");
        String viewName;
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            viewName = "owners/findOwners";
        }
        else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            viewName = "redirect:/owners/" + owner.getId();
        }
        else {
            // multiple owners found
            model.addAttribute("selections", results);
            viewName = "owners/ownersList";
        }
        log.trace("Exit processFindForm()");
        return viewName;
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        log.trace("Enter showOwner()");
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        Owner owner = ownerService.findById(ownerId);

        mav.addObject(owner);
        log.trace("Exit showOwner()");
        return mav;
    }

}
