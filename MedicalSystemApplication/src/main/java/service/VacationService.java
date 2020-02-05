package service;

import model.User;
import model.Vacation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.VacationRepository;

import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;


    public void save(Vacation vacation)
    {
        vacationRepository.save(vacation);
    }

    public List<Vacation> findAllByUser(User user)
    {
        return vacationRepository.findAllByUser(user);
    }
}
