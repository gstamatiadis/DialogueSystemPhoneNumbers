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

    private DangerOfOutOfBoundHandler dangerOfOutOfBoundHandler;
    private GroupDigitsLength3Handler groupDigitsLength3Handler;
    private GroupDigitsLength2Handler groupDigitsLength2Handler;

    @Autowired
    public PhoneNumberService(DangerOfOutOfBoundHandler dangerOfOutOfBoundHandler, GroupDigitsLength3Handler groupDigitsLength3Handler, GroupDigitsLength2Handler groupDigitsLength2Handler) {
        this.dangerOfOutOfBoundHandler = dangerOfOutOfBoundHandler;
        this.groupDigitsLength3Handler = groupDigitsLength3Handler;
        this.groupDigitsLength2Handler = groupDigitsLength2Handler;
    }


    public List<String> getAllPossibleScenarios(String phoneNumber) {


        String[] arrOfStr = phoneNumber.split(" ");

        for (int i = 0; i < arrOfStr.length; i++) {


            if (arrOfStr[i].length() == 2 && (i + 1) < arrOfStr.length) {
                ScenariosForDigitGroupings scenariosI = groupDigitsLength2Handler.handleLength2(arrOfStr[i], arrOfStr[i + 1]);
                addNewScenarios(scenariosI.getScenarios());

                //For example if we have covered the scenarios of three digit groups then we add 2 to the iterator to start from the next unchecked digit grouping
                i += scenariosI.getDigitGroupingsCovered() - 1;
            } else if (arrOfStr[i].length() == 3 && (i + 2) < arrOfStr.length) {
                ScenariosForDigitGroupings scenariosI = groupDigitsLength3Handler.handleLength3(arrOfStr[i], arrOfStr[i + 1], arrOfStr[i + 2]);

                addNewScenarios(scenariosI.getScenarios());

                //For example if we have covered the scenarios of three digit groups then we add 2 to the iterator to start from the next unchecked digit grouping
                i += scenariosI.getDigitGroupingsCovered() - 1;

            } else if (dangerOfOutOfBoundHandler.isDangerOutOfBounds(arrOfStr.length, arrOfStr[i].length(), i)) {

                ScenariosForDigitGroupings scenariosI = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfStr, i);
                addNewScenarios(scenariosI.getScenarios());

                //For example if we have covered the scenarios of three digit groups then we add 2 to the iterator to start from the next unchecked digit grouping
                i += scenariosI.getDigitGroupingsCovered() - 1;

            } else {

                addNewScenarios(Collections.singletonList(arrOfStr[i]));

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




