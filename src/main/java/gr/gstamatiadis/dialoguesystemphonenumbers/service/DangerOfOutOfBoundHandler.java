package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.ScenariosForDigitGroupings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gr.gstamatiadis.dialoguesystemphonenumbers.service.PhoneNumberService.addNewScenarios;

@Component
public class DangerOfOutOfBoundHandler {


    private  CommonGroupDigitsHelper commonGroupDigitsHelper;
    private  GroupDigitsLength3Handler groupDigitsLength3Handler;
    private  GroupDigitsLength2Handler groupDigitsLength2Handler;

    public DangerOfOutOfBoundHandler() {
    }
    @Autowired
    public DangerOfOutOfBoundHandler(CommonGroupDigitsHelper commonGroupDigitsHelper, GroupDigitsLength3Handler groupDigitsLength3Handler,GroupDigitsLength2Handler groupDigitsLength2Handler){
        this.commonGroupDigitsHelper = commonGroupDigitsHelper;
        this.groupDigitsLength3Handler = groupDigitsLength3Handler;
        this.groupDigitsLength2Handler = groupDigitsLength2Handler;
    }

    protected boolean isDangerOutOfBounds(int arrayLength, int groupDigitsLength, int i) {
        Boolean length2Danger = arrayLength <= i + 1 && groupDigitsLength == 2;
        Boolean length3Danger = arrayLength <= i + 2 && groupDigitsLength == 3;

        return length2Danger || length3Danger;
    }

    protected ScenariosForDigitGroupings handleDangerOutOfBoundsCases(String[] arrOfStr, int i) {

        if (arrOfStr[i].length() == 2) {
            return handleLength2LastElement(arrOfStr[i]);
        }

        if (i + 1 == arrOfStr.length) {
            return handleLength3LastElement(arrOfStr[i]);
        }

        // Only case left is that i=2 == arrOfStr.length and group digit length 3
        return handleLength3SecondToLastElement(arrOfStr[i], arrOfStr[i + 1]);


    }

    private ScenariosForDigitGroupings handleLength3LastElement(String length3LastElement) {
        List<String> possibleScenariosForLast = new ArrayList<>();
        possibleScenariosForLast.add(commonGroupDigitsHelper.removeLast2Characters(length3LastElement) + "00");
        String removedLast2Characters = commonGroupDigitsHelper.removeLast2Characters(length3LastElement);
        possibleScenariosForLast.add(removedLast2Characters);
        addNewScenarios(possibleScenariosForLast);

        return handleLength2LastElement(commonGroupDigitsHelper.getLast2Characters(length3LastElement));

    }

    private  ScenariosForDigitGroupings handleLength3SecondToLastElement(String length3SecondToLastElement, String lastElement) {
        List<String> possibleScenariosForSecondLast = new ArrayList<>();
        if (length3SecondToLastElement.endsWith("00")) {
            if (lastElement.length() == 1) {
                possibleScenariosForSecondLast.add(length3SecondToLastElement + lastElement);
                String secondScenario = commonGroupDigitsHelper.replaceFinalDigit(length3SecondToLastElement, lastElement);

                possibleScenariosForSecondLast.add(secondScenario);

                return new ScenariosForDigitGroupings(possibleScenariosForSecondLast, 2);

            } else if (lastElement.length() == 2) {

                possibleScenariosForSecondLast.add(commonGroupDigitsHelper.removeLast2Characters(length3SecondToLastElement) + "00");
                String removedLast2Characters = commonGroupDigitsHelper.removeLast2Characters(length3SecondToLastElement);
                possibleScenariosForSecondLast.add(removedLast2Characters);
                addNewScenarios(possibleScenariosForSecondLast);
                ScenariosForDigitGroupings scenariosLast = handleLength2LastElement(lastElement);
                int groupsCovered = scenariosLast.getDigitGroupingsCovered() + 1;

                // The scenarios covered are the ones returned by handleLength2 method plus 1 (the scenarios for group Digits i)
                scenariosLast.setDigitGroupingsCovered(groupsCovered);
                return scenariosLast;
            } else {
                return new ScenariosForDigitGroupings(Collections.singletonList(length3SecondToLastElement), 1);
            }


        }
        // if the 3 length group doesn't end with 00 then there is no danger of out of bounds and no need for the i+2 group
        //So we can use the handleLength3 without the i+2 group
        return groupDigitsLength3Handler.handleLength3(length3SecondToLastElement, lastElement, null);
    }


    private  ScenariosForDigitGroupings handleLength2LastElement(String currentDigits) {

        if (currentDigits.endsWith("0")) {
            return new ScenariosForDigitGroupings(Collections.singletonList(currentDigits), 1);
        }

        return groupDigitsLength2Handler.scenariosNotEndsWithZero(currentDigits);
    }

}
