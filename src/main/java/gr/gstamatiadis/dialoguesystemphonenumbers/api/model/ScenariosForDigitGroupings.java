package gr.gstamatiadis.dialoguesystemphonenumbers.api.model;

import java.util.List;

public class ScenariosForDigitGroupings {
    List<String> scenarios;
    int digitGroupingsCovered;

    public ScenariosForDigitGroupings(List<String> scenarios, int digitGroupingsCovered) {
        this.scenarios = scenarios;
        this.digitGroupingsCovered = digitGroupingsCovered;
    }

    public List<String> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<String> scenarios) {
        this.scenarios = scenarios;
    }

    public int getDigitGroupingsCovered() {
        return digitGroupingsCovered;
    }

    public void setDigitGroupingsCovered(int digitGroupingsCovered) {
        this.digitGroupingsCovered = digitGroupingsCovered;
    }
}

