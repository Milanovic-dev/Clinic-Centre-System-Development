package com.group14.MedicalSystemApplication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EndToEndTests {
	
	private final static Logger logger = LoggerFactory.getLogger(EndToEndTests.class);
	
	@LocalServerPort
	private int port;
	
	private WebDriver driver;

	private String base;
	
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
	public void e2e_send_appointment_request_patient()
	{		
		driver.navigate().to(base+"/login.html");
		driver.findElement(By.id("inputEmail")).sendKeys("nikolamilanovic21@gmail.com");
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		driver.findElement(By.id("submitLogin")).click();
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("clinicList")));
		driver.findElement(By.id("clinicList")).click();
		
		sleep(3);
		
		(new WebDriverWait(driver, 10))
           .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("selectAppointmentType"))));
		Select ToE = new Select(driver.findElement(By.id("selectAppointmentType")));
		ToE.selectByValue("Stomatoloski");
		
		sleep(3);
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("tableSearch_btn_clinicTable"))));
		driver.findElement(By.id("tableSearch_btn_clinicTable")).click();
		   
		sleep(3);
	
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("makeAppointment_btn0")));
		driver.findElement(By.id("makeAppointment_btn0")).click();
						
		sleep(3);
		
		driver.findElement(By.id("detailsAppointment_btn")).click();
		
		sleep(3);
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("inputStartTime")));
		driver.findElement(By.id("inputStartTime")).sendKeys("1400");
		
		sleep(3);
		
		driver.findElement(By.id("submitAppointmentRequest")).click();
		
		sleep(6);
		logger.info("e2e_send_appointment_request_patient finished successfully");
	}
	
	 @AfterEach
	 public void tearDown() {
		 driver.close();
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
}
