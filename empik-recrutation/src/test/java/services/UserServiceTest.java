package services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import model.User;
import request.sources.DumbRequestCountSource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ContextConfiguration(classes = { UserService.class, DumbRequestCountSource.class })
class UserServiceTest {

	@Autowired
	UserService userService;

	@Test
	void testGetUser() {
		String login = "octocat";
		User user = userService.getUser(login);
		assertEquals("octocat", user.getLogin());
		assertEquals("The Octocat", user.getName());
		assertEquals("User", user.getType());
		assertEquals("https://avatars.githubusercontent.com/u/583231?v=4", user.getAvatarUrl());
		assertEquals("2011-01-25T18:44:36Z", user.getCreatedAt());
		assertEquals("0.0149812734082397", user.getCalculations());
	}

}
