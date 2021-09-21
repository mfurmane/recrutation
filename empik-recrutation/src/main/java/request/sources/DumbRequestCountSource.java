package request.sources;

import java.util.HashMap;
import java.util.Map;

//@Component
public class DumbRequestCountSource implements RequestCountSource {

	Map<String, Integer> database = new HashMap<>();

	@Override
	public void incrementRequestCount(String login) {
		Integer count = 1;
		if (database.containsKey(login))
			count = database.get(login) + 1;
		database.put(login, count);
	}

}
