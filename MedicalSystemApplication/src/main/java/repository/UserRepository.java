package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;
import model.User.UserRole;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByEmail(String email);
	
	public List<User> findAllByRole(UserRole role);
	
}
