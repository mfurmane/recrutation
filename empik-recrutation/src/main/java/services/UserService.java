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
import request.sources.RequestSource;

@Service
public class UserService {

	@Value("${basic.url}")
	private String basicUrl;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RequestSource requestSource;

	public User getUser(String login) {
		requestSource.increment(login);
		User user = new User();
		try {
			URL url = new URL(basicUrl.concat(login));
			String mainResponse = callUrl(url);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> valuesMap = mapper.readValue(mainResponse, Map.class);
			user.setLogin(valuesMap.get("login").toString());
			user.setId(Long.parseLong(valuesMap.get("id").toString()));
			Object name = valuesMap.get("name");
			user.setName(name !=null ? name.toString() : null);
			user.setType(valuesMap.get("type").toString());
			user.setAvatarUrl(valuesMap.get("avatar_url").toString());
			user.setCreatedAt(valuesMap.get("created_at").toString());
			user.setCalculations(doCalculations(valuesMap).toString());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return user;
	}

	private Double doCalculations(Map<String, Object> valuesMap) {
		double followers = Double.valueOf(valuesMap.get("followers").toString());
		double publicRepos = Double.valueOf(valuesMap.get("public_repos").toString());
		Double calculation = 6 / followers * (2 + publicRepos);
		return calculation;
	}

	private String callUrl(URL url) throws MalformedURLException, IOException, ProtocolException {
		HttpsURLConnection connection = prepareConnection(url);
		String response = "{}";
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			response = readResponse(in);
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

}
