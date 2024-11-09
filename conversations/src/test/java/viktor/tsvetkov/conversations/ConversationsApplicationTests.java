package viktor.tsvetkov.conversations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.enums.Sex;
import viktor.tsvetkov.conversations.mappers.UserTestMapper;
import viktor.tsvetkov.conversations.repositories.UserChatItemRepository;
import viktor.tsvetkov.conversations.security.authentication.AuthenticationService;
import viktor.tsvetkov.conversations.security.dto.RegisterRequest;
import viktor.tsvetkov.conversations.services.impl.UserService;
import viktor.tsvetkov.conversations.utils.TestQueries;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@SqlGroup({
		@Sql(value = "classpath:sql/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
		@Sql(value = "classpath:sql/drop_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)})
class ConversationsApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UserChatItemRepository userChatItemRepository;

	@Test
	public void testGetChatWithInterlocutor() {

	}

	@Test
	public void testCreateUser() {
		jdbcTemplate.update(TestQueries.CREATE_USER);
		List<User> users = jdbcTemplate.query(TestQueries.SELECT_USER, new UserTestMapper());
		Assertions.assertNotNull(users);
		Assertions.assertFalse(users.isEmpty());
		User maria = users.get(0);
		Assertions.assertEquals(1, users.size());
		Assertions.assertEquals("Maria", maria.getName());
	}

	@Test
	public void testSaveDto() {
		UserDto userDto = new UserDto(null, "Petr", Sex.MALE, "petr222", "12345");
		userService.save(userDto);
		String sql = "select * from users where name = 'Petr'";
		List<User> users = jdbcTemplate.query(sql, new UserTestMapper());
		Assertions.assertFalse(users.isEmpty());
		Assertions.assertEquals(1, users.size());
		User petr = users.get(0);
		Assertions.assertEquals("Petr", petr.getName());
		Assertions.assertEquals(Sex.MALE, petr.getSex());
		Assertions.assertEquals("petr222", petr.getUsername());
		Assertions.assertEquals("12345", petr.getPassword());
	}

	@Test
	public void testRegister() {
		String name = "Pidoras";
		String username = "pidor14";
		String password = "55555";
		Sex sex = Sex.MALE;
		RegisterRequest request = new RegisterRequest(name, username, password, sex);
		authenticationService.register(request);
		String sql = "select * from users where name = '" + name + "';";
		List<User> users = jdbcTemplate.query(sql, new UserTestMapper());
		Assertions.assertFalse(users.isEmpty());
		Assertions.assertEquals(1, users.size());
		User user = users.get(0);
		Assertions.assertEquals(name, user.getName());
		Assertions.assertEquals(username, user.getUsername());
		Assertions.assertEquals(sex, user.getSex());
	}
}
