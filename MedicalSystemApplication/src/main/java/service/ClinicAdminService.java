package service;
import model.ClinicAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClinicAdminRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicAdminService {

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;



    public List<ClinicAdmin> getAll()
    {
        return clinicAdminRepository.findAll();
    }

    public ClinicAdmin findById(Long id)
    {
        Optional<ClinicAdmin> user = clinicAdminRepository.findById(id);

        if(user.isPresent())
        {
            return user.get();
        }

        return null;

    }

    public ClinicAdmin findByEmail(String email)
    {
        return clinicAdminRepository.findByEmail(email);
    }

    public ClinicAdmin findByUsername(String username)
    {
        return clinicAdminRepository.findByUsername(username);
    }

    public void save(ClinicAdmin clinicAdmin)
    {
        clinicAdminRepository.save(clinicAdmin);
    }
}
