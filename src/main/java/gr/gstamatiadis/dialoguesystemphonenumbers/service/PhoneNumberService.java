package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.PhoneNumberResponse;
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



    private final DangerOfOutOfBoundHandler dangerOfOutOfBoundHandler;
    private final GroupDigitsLength3Handler groupDigitsLength3Handler;
    private final GroupDigitsLength2Handler groupDigitsLength2Handler;
    private final CommonGroupDigitsHelper commonGroupDigitsHelper;
    private final ValidateGreekPhoneNumber validateGreekPhoneNumber;

    @Autowired
    public PhoneNumberService(DangerOfOutOfBoundHandler dangerOfOutOfBoundHandler, GroupDigitsLength3Handler groupDigitsLength3Handler, GroupDigitsLength2Handler groupDigitsLength2Handler, ValidateGreekPhoneNumber validateGreekPhoneNumber,CommonGroupDigitsHelper commonGroupDigitsHelper) {
        this.dangerOfOutOfBoundHandler = dangerOfOutOfBoundHandler;
        this.groupDigitsLength3Handler = groupDigitsLength3Handler;
        this.groupDigitsLength2Handler = groupDigitsLength2Handler;
        this.commonGroupDigitsHelper = commonGroupDigitsHelper;
        this.validateGreekPhoneNumber = validateGreekPhoneNumber;

    }


    public PhoneNumberResponse getAllPossibleScenarios(String phoneNumber) {

        List<String> possibleScenarios = Collections.singletonList("");
        String[] arrOfGroupDigits = phoneNumber.split(" ");

        // variable i is the iterator that counts how many group digits were processed
        int i = 0;
        int groupDigits = arrOfGroupDigits.length;

        while (i < groupDigits) {


            if (arrOfGroupDigits[i].length() == 2 && (i + 1) < arrOfGroupDigits.length) {
                ScenariosForDigitGroupings scenariosI = groupDigitsLength2Handler.handleLength2(arrOfGroupDigits[i], arrOfGroupDigits[i + 1]);
                possibleScenarios = commonGroupDigitsHelper.combineGroupDigitsScenarios(possibleScenarios,scenariosI.getScenarios());


                i += scenariosI.getDigitGroupingsCovered();
            } else if (arrOfGroupDigits[i].length() == 3 && (i + 2) < arrOfGroupDigits.length) {
                ScenariosForDigitGroupings scenariosI = groupDigitsLength3Handler.handleLength3(arrOfGroupDigits[i], arrOfGroupDigits[i + 1], arrOfGroupDigits[i + 2]);

                possibleScenarios = commonGroupDigitsHelper.combineGroupDigitsScenarios(possibleScenarios,scenariosI.getScenarios());

                i += scenariosI.getDigitGroupingsCovered();

            } else if (dangerOfOutOfBoundHandler.isDangerOutOfBounds(arrOfGroupDigits.length, arrOfGroupDigits[i].length(), i)) {

                ScenariosForDigitGroupings scenariosI = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfGroupDigits, i);
                possibleScenarios = commonGroupDigitsHelper.combineGroupDigitsScenarios(possibleScenarios,scenariosI.getScenarios());

                i += scenariosI.getDigitGroupingsCovered();

            } else {

                possibleScenarios = commonGroupDigitsHelper.combineGroupDigitsScenarios(possibleScenarios, Collections.singletonList(arrOfGroupDigits[i]));

                i += 1;

            }
        }

        for (String possibleScenario : possibleScenarios) {
            log.info(possibleScenario);
        }

        return validateGreekPhoneNumber.validateAllPhoneScenarios(possibleScenarios);

    }





}




