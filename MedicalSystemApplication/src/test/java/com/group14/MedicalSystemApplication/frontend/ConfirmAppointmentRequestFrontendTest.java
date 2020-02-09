package com.group14.MedicalSystemApplication.frontend;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import model.Appointment;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfirmAppointmentRequestFrontendTest 
{
	
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
		
		//sleep(1000);
		
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
		sleep(6);
	
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
