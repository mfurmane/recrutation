package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import request.sources.RequestCountSource;

@Service
public class UserService {

	@Value("${basic.url:https://api.github.com/users/}")
	private String basicUrl;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RequestCountSource requestCountSource;

	public User getUser(String login) {
		requestCountSource.incrementRequestCount(login);
		User user = new User();
		try {
			URL url = new URL(basicUrl.concat(login));
			String mainResponse = callUrl(url);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> valuesMap = mapper.readValue(mainResponse, Map.class);
			fillUser(user, valuesMap);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			user.setId(-1L);
			user.setName("Exception at getting user data");
		}
		return user;
	}

	private String callUrl(URL url) throws MalformedURLException, IOException, ProtocolException {
		HttpsURLConnection connection = prepareConnection(url);
		String response = "{}";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			response = readResponse(reader);
		}
		return response;
	}

	private HttpsURLConnection prepareConnection(URL url) throws MalformedURLException, IOException, ProtocolException {
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		return connection;
	}

	private String readResponse(BufferedReader in) throws IOException {
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		return content.toString();
	}

	private void fillUser(User user, Map<String, Object> valuesMap) {
		user.setLogin(valuesMap.get("login").toString());
		user.setId(Long.parseLong(valuesMap.get("id").toString()));
		Object name = valuesMap.get("name");
		user.setName(name != null ? name.toString() : null);
		user.setType(valuesMap.get("type").toString());
		user.setAvatarUrl(valuesMap.get("avatar_url").toString());
		user.setCreatedAt(valuesMap.get("created_at").toString());
		user.setCalculations(doCalculations(valuesMap));
	}

	private Double doCalculations(Map<String, Object> valuesMap) {
		double followers = Double.valueOf(valuesMap.get("followers").toString());
		double publicRepos = Double.valueOf(valuesMap.get("public_repos").toString());
		Double calculation = followers != 0 ? 6 * (2 + publicRepos) / followers : -1;
		return calculation;
	}

}
