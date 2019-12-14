package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.User;
import model.User.UserRole;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByEmailAndDeleted(String email, Boolean deleted);
	
	public List<User> findAllByRole(UserRole role);
	
}
