package service;

import model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DrugRepository;

import java.util.List;

@Service
public class DrugService {

    @Autowired
    private DrugRepository drugRepository;

    public Drug findByName(String name) {
        return drugRepository.findByName(name);
    }

    public Drug findByCode(String code) {
        return drugRepository.findByCode(code);
    }

    public void save(Drug drug)
    {
        drugRepository.save(drug);
    }

    public List<Drug> findAll() {
        return drugRepository.findAll();
    }
}
