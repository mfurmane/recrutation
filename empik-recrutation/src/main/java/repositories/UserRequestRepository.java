package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dto.UserRequest;

public interface UserRequestRepository extends JpaRepository<UserRequest, String> {

}
