package jala.university.demoTest.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jala.university.demoTest.entities.User;
import jala.university.demoTest.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setup() {
		userRepository.deleteAll();
	}

	//Test when a user is saved
	@Test
	public void whenFindById_thenReturnUser() {
		//given
		User user = User.builder().firstName("Alejo").email("alejo.galizzi@fundacion-jala.org").lastName("Galizzi").build();
		//when
		userRepository.save(user);
		User found = userRepository.findById(user.getId()).orElse(null);

		//then
		assertThat(found.getFirstName()).isEqualTo(user.getFirstName());
	}

	//Test when the request of getting all users
	@Test
	public void whenGetAllUsers_Return200() throws Exception {
		//given
		User user = User.builder().firstName("Alejo").email("alejo.galizzi@fundacion-jala.org").lastName("Galizzi").build();
		//when
		userRepository.save(user);
		List<User> users = userRepository.findAll();

		//then
		mockMvc.perform(get("/users")
						.contentType("application/json")
						.content(users.toString()))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(users.size())));
	}

	@Test
	public void whenPostAUser_return201() throws Exception {
		//given
		User user = User.builder().firstName("Alejo").email("alejo.galizzi@fundacion-jala.org").lastName("Galizzi").build();

		//when
		userRepository.save(user);

		// then
		mockMvc.perform(post("/users")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(user)))
				.andDo(print()).andExpect(status().isCreated())
				.andExpectAll(jsonPath("$.firstName", is(user.getFirstName())), jsonPath("$.lastName", is(user.getLastName())), jsonPath("$.email", is(user.getEmail())));
	}

	@Test
	public void whenPostAUserWithBlankArguments_return400() throws Exception {
		//given
		User user = User.builder().firstName("").email("alejo.galizzi@fundacion-jala.org").lastName("Galizzi").build();


		//when and then
		mockMvc.perform(post("/users")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(user)))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpectAll(jsonPath("$.messages",hasSize(2)));
	}
}
