package com.group14.MedicalSystemApplication.controller;

import dto.PrescriptionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import service.PrescriptionService;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrescriptionServiceTest {


    @Autowired
    PrescriptionService prescriptionService;


    @Test
    @Transactional
    @Rollback(true)
    void test_validate(){

        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setValid(false);
        dto.setNurseEmail("nurse@gmail.com");
        dto.setId(28);

        prescriptionService.validate(dto);

        assertNotNull(prescriptionService.findById(dto.getId()));
        assertTrue(prescriptionService.findById(dto.getId()).getIsValid());
    }

}
