package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class CommonGroupDigitsHelper {

    protected boolean isNotPhoneticallyUniqueNumber(String s) {
        return !s.equals("11") && !s.equals("12");
    }

    protected String replaceFinalDigit(String currentDigits, String nextDigits) {
        return currentDigits.substring(0, currentDigits.length() - 1) + nextDigits;
    }

    protected String removeLast2Characters(String string) {
        return string.substring(0, string.length() - 2);
    }

    protected String getLast2Characters(String string) {
        return string.substring(string.length() - 2);
    }


    protected List<String> combineGroupDigitsScenarios(List<String> pastGroupDigitsScenarios, List<String> presentGroupDigitsScenarios) {
        //Combining the scenarios until now with the scenarios of digit grouping i

        List<String> combinedScenarios = new ArrayList<>();

        for (String scenarioUntilI : pastGroupDigitsScenarios) {
            for (String scenarioI : presentGroupDigitsScenarios) {

                combinedScenarios.add(scenarioUntilI + scenarioI);

            }

        }

        return combinedScenarios;
    }
}
