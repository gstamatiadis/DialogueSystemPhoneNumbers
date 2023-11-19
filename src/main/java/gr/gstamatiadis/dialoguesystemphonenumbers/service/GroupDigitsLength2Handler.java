package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.ScenariosForDigitGroupings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupDigitsLength2Handler {

    private CommonGroupDigitsHelper commonGroupDigitsHelper;


    public GroupDigitsLength2Handler() {
    }

    @Autowired
    public GroupDigitsLength2Handler(CommonGroupDigitsHelper commonGroupDigitsHelper) {
        this.commonGroupDigitsHelper = commonGroupDigitsHelper;
    }

    protected ScenariosForDigitGroupings handleLength2(String currentDigits, String nextDigits) {
        if (currentDigits.endsWith("0")) {
            return scenariosEndsWithZero(currentDigits, nextDigits);
        }

        return scenariosNotEndsWithZero(currentDigits);

    }


    protected ScenariosForDigitGroupings scenariosNotEndsWithZero(String currentDigits) {
        List<String> possibleScenariosForI = new ArrayList<>();
        possibleScenariosForI.add(currentDigits);
        if (commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber(currentDigits)) {
            String zeroInTheMiddle = currentDigits.charAt(0) + "0" + currentDigits.charAt(1);
            //For example 25 becomes 20 5 (and the space isn't needed since it is a phone number)
            possibleScenariosForI.add(zeroInTheMiddle);
        }

        return new ScenariosForDigitGroupings(possibleScenariosForI, 1);
    }

    protected ScenariosForDigitGroupings scenariosEndsWithZero(String currentDigits, String nextDigits) {

        List<String> possibleScenariosForI = new ArrayList<>();


        if (nextDigits.length() == 1 && commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber(commonGroupDigitsHelper.replaceFinalDigit(currentDigits, nextDigits))) {
            String secondScenario = commonGroupDigitsHelper.replaceFinalDigit(currentDigits, nextDigits);


            possibleScenariosForI.add(secondScenario);
            possibleScenariosForI.add(currentDigits + nextDigits);

            return new ScenariosForDigitGroupings(possibleScenariosForI, 2);
        }
        possibleScenariosForI.add(currentDigits);
        return new ScenariosForDigitGroupings(possibleScenariosForI, 1);
    }

}
