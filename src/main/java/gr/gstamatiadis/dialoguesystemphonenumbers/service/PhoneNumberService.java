package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.ScenariosForDigitGroupings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class PhoneNumberService {

    static List<String> possibleScenarios = Collections.singletonList("");

    private final DangerOfOutOfBoundHandler dangerOfOutOfBoundHandler;
    private final GroupDigitsLength3Handler groupDigitsLength3Handler;
    private final GroupDigitsLength2Handler groupDigitsLength2Handler;

    @Autowired
    public PhoneNumberService(DangerOfOutOfBoundHandler dangerOfOutOfBoundHandler, GroupDigitsLength3Handler groupDigitsLength3Handler, GroupDigitsLength2Handler groupDigitsLength2Handler) {
        this.dangerOfOutOfBoundHandler = dangerOfOutOfBoundHandler;
        this.groupDigitsLength3Handler = groupDigitsLength3Handler;
        this.groupDigitsLength2Handler = groupDigitsLength2Handler;
    }


    public List<String> getAllPossibleScenarios(String phoneNumber) {


        String[] arrOfGroupDigits = phoneNumber.split(" ");

        // variable i is the iterator that counts how many group digits were processed
        int i = 0;
        int groupDigits = arrOfGroupDigits.length;

        while (i < groupDigits) {


            if (arrOfGroupDigits[i].length() == 2 && (i + 1) < arrOfGroupDigits.length) {
                ScenariosForDigitGroupings scenariosI = groupDigitsLength2Handler.handleLength2(arrOfGroupDigits[i], arrOfGroupDigits[i + 1]);
                addNewScenarios(scenariosI.getScenarios());


                i += scenariosI.getDigitGroupingsCovered();
            } else if (arrOfGroupDigits[i].length() == 3 && (i + 2) < arrOfGroupDigits.length) {
                ScenariosForDigitGroupings scenariosI = groupDigitsLength3Handler.handleLength3(arrOfGroupDigits[i], arrOfGroupDigits[i + 1], arrOfGroupDigits[i + 2]);

                addNewScenarios(scenariosI.getScenarios());

                i += scenariosI.getDigitGroupingsCovered();

            } else if (dangerOfOutOfBoundHandler.isDangerOutOfBounds(arrOfGroupDigits.length, arrOfGroupDigits[i].length(), i)) {

                ScenariosForDigitGroupings scenariosI = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfGroupDigits, i);
                addNewScenarios(scenariosI.getScenarios());

                i += scenariosI.getDigitGroupingsCovered();

            } else {

                addNewScenarios(Collections.singletonList(arrOfGroupDigits[i]));

                i += 1;

            }
        }

        for (String possibleScenario : possibleScenarios) {
            log.info(possibleScenario);
        }

        return possibleScenarios;

    }


    protected static void addNewScenarios(List<String> possibleScenariosForI) {
        //Combining the scenarios until now with the scenarios of digit grouping i

        List<String> result = new ArrayList<>();

        for (String scenarioUntilI : possibleScenarios) {
            for (String scenarioI : possibleScenariosForI) {

                result.add(scenarioUntilI + scenarioI);

            }

        }

        possibleScenarios = result;
    }


}




