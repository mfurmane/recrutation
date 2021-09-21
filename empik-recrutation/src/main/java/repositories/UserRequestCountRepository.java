package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dto.UserRequestCount;

public interface UserRequestCountRepository extends JpaRepository<UserRequestCount, String> {

}
