package com.group14.MedicalSystemApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import dto.ClinicDTO;
import dto.ClinicFilterDTO;
import helpers.DateUtil;
import model.Appointment;
import model.AppointmentRequest;
import service.AppointmentRequestService;
import service.AppointmentService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndTests {
	
	private final static Logger logger = LoggerFactory.getLogger(EndToEndTests.class);
	
	@LocalServerPort
	private int port;
	
	private WebDriver driver;

	private String base;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private AppointmentRequestService appointmentRequestService;
	
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
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("inputEmail")));
		driver.findElement(By.id("inputEmail")).sendKeys("nikolamilanovic21@gmail.com");
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("inputPassword")));
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("submitLogin")));
		driver.findElement(By.id("submitLogin")).click();
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("clinicList")));
		driver.findElement(By.id("clinicList")).click();
		
		
		sleep(1);
		
		(new WebDriverWait(driver, 10))
           .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("selectAppointmentType"))));
		Select ToE = new Select(driver.findElement(By.id("selectAppointmentType")));
		ToE.selectByValue("Stomatoloski");
		
		sleep(1);
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("tableSearch_btn_clinicTable"))));
		driver.findElement(By.id("tableSearch_btn_clinicTable")).click();
		   
		sleep(1);
	
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("makeAppointment_btn0")));
		driver.findElement(By.id("makeAppointment_btn0")).click();
						
		sleep(1);
		
		driver.findElement(By.id("detailsAppointment_btn")).click();
		
		sleep(1);
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("inputStartTime")));
		driver.findElement(By.id("inputStartTime")).sendKeys("1400");
		
		sleep(1);
		
		driver.findElement(By.id("submitAppointmentRequest")).click();
		
		sleep(2);
		
		AppointmentRequest request = appointmentRequestService.findAppointmentRequest(DateUtil.getInstance().getString(new Date(), "dd-MM-yyyy") + " 14:00","nikolamilanovic21@gmail.com", "KlinikaA");
		
		assertTrue(request != null);
		
		logger.info("e2e_send_appointment_request_patient finished successfully");
	}
	
	@Test
	public void e2e_search_clinics()
	{
		driver.navigate().to(base+"/login.html");
		driver.findElement(By.id("inputEmail")).sendKeys("nikolamilanovic21@gmail.com");
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		driver.findElement(By.id("submitLogin")).click();
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("clinicList")));
		driver.findElement(By.id("clinicList")).click();
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("selectAppointmentType"))));
		Select ToE = new Select(driver.findElement(By.id("selectAppointmentType")));
		ToE.selectByValue("Stomatoloski");
		
		driver.findElement(By.id("clinicAdressPick")).sendKeys("Bulevar Osl.");
		driver.findElement(By.id("clinicRatingPick")).sendKeys("0");
		driver.findElement(By.id("tableSearch_btn_clinicTable")).click()
		;
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("makeAppointment_btn0")));
		driver.findElement(By.id("makeAppointment_btn0")).click();
						
		sleep(1);
		
		TestRestTemplate rest = new TestRestTemplate();
		ResponseEntity<ClinicDTO[]> response = rest.postForEntity(getPath()+"/getAll/{date}/{type}",new ClinicFilterDTO(), ClinicDTO[].class, DateUtil.getInstance().getString(new Date(), "dd-MM-yyyy"),"Stomatoloski");
		assertTrue(response.getBody().length > 0);
	}
	
	@Test
	public void e2e_confirm_appointment_request()
	{
		driver.navigate().to(base+"/login.html");
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("inputEmail")));
		driver.findElement(By.id("inputEmail")).sendKeys("adminKlinike@gmail.com");
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("inputPassword")));
		driver.findElement(By.id("inputPassword")).sendKeys("123");
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("submitLogin")));
		driver.findElement(By.id("submitLogin")).click();
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("examinationRequestList")));
		driver.findElement(By.id("examinationRequestList")).click();

		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.name("28-02-2020 11:00")));
		driver.findElement(By.name("28-02-2020 11:00")).click();
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("appEndTime")));
		driver.findElement(By.id("appEndTime")).sendKeys("14:00");
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("DoctorPicker")));
		Select docSelect = new Select(driver.findElement(By.id("selectDoctor")));
		//sleep(100);
		docSelect.selectByIndex(0);
		
		driver.findElement(By.id("submitApp")).click();
		sleep(2);
		
		(new WebDriverWait(driver, 10))
        .until(ExpectedConditions.visibilityOf(driver.findElement(By.id("examinationRequestList"))));
		
		Appointment app = appointmentService.findAppointment("28-02-2020 11:00", 1, "KlinikaA");
		
		assertTrue(app != null);
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
	 
	 private String getPath()
	 {
		 return "http://localhost:"+port+"api/clinic";
	 }
}
