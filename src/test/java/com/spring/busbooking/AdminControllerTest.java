package com.spring.busbooking;

import com.spring.busbooking.controller.AdminController;
import com.spring.busbooking.model.Bus;
import com.spring.busbooking.repository.BusRepository;
import com.spring.busbooking.service.AdminService;
import com.spring.busbooking.validation.BusNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BusRepository busRepository;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testGetmethod() throws Exception {
        // Arrange
        when(adminService.getAllBuses()).thenReturn(List.of(new Bus()));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("buses"))
                .andDo(print());
    }

    @Test
    public void testAddRoute() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/add")
                .accept(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin_add_route"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bus"))
                .andDo(print());
    }

    @Test
    public void testProcessRoute() throws Exception {
        Bus bus = new Bus();
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/add")
                .flashAttr("bus", bus)
                .param("busName", "Bus Name")
                .param("busClass", "Luxury")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"))
                .andDo(print());

        verify(busRepository, times(1)).save(any(Bus.class));
    }

    @Test
    public void testDeleteRoute() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"))
                .andDo(print());

        verify(busRepository, times(1)).deleteById(1);
    }

    @Test
    public void testUpdateRoute() throws Exception {
        when(adminService.findBusById(1)).thenReturn(new Bus());

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin_update_route"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bus"))
                .andDo(print());
    }

    @Test
    public void testUpdateProcess() throws Exception {
        Bus bus = new Bus();
        when(adminService.findBusById(1)).thenReturn(bus);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/update/1")
                .flashAttr("bus", bus)
                .param("busName", "Updated Bus Name")
                .param("busClass", "Luxury")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"))
                .andDo(print());

        verify(adminService, times(1)).updateBus(any(Bus.class));
    }
}

