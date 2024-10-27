package demo;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import org.apache.commons.collections4.functors.TruePredicate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demo.utils.ExcelDataProvider;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Durations;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @BeforeMethod
        public void goToYT() throws InterruptedException {
                Wrappers.goToUrlAndWait(driver, "https://www.youtube.com");
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
        }

        @Test
        public void testCase01() throws InterruptedException {
                String url=driver.getCurrentUrl();
                Assert.assertTrue(url.contains("youtube"),"URL not contains youtube");
               Wrappers.findElementAndClick(driver, By.xpath("//a[text()='About']"));
               WebElement about=driver.findElement(By.xpath("//h1[contains(text(),'About YouTube')]"));
               String text=about.getText();
               System.out.println(text);
               for(int i=0;i<2;i++){
               String text1=Wrappers.findElementAndPrint(driver, By.xpath("//p"), i);
                System.out.println(text1);
               }

        }

        @Test
        public void testCase02() throws InterruptedException {
                System.out.println("Runnong Test Case 02");
                Wrappers.findElementAndClick(driver, By.xpath("//a[@title='Movies']"));
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                Wrappers.clickTillUnclickable(driver, By.xpath("//span[contains(text(), \"Top selling\")]/ancestor::div[@id=\"dismissible\"]//div[@id=\"right-arrow\"]//button"), 3);
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                By movieMark=By.xpath("//span[contains(text(), \"Top selling\")]/ancestor::div[@id=\"dismissible\"]//div[@id='contents']//div[@class='badge  badge-style-type-simple style-scope ytd-badge-supported-renderer style-scope ytd-badge-supported-renderer']//p");
                String res=Wrappers.findElementAndPrint(driver, movieMark, driver.findElements(movieMark).size()-1);
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                SoftAssert sa=new SoftAssert();
                sa.assertTrue(res.contains("A"),"Movies not mark as 'A'");
                By movieType=By.xpath("//span[contains(text(), \"Top selling\")]/ancestor::div[@id=\"dismissible\"]//div[@id='contents']//span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']");
                String res1=Wrappers.findElementAndPrint(driver, movieType, driver.findElements(movieType).size()-1);
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                sa.assertTrue(res1.contains("Comedy")||res1.equals("Animation")||res1.equals("Drama"),"Category is missing or not as expected");

        }
          
        @Test(enabled=true)
        public void testCase03() throws InterruptedException {
                System.out.println("Running Test Case 03");
                Wrappers.findElementAndClick(driver, By.xpath("//a[@title='Music']"));
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                Wrappers.clickTillUnclickable(driver, By.xpath(
                                "//span[contains(text(),\"India's Biggest Hits\")]//ancestor::div[@id='dismissible']//div[@id='right-arrow']//button"),
                                5);
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                By locator_TrackCount = By.xpath(
                                "//span[contains(.,'Biggest Hits')]//ancestor::div[6]//div[@id='contents']//ytd-compact-station-renderer//p[@id='video-count-text']");
                String res = Wrappers.findElementAndPrint(driver, locator_TrackCount,
                                driver.findElements(locator_TrackCount).size() - 1);
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                SoftAssert sa = new SoftAssert();
                sa.assertTrue(Wrappers.convertToNumericValue(res.split(" ")[0]) <= 50);

        }
       @Test
        public void testCase04() throws InterruptedException {
                System.out.println("Running Test Case 04");   
                Wrappers.findElementAndClick(driver, By.xpath("//a[@title='News']"));
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
                WebElement contentCards=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='rich-shelf-header-container' and contains(.,'Latest news posts')]//ancestor::div[1]//div[@id='contents']")));
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                long sumOfVotes=0;
                for(int i=0;i<3;i++){
                        System.out.println(Wrappers.findElementAndPrintWE(driver,By.xpath("//div[@id='header']"), contentCards, i));
                        System.out.println(Wrappers.findElementAndPrintWE(driver,By.xpath("//div[@id='body']"), contentCards, i));
                        try{
                                String res=Wrappers.findElementAndPrintWE(driver, By.xpath("//span[@id='vote-count-middle']"), contentCards, i);
                                sumOfVotes=sumOfVotes+Wrappers.convertToNumericValue(res);
                        }
                        catch(NoSuchElementException e){
                                System.out.println("Vote not present -"+e.getMessage());
                        }
                        System.out.println(sumOfVotes);
                        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                }
                System.out.println("Ending test case 04");

        }
        @Test(dataProvider = "excelData")
        public void testCase05(String searchWord) throws InterruptedException {
                System.out.println("Running Test case 05 flow for:"+searchWord);
                Wrappers.sendKeysWrapper(driver, By.xpath("//input[@id='search']"), searchWord);
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                long tally=0;
                int iter=1;
                while(tally<1000000000 || iter>5){
                        String res=Wrappers.findElementAndPrint(driver, By.xpath("//div[@class='style-scope ytd-video-meta-block']//span[contains(text(),'view')]"), iter);
                        res=res.split(" ")[0];
                        tally+=Wrappers.convertToNumericValue(res);
                        Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                }
                Thread.sleep((new java.util.Random().nextInt(3) + 2) * 1000);
                System.out.println("Ending of test case 05");


        }

  

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}