package com.group14.MedicalSystemApplication.frontend;


import model.Appointment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import service.AppointmentService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendPredefinedAppointmentRequestFrontendTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private String base;

    @Autowired
    AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("marionette",true);
        this.driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        this.base = "http://localhost:"+port;
    }


    @Test
    public void frontend_test_send_predefined_appointment_request_patient(){
        driver.navigate().to(base+"/login.html");
        driver.findElement(By.id("inputEmail")).sendKeys("patient@gmail.com");
        driver.findElement(By.id("inputPassword")).sendKeys("123");
        driver.findElement(By.id("submitLogin")).click();
        sleep(2);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("clinicList")));
        driver.findElement(By.id("preApps")).click();
        sleep(2);
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("clinicList")));
        driver.findElement(By.id("submitPredefinedAppRequest0")).click();
        sleep(5);

        Appointment app = appointmentService.findAppointment("21-03-2020 07:30", 1, "KlinikaA");
        assertNotNull(app);
    }


    private void sleep(long seconds)
    {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown()
    {

        driver.quit();
    }


}
