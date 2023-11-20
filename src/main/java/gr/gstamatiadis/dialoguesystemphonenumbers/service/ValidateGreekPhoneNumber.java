package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.PhoneNumberResponse;
import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.PhoneScenario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ValidateGreekPhoneNumber {

    protected PhoneNumberResponse validateAllPhoneScenarios(List<String> phoneScenarios) {
        List<PhoneScenario> phoneScenarioList = new ArrayList<>();

        int i = 1;
        for (String phoneScenario : phoneScenarios) {


            phoneScenarioList.add(new PhoneScenario(phoneScenario, isValidGreekPhoneNumber(phoneScenario)));

            if (isValidGreekPhoneNumber(phoneScenario)) {
                log.info("Interpretation " + i + ": " + phoneScenario + " [phone number: VALID] ");
            } else {
                log.info("Interpretation " + i + ": " + phoneScenario + " [phone number: INVALID] ");
            }
            i++;

        }
        log.info("End of Interpretations");
        return new PhoneNumberResponse(phoneScenarioList);
    }

    private boolean isValidGreekPhoneNumber(String phoneScenario) {

        boolean isLength10ValidPhone = phoneScenario.length() == 10 && (phoneScenario.startsWith("2") || phoneScenario.startsWith("69"));

        boolean isLength14ValidPhone = phoneScenario.length() == 14 && (phoneScenario.startsWith("00302") || phoneScenario.startsWith("003069"));


        return isLength10ValidPhone || isLength14ValidPhone;
    }
}
