package request.sources;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dto.UserRequestCount;
import repositories.UserRequestCountRepository;

@Component
public class RequestCountSourceImpl implements RequestCountSource {

	@Autowired
	private UserRequestCountRepository repository;

	@Override
	@Transactional
	public void incrementRequestCount(String login) {
		Optional<UserRequestCount> optional = repository.findById(login);
		if (optional.isPresent()) {
			UserRequestCount entity = optional.get();
			entity.setCount(entity.getCount() + 1);
			repository.save(entity);
		} else {
			repository.save(new UserRequestCount(login, 1));
		}
	}

}
