package repository;

import model.ClinicAdmin;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin,Long> {

    public ClinicAdmin findByUsername(String username);

    public ClinicAdmin findByEmail(String email);

    public List<ClinicAdmin> findAllByRole(User.UserRole role);
}
