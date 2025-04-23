package com.sample.ayetri;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;

import static com.sample.ayetri.ByFactory.check_now_btn;
import static com.sample.ayetri.ByFactory.general_information_table;
import static com.sample.ayetri.ByFactory.general_information_table_columns;
import static com.sample.ayetri.ByFactory.general_information_table_rows;
import static com.sample.ayetri.ByFactory.input_box;
import static com.sample.ayetri.Constants.CAR_CHECKING_URL;
import static com.sample.ayetri.Constants.MAKE;
import static com.sample.ayetri.Constants.MODEL;
import static com.sample.ayetri.Constants.YEAR;

public class CarRegTest {
    private WebDriver driver;
    private Map<String, CarInfo> expectedCarsOutput;
    @BeforeClass
    public void setUp() throws IOException {
        driver = ChromeDriverManager.getManagerInstance().getDriver();
        expectedCarsOutput = new CarDataReader().loadExpectedCarInfo("src/test/resources/car_output.txt");
    }

    @DataProvider(name = "regProvider")
    public Object[][] getRegistrations() throws IOException {
        List<String> allRegistrations = new ArrayList<>();
        Path dirPath = Paths.get("src/test/resources");
        // All files starting with car_input will be processed here
        DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "car_input*.txt");

        for (Path file : stream) {
            String content = Files.readString(file);
            List<String> regList = RegExtractor.extractRegistrations(content);
            allRegistrations.addAll(regList);
        }
        Object[][] data = new Object[allRegistrations.size()][1];
        for (int i = 0; i < allRegistrations.size(); i++) {
            data[i][0] = allRegistrations.get(i);
        }
        return data;
    }

    @Test(dataProvider = "regProvider")
    public void testRegistration(String regNumber) throws IOException {
        System.out.println("Checking: " + regNumber);
        CarInfo expectedCarInfo = expectedCarsOutput.get(regNumber);
        if(expectedCarInfo == null) {
            Assert.fail(regNumber+ " : Output file does not contain any information about this registration");
            return;
        }
        try{
            driver.get(CAR_CHECKING_URL);
            WebElement inputBox = driver.findElement(input_box);
            inputBox.sendKeys(regNumber);

            // Click on "Check Now" button
            WebElement checkNowButton = driver.findElement(check_now_btn);
            checkNowButton.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(general_information_table));

            List<WebElement> rows = table.findElements(general_information_table_rows);

            for (WebElement row : rows) {
                List<WebElement> columns = row.findElements(general_information_table_columns);
                if(MAKE.equals(columns.get(0).getText())) {
                    String make = columns.get(1).getText();
                    String expectedMake = expectedCarInfo.getMake();
                    Assert.assertEquals(make, expectedMake);
                }
                if(MODEL.equals(columns.get(0).getText())) {
                    String model = columns.get(1).getText();
                    String expectedModel = expectedCarInfo.getModel();
                    Assert.assertEquals(model, expectedModel);
                }
                if(YEAR.equals(columns.get(0).getText())) {
                    Integer year = Integer.parseInt(columns.get(1).getText());
                    int expectedYear = expectedCarInfo.getYear();
                    Assert.assertEquals(year, expectedYear);
                }
            }
        }
        catch (TimeoutException e) {
            Assert.fail(regNumber + " : Table not visible after waiting: " + e.getMessage());
        } catch (NoSuchElementException e) {
            Assert.fail(regNumber + " : Table element not found on the page: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail(regNumber + " : Unexpected error: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
