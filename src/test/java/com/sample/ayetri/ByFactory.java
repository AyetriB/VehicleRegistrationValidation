package com.sample.ayetri;

import org.openqa.selenium.By;

public class ByFactory {
    public static final By input_box = By.id("subForm1");
    public static final By check_now_btn = By.className("check-now-button");
    public static final By general_information_table = By.cssSelector("table.table-responsive");
    public static final By general_information_table_rows = By.tagName("tr");
    public static final By general_information_table_columns = By.xpath("./th|./td");
}
