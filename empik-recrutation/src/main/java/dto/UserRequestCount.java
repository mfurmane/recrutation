package dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "REQUEST")
public class UserRequestCount {

	@Id
	@Column(name = "LOGIN")
	private String login;
	
	@Column(name = "REQUEST_COUNT")
	private Integer count;

}
