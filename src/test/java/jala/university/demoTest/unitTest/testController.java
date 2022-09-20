package jala.university.demoTest.unitTest;

import jala.university.demoTest.entities.User;
import jala.university.demoTest.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class testController {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public UserService userService;

    @Test
    public void mustGetAllUsers() throws Exception {
        User user = new User(UUID.randomUUID(),"Alejo","Galizzi","alejo.galizzi@fundacion-jala.org");
        List<User> users = Arrays.asList(user);
        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/users")
                .contentType("application/json")
                .content(users.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
