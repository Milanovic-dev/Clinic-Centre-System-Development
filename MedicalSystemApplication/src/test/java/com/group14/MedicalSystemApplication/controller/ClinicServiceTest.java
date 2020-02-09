package com.group14.MedicalSystemApplication.controller;


import helpers.DateUtil;
import model.Appointment;
import model.Clinic;
import model.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import service.ClinicService;
import service.UserService;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClinicServiceTest {


    @Autowired
    ClinicService clinicService;

    @Autowired
    UserService userService;



    @Test
    @Transactional
    @Rollback(true)
    void test_find_by_name()
    {
        Clinic clinic = clinicService.findByName("KlinikaA");
        assertNotNull(clinic);
    }

    @Test
    @Transactional
    @Rollback(true)
    void test_find_by_doctor()
    {
        Doctor doctor = (Doctor)userService.findByEmailAndDeleted("doktor1@gmail.com", false);
        Clinic clinic = clinicService.findByDoctor(doctor);
        assertNotNull(clinic);
    }


    @Test
    @Transactional
    @Rollback(true)
    void test_save()
    {

        Clinic clinic = new Clinic("KlinikaC","Kralja Petra I 10","Novi Sad","Srbija","Opis KlinikeC");
        clinicService.save(clinic);
        Clinic found = clinicService.findByName("KlinikaC");

        assertTrue(found !=null);
    }

    @Test
    @Transactional
    @Rollback(true)
    void test_find_all_safe()
    {
        List<Clinic> clinics = clinicService.findAll();
        assertFalse(clinics.size() == 0);
    }


}
