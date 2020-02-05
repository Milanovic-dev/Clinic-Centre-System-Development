package repository;

import model.User;
import model.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

     List<Vacation> findAllByUser(User user);
}
