package com.group14.MedicalSystemApplication.frontend;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchKlinicFrontendTest 
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
	void frontend_test_search_clinic()
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
