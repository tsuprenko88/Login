package W3School;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Login extends Utils {
	
	@Test
	public void log_in(String browser) throws Exception {
		
		xlSheetName = "Login";
		xlWritePath = "excel-output//"+xlSheetName+".xls";
		
		String login, password, expResult, actResult;
		
		
		xlRead(xlPath, xlSheetName);
		
		driverSetup();
		
		driver.get("http://w3schools.invisionzone.com/");
		driver.manage().window().maximize();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
				
		for (int i = 1; i < xlRows; i++) {
			
			login = localArray[i][0];
			if (login.equals("0")) {
				login = null;
			}
			password = localArray[i][1];
			if (password.equals("0")) {
				password = null;
			}
			expResult = localArray[i][2];
			
			Reporter.log("Login: " + login, true);
			Reporter.log("Password: " + password, true);
						
			element = driver.findElement(By.id("sign_in"));
			element.click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign_in_popup_popup")));
			
			element = driver.findElement(By.id("ips_username"));
			element.sendKeys(login);
			
			element = driver.findElement(By.id("ips_password"));
			element.sendKeys(password);
			
			element = driver.findElement(By.xpath("//*[@id='login']/div/div/input"));
			element.click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
			
			boolean error = driver.findElements(By.xpath("//*[@id='content']/p")).size() != 0;
			
			if (error) {
				
				actResult = driver.findElement(By.xpath("//*[@id='content']/p")).getText();
				
				Reporter.log("Error: " + actResult, true);
				
				if (expResult.equals(actResult)) {
					localArray[i][3] = actResult;
					localArray[i][4] = "Pass";
				}
				else {
					localArray[i][3] = actResult;
					localArray[i][4] = "FAIL";
					fail = true;
					failName.add(login + " + " + password + ": Expected result: \"" + expResult + "\", but actual: \"" + actResult + "\"");
				}
				
				xlWrite(xlWritePath, xlSheetName, localArray);
								
				if (i != xlRows - 1) {
					Reporter.log("______________________________", true);
				}
				Reporter.log("", true);
				
				element = driver.findElement(By.xpath("//*[@id='logo']/a/img"));
				element.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
				
			}
			else {
							
				actResult = driver.findElement(By.xpath("//*[@id='header_bar']/div/div[1]/ul/li[3]")).getText().trim();
				
				Reporter.log("Username: " + actResult, true);
				
				if (expResult.equals(actResult)) {
					localArray[i][3] = actResult;
					localArray[i][4] = "Pass";
				}
				else {
					localArray[i][3] = actResult;
					localArray[i][4] = "FAIL";
					fail = true;
					failName.add(login + " + " + password + ": Expected result: \"" + expResult + "\", but actual: \"" + actResult + "\"");
				}
				
				xlWrite(xlWritePath, xlSheetName, localArray);
								
				if (i != xlRows - 1) {
					Reporter.log("______________________________", true);
				}
				Reporter.log("", true);
				
				element = driver.findElement(By.xpath("//*[@id='user_navigation']/ul/li[4]/a"));
				element.click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
			}
				
		}
		
		verifyFailandCloseDriver();
				
	}
	
}
