package com.group14.MedicalSystemApplication.controller;


import dto.PriceListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceListControllerTest {


    @LocalServerPort
    private int port;


    @Test
    void test_get_all_by_clinic()
    {
        TestRestTemplate rest = new TestRestTemplate();


        ResponseEntity<PriceListDTO[]> response =
                rest.getForEntity(getPath() + "/getAllByClinic/{clinicName}", PriceListDTO[].class, "KlinikaA");

        PriceListDTO[] apps = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(apps.length > 0);
    }



    private String getPath()
    {
        return "http://localhost:"+port+"/api/priceList";
    }

}
