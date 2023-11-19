package gr.gstamatiadis.dialoguesystemphonenumbers.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScenariosForDigitGroupings {
    List<String> scenarios;
    int digitGroupingsCovered;

    public ScenariosForDigitGroupings(List<String> scenarios, int digitGroupingsCovered) {
        this.scenarios = scenarios;
        this.digitGroupingsCovered = digitGroupingsCovered;
    }

}

