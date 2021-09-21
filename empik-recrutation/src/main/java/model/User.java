package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	private Long id;
	private String login;
	private String name;
	private String type;
	private String avatarUrl;
	private String createdAt;
	private String calculations;
}
