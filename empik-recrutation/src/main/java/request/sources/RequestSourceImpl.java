package request.sources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.UserRequest;
import repositories.UserRequestRepository;

@Component
public class RequestSourceImpl implements RequestSource {

	@Autowired
	private UserRequestRepository repository;

	@Override
	public void increment(String login) {
		Optional<UserRequest> optional = repository.findById(login);
		if (optional.isPresent()) {
			UserRequest entity = optional.get();
			entity.setCount(entity.getCount() + 1);
			repository.save(entity);
		} else {
			repository.save(new UserRequest(login, 1));
		}
	}

}
