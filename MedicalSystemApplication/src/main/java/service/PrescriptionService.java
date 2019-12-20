package service;

import model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PrescriptionRepository;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    public void save(Prescription prescription)
    {
        prescriptionRepository.save(prescription);
    }

    public void delete(Prescription prescription)
    {
        prescriptionRepository.delete(prescription);
    }


    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }
}
