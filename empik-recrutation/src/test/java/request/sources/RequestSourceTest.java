package request.sources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import dto.UserRequest;
import repositories.UserRequestRepository;

@DataJpaTest
@EnableJpaRepositories("repositories")
@EntityScan("dto")
@ContextConfiguration(classes = { RequestSourceImpl.class })
class RequestSourceTest {

	@Autowired
	RequestSource requestSource;

	@Autowired
	UserRequestRepository repository;

	@Test
	void testIncrement() {
		String key = "octocat";
		repository.save(new UserRequest(key, 0));
		requestSource.increment("octocat");
		UserRequest userRequest = repository.getById(key);
		assertEquals(1, userRequest.getCount());
	}

}
