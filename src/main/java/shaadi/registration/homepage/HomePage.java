package shaadi.registration.homepage;

import java.io.File;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;


import io.github.bonigarcia.wdm.WebDriverManager;

public class HomePage{
	
	WebDriver driver;
		
	@Test (dataProvider="readData")
	public void openBrowserandValidate(String url,String email,String pass,String lang) throws Exception
	{
				
		String driver_path = getFilePath()+"\\src\\test\\resources\\chromedriver.exe";
		driver_path = driver_path.replaceAll(".\\\\src", "\\src");
		
		System.setProperty("webdriver.chrome.driver", driver_path);
		driver = new ChromeDriver();
		WebDriverManager.chromedriver().version("77").setup();
		
		driver.get(url);
		driver.manage().window().maximize();
		
		WebDriverWait wait = new WebDriverWait(driver,20);
		
		driver.findElement(By.xpath("//a[@data-testid='login_link']")).click();
		
		driver.findElement(By.xpath("//a[@data-testid='sign_up_free']")).click();
		
		driver.findElement(By.xpath("//input[@data-testid='email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@data-testid='password1']")).sendKeys(pass);
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='Dropdown-placeholder']")));
		
		driver.findElement(By.xpath("//div[@class='Dropdown-placeholder']")).click();
				
		driver.findElement(By.xpath("//div[@class='Dropdown-menu postedby_options']"));
		driver.findElement(By.xpath("//div[@class='Dropdown-menu postedby_options']/div[1]")).click();
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@data-testid='gender_male']")).click();
		
		driver.findElement(By.xpath("//button[@data-testid='next_button']")).click();
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='Dropdown-control mother_tongue_selector Dropdown-disabled']/div[1]")));
		
		String language = driver.findElement(By.xpath("//div[@class='Dropdown-control mother_tongue_selector Dropdown-disabled']/div[1]")).getText();
		Assert.assertEquals(language, lang);
	}
	
		
	@DataProvider (name="readData")
	public Object[][] readCSV() throws Exception
	{
		String filename = getFilePath()+"\\src\\test\\resources\\Registration.csv";
		filename = filename.replaceAll(".\\\\src", "\\src");
		
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		sc.next(); //ignoring csv first line
		
		Object[][] obj = new Object[2][4];
		int i=0;
		while(sc.hasNext())
		{
			String data = sc.next();
			String[] values = data.split(",");
						
			obj[i][0] = values[0];
			obj[i][1] = values[1];
			obj[i][2] = values[2];
			obj[i][3] = values[3];
					
			i++;
		}
		
		sc.close();
		return obj;
	
	}
	
		
	@AfterMethod(alwaysRun = true)
	  public void closeBrowser() {
		    //driver.close();
		}
	
	public String getFilePath()
	{
		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		return helper;
	}
}
