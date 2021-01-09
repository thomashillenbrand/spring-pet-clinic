package learn.spring.springpetclinic.controllers;

import learn.spring.springpetclinic.model.Owner;
import learn.spring.springpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;
    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;

    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void findOwner() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoMoreInteractions(ownerService);
    }

    @Test
    void processFindOwnerFormReturnMany() throws Exception {

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                Arrays.asList(
                        Owner.builder().id(1L).build(),
                        Owner.builder().id(1L).build())
        );

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));

    }

    @Test
    void processFindOwnerEmptyFormReturnMany() throws Exception {

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                Arrays.asList(
                        Owner.builder().id(1L).build(),
                        Owner.builder().id(1L).build())
        );

        mockMvc.perform(get("/owners").param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindOwnerFormReturnOne() throws Exception {

        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1L).build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void showOwner() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {

        when(ownerService.save(any(Owner.class))).thenReturn(Owner.builder().id(1L).build());
        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService).save(any(Owner.class));
    }

    @Test
    void initUpdateForm() throws Exception {
        // when ownerService gets an owner
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        // then perform a get on the edit page
        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));

        verify(ownerService).findById(anyLong());
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.save(any(Owner.class))).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));

        verify(ownerService).save(any(Owner.class));

    }
}