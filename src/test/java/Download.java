import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Download<psvm> {


    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        String downloadtext="Asus Laptop Bios";
        download(downloadtext);
    }


    public static void download(String downloadtext) throws InterruptedException, MalformedURLException {
       /* DesiredCapabilities crcapabilities = DesiredCapabilities.chrome();
        crcapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        crcapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
*/
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        //options.setHeadless(true);
        options.addArguments("start-maximized");
        options.setExperimentalOption("excludeSwitches",
                Arrays.asList("disable-popup-blocking"));
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", "C:\\WorkFolder\\Download");
        prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);
        //WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        //URL url=new URL("http://localhost:4444/wd/hub");
        //Create driver object for Chrome
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, 30);
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
        try {
            //Navigate to a URL
            driver.get("https://chandigarhlaptoprepair.com/my-account");
            //driver.findElement(By.xpath("//div[@class='container rsrc-container']//a[@title='Login / Register']")).click();
            driver.findElement(By.name("username")).sendKeys("surajvaish28@gmail.com");
            driver.findElement(By.id("password")).sendKeys("Suraj@123");
            driver.findElement(By.name("login")).click();
            driver.findElement(By.xpath("//ul[@id='view-all-guides']")).click();
            driver.findElement(By.xpath("//ul[@id='view-all-guides']//li/a[@title='"+downloadtext+"']")).click();

            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//nav[@class='woocommerce-pagination']/ul/li"))));
            int pageCount = driver.findElements(By.xpath("//nav[@class='woocommerce-pagination']/ul/li")).size() - 1;
            //int pageCount =2;
            for (int j = 1; j <= pageCount; j++) {
                int productCount = driver.findElements(By.xpath("//ul[@class='products columns-4']/li")).size();
                for (int i = 1; i <= productCount ; i++) {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='row rsrc-content']//ul[@class='products columns-4']/li[" + i + "]/a[2]")));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", element);

                    //if (j!=pageCount && i!=productCount) {
                    driver.findElement(By.linkText("Continue shopping")).click();
                   // }
                }
                if (j != pageCount) {
                    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//nav[@class='woocommerce-pagination']/ul/li/a[@class='next page-numbers']")));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", element);
                }
            }
            driver.navigate().back();
            //to perform scroll on an application using Selenium

            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.partialLinkText("Proceed to checkout")));
            executor.executeScript("arguments[0].click();", driver.findElement(By.partialLinkText("Proceed to checkout")));
            executor.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//form[@name='checkout']//div[@id='payment']//button[@id='place_order']")));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@name='checkout']//div[@id='payment']//button[@id='place_order']")));
            executor.executeScript("arguments[0].click();", element);
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//section[@class='woocommerce-order-downloads']//tbody/tr/td[@class='download-file']/a"))));
            int downloadCount = driver.findElements(By.xpath("//section[@class='woocommerce-order-downloads']//tbody/tr/td[@class='download-file']/a")).size();
            for (int i = 1; i <= downloadCount; i++) {
                Thread.sleep(1000);
                executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//section[@class='woocommerce-order-downloads']//tbody/tr/td[@class='download-file']/a)[" + i + "]")));
                Thread.sleep(1000);
                if(driver.getTitle().contains("403 Forbidden") || driver.getTitle().contains("503"))
                {
                    driver.navigate().back();
                }
            }

            //driver.findElement(By.xpath("//div[@class='row rsrc-content']//ul[@class='products columns-4']/li[1]/a[2]")).click();
            System.out.println("Reached");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(1000);
            //driver.quit();
        }

    }

}
