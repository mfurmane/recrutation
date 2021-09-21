package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import model.User;
import services.UserService;

@RestController
public class ApiController {

	@Autowired
	private UserService userService;

	@GetMapping("/users/{login}")
	User getUser(@PathVariable(name = "login") String login) {
		return userService.getUser(login);
	}

}
