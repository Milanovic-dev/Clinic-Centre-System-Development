package service;

import dto.PrescriptionDTO;
import model.Appointment;
import model.Nurse;
import model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.PrescriptionRepository;
import repository.UserRepository;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    UserRepository userRepository;

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

    public Prescription findById(long id) {
        return  prescriptionRepository.findById(id);
    }

    @Transactional(isolation =  Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void validate(PrescriptionDTO dto){
        Prescription prescription = prescriptionRepository.findById(dto.getId());

        if(prescription == null)
        {
            throw new ValidationException("prescription");
        }

        Nurse nurse = (Nurse)userRepository.findByEmailAndDeleted(dto.getNurseEmail(), false);
        if(nurse == null)
        {
            throw new ValidationException("nurse");
        }


        if(prescription != null)
        {
            if(prescription.getVersion() != dto.getVersion())
            {
                throw new ObjectOptimisticLockingFailureException("Resource Locked.", prescription);
            }
        }

        Date date = new Date();

        prescription.setValid(true);
        prescription.setNurse(nurse);
        prescription.setValidationDate(date);
        prescriptionRepository.save(prescription);
    }
}
