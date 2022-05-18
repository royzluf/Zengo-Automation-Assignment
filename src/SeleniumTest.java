import java.util.*;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumTest {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "./browserDrivers/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        Actions action = new Actions(driver);

        try
        {

            // Open the site
            driver.manage().window().maximize();
            driver.get("https://www.zengo.com");

            // Verify that the site displayed by page and credit
            WebElement page = driver.findElement(By.id("page"));
            WebElement credit = driver.findElement(By.xpath("//*[@id='colophon']/div/div[3]/div"));
            if (!(page.isDisplayed() && credit.isDisplayed()))
            {
                throw new Exception("The site 'www.zengo.com' did not displayed correctly");
            }

            // Get to the menu item 'Features'
            WebElement features = driver.findElement(By.id("menu-item-13191"));
            action.moveToElement(features).perform();
            Thread.sleep(2 * 1000);

            // Click on the 'Buy' element
            WebElement buy = driver.findElement(By.id("menu-item-10474"));
            action.moveToElement(buy).click().perform();
            Thread.sleep(1 * 1000);

            // Verify redirected to https://zengowallet.banxa.com/
            Set<String> windowsSet = driver.getWindowHandles();
            List<String> windowsList = new ArrayList<String>(windowsSet);
            driver.switchTo().window(windowsList.get(1));

            if (!(driver.getCurrentUrl().startsWith("https://zengowallet.banxa.com/")))
            {
                throw new Exception("Site did not redirected to https://zengowallet.banxa.com/");
            }

            // Verify ZenGo logo is displayed
            WebElement zenLogo = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\'app\']/div/div/img[2]")));

            if (!(zenLogo.isDisplayed()))
            {
                throw new Exception("ZenGo logo did not displayed successfully");
            }

            // Get back to Home page
            Thread.sleep(2 * 1000);
            driver.switchTo().window(windowsList.get(0));
            Thread.sleep(2 * 1000);

            System.out.println("The automation ended successfully!");

        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Quit browser
        finally
        {
            driver.quit();
        }
    }
}
