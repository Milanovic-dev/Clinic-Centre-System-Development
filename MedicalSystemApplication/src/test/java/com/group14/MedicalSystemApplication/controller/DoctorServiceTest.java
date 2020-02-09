package com.group14.MedicalSystemApplication.controller;


import model.Clinic;
import model.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import service.ClinicService;
import service.DoctorService;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorServiceTest {

    @Autowired
    DoctorService doctorService;

    @Autowired
    ClinicService clinicService;


    @Test
    @Transactional
    @Rollback(true)
    void test_find_all_by_clinic_and_type()
    {
        Clinic clinic = clinicService.findByName("KlinikaB");
        List<Doctor> doctors = doctorService.findAllByClinicAndType(clinic, "Neuroloski" );

        assertTrue(doctors.size() != 0);
    }

}
