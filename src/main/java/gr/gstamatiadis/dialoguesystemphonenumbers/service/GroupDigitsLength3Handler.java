package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.ScenariosForDigitGroupings;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@NoArgsConstructor
public class GroupDigitsLength3Handler {

    private CommonGroupDigitsHelper commonGroupDigitsHelper;
    private GroupDigitsLength2Handler groupDigitsLength2Handler;


    @Autowired
    public GroupDigitsLength3Handler(CommonGroupDigitsHelper commonGroupDigitsHelper, GroupDigitsLength2Handler groupDigitsLength2Handler) {
        this.commonGroupDigitsHelper = commonGroupDigitsHelper;
        this.groupDigitsLength2Handler = groupDigitsLength2Handler;
    }

    protected ScenariosForDigitGroupings handleLength3(String groupDigitsI, String groupDigitsIPlus1, String groupDigitsIPlus2) {


        if (groupDigitsI.endsWith("00")) {


            return handleLength3EndsWithDoubleZero(groupDigitsI, groupDigitsIPlus1, groupDigitsIPlus2);
        }

        return handleLength3NotEndsWithDoubleZero(groupDigitsI, groupDigitsIPlus1);
    }

    private ScenariosForDigitGroupings handleLength3EndsWithDoubleZero(String groupDigitsI, String groupDigitsIPlus1, String groupDigitsIPlus2) {
        List<String> possibleScenariosForI = new ArrayList<>();

        if (groupDigitsIPlus1.length() == 1) {
            possibleScenariosForI.add(groupDigitsI + groupDigitsIPlus1);
            String secondScenario = commonGroupDigitsHelper.replaceFinalDigit(groupDigitsI, groupDigitsIPlus1);


            possibleScenariosForI.add(secondScenario);
            //Since we got all the scenario for group digits i and i+1, digitGroupingsCovered = 2
            return new ScenariosForDigitGroupings(possibleScenariosForI, 2);
        } else if (groupDigitsIPlus1.length() == 2) {
            // **Important** This is a crucial part for this application since it is used a lot of times for group digits length 3
            // Let's say we have group digits "800 50 3". Then the possible combinations are "800 50 3", "800 53" and "8 50 3" , "8 53"
            //Notice that the scenarios after 8 and 800 are the same. We take advantage of that to make the problem simpler and make use of handle length2 methods
            possibleScenariosForI.add(groupDigitsI);
            String removedDoubleZero = commonGroupDigitsHelper.removeLast2Characters(groupDigitsI);
            possibleScenariosForI.add(removedDoubleZero);


            ScenariosForDigitGroupings scenariosI = groupDigitsLength2Handler.handleLength2(groupDigitsIPlus1, groupDigitsIPlus2);
            int groupsCovered = scenariosI.getDigitGroupingsCovered() + 1;

            // The scenarios covered are the ones returned by handleLength2 method plus 1 (the scenarios for group Digits i)
            scenariosI.setDigitGroupingsCovered(groupsCovered);
            List<String> combinedScenarios = commonGroupDigitsHelper.combineGroupDigitsScenarios(possibleScenariosForI, scenariosI.getScenarios());
            scenariosI.setScenarios(combinedScenarios);
            return scenariosI;

        } else {
            possibleScenariosForI.add(groupDigitsI);
            return new ScenariosForDigitGroupings(possibleScenariosForI, 1);
        }


    }

    protected ScenariosForDigitGroupings handleLength3NotEndsWithDoubleZero(String groupDigitsI, String groupDigitsIPlus1) {
        List<String> possibleScenariosForI = new ArrayList<>();
        if (groupDigitsI.endsWith("0")) {


            possibleScenariosForI.add(commonGroupDigitsHelper.removeLast2Characters(groupDigitsI) + "00");
            String removedLast2Characters = commonGroupDigitsHelper.removeLast2Characters(groupDigitsI);
            possibleScenariosForI.add(removedLast2Characters);

            ScenariosForDigitGroupings scenariosI = groupDigitsLength2Handler.handleLength2(commonGroupDigitsHelper.getLast2Characters(groupDigitsI), groupDigitsIPlus1);
            int groupsCovered = scenariosI.getDigitGroupingsCovered();

            // The scenarios covered are the ones returned by handleLength2 method plus 1 (the scenarios for group Digits i)
            scenariosI.setDigitGroupingsCovered(groupsCovered);
            List<String> combinedScenarios = commonGroupDigitsHelper.combineGroupDigitsScenarios(possibleScenariosForI, scenariosI.getScenarios());
            scenariosI.setScenarios(combinedScenarios);
            return scenariosI;


        } else if (groupDigitsI.charAt(1) == '0') {

            possibleScenariosForI.add(groupDigitsI);
            String secondScenario = commonGroupDigitsHelper.replaceFinalDigit(groupDigitsI, "0") + groupDigitsI.substring(groupDigitsI.length() - 1);
            possibleScenariosForI.add(secondScenario);

            return new ScenariosForDigitGroupings(possibleScenariosForI, 1);
        } else {


            possibleScenariosForI.add(commonGroupDigitsHelper.removeLast2Characters(groupDigitsI) + "00");
            String removedLast2Characters = commonGroupDigitsHelper.removeLast2Characters(groupDigitsI);
            possibleScenariosForI.add(removedLast2Characters);

            ScenariosForDigitGroupings scenariosI = groupDigitsLength2Handler.scenariosNotEndsWithZero(commonGroupDigitsHelper.getLast2Characters(groupDigitsI));
            int groupsCovered = scenariosI.getDigitGroupingsCovered();

            // The scenarios covered are the ones returned by handleLength2 method plus 1 (the scenarios for group Digits i)
            scenariosI.setDigitGroupingsCovered(groupsCovered);
            List<String> combinedScenarios = commonGroupDigitsHelper.combineGroupDigitsScenarios(possibleScenariosForI, scenariosI.getScenarios());
            scenariosI.setScenarios(combinedScenarios);
            return scenariosI;


        }
    }


}
