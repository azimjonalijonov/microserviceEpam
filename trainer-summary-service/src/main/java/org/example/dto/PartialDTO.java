package org.example.dto;

import java.time.Month;
import java.util.Map;

public class PartialDTO {
    private Month month;

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
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
