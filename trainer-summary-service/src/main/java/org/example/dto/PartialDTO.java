package org.example.dto;

import javax.xml.transform.sax.SAXResult;
import java.time.Month;
import java.time.Year;
import java.util.Map;

public class PartialDTO {
    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Number getSummaryDuration() {
        return summaryDuration;
    }

    public void setSummaryDuration(Number summaryDuration) {
        this.summaryDuration = summaryDuration;
    }

    Number summaryDuration = 0;

}
