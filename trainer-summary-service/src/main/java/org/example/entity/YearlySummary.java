package org.example.entity;

import java.util.Map;

public class YearlySummary {

    private Map<String, Number> monthlySummary;

    public Map<String, Number> getMonthlySummary() {
        return monthlySummary;
    }

    public void setMonthlySummary(Map<String, Number> monthlySummary) {
        this.monthlySummary = monthlySummary;
    }
}
