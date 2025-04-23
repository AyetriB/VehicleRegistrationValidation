# How to run the testsuite 

    mvn clean test

# Class Overview 

### CarDataReader
The `CarDataReader` class is a **utility class** that reads a CSV style file containing car data and loads it into a **Map** for use in tests or other application logic.

### ByFactory
This class defines `By` locators for web elements used in Selenium tests.

### CarRegTest
Main test class which performs automated tests using Selenium and TestNG for verifying vehicle registration data from a website against expected results stored in files.

### ChromeDriverManager
This is singleton class to create the WebDriver instance 

### CarInfo
Model class for storing Car info

### RegExtractor
Based on the car registration regex, this can read input file(s) from a directory and extract the Registration number 
