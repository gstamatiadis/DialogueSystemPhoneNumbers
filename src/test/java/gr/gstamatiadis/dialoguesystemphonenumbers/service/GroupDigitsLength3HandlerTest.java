package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.ScenariosForDigitGroupings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroupDigitsLength3HandlerTest {


    @Mock
    private CommonGroupDigitsHelper commonGroupDigitsHelper;

    @Mock
    private GroupDigitsLength2Handler groupDigitsLength2Handler;


    @InjectMocks
    private GroupDigitsLength3Handler groupDigitsLength3Handler;


    @Test
    void testHandleLength3_length3EndsWithDoubleZero_nextGroupLength1() {

        /*        when(commonGroupDigitsHelper.removeLast2Characters("800")).thenReturn("8");*/
        when(commonGroupDigitsHelper.replaceFinalDigit("800", "5")).thenReturn("805");

        ScenariosForDigitGroupings scenarios = groupDigitsLength3Handler.handleLength3("800", "5", "32");
        Assertions.assertAll("Test handleLength3 method next group of digits has length 1",
                () -> Assertions.assertEquals("8005", scenarios.getScenarios().get(0), "The first combination between 800 and 5 is 8005"),
                () -> Assertions.assertEquals("805", scenarios.getScenarios().get(1), "The second combination between 800 and 5 is 805"),
                () -> Assertions.assertEquals(2, scenarios.getDigitGroupingsCovered(), "Two group digits were covered (800 and 5)")
        );
    }

    @Test
    void testHandleLength3_length3EndsWithDoubleZero_nextGroupLength2() {

        List<String> previousScenarios = Arrays.asList("800", "8");
        List<String> presentScenarios = Arrays.asList("802", "82");
        List<String> expected = Arrays.asList("800802", "80082", "8802", "882");
        when(commonGroupDigitsHelper.removeLast2Characters("800")).thenReturn("8");
        when(groupDigitsLength2Handler.handleLength2("82", "32")).thenReturn(createExampleScenario("802", "82", 1));

        when(commonGroupDigitsHelper.combineGroupDigitsScenarios(previousScenarios, presentScenarios)).thenReturn(expected);


        ScenariosForDigitGroupings scenarios = groupDigitsLength3Handler.handleLength3("800", "82", "32");
        Assertions.assertAll("Test handleLength3 method next group of digits has length 2",
                () -> Assertions.assertEquals("800802", scenarios.getScenarios().get(0), "We expect the first element to come as mocked from expected list"),
                () -> Assertions.assertEquals(2, scenarios.getDigitGroupingsCovered(), "Two group digits were covered (800 and 82). The method should get 1 returned from handleLength2 and add 1")

        );
    }

    @Test
    void testHandleLength3_length3EndsWithDoubleZero_nextGroupLength3() {


        ScenariosForDigitGroupings scenarios = groupDigitsLength3Handler.handleLength3("800", "823", "32");
        Assertions.assertAll("Test handleLength3 method next group of digits has length 3 ",
                () -> Assertions.assertEquals("800", scenarios.getScenarios().get(0), "We expect the first and only element to come as 800 because the next group is of length 3"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "One group of digits covered (800)")

        );
    }


    @Test
    void testHandleLength3_length3EndsWithOneZero_nextGroupLength1() {

        List<String> previousScenarios = Arrays.asList("700", "7");
        List<String> presentScenarios = Arrays.asList("505", "55");
        List<String> expected = Arrays.asList("700505", "70055", "7505", "755");
        when(commonGroupDigitsHelper.removeLast2Characters("750")).thenReturn("7").thenReturn("7");
        when(commonGroupDigitsHelper.getLast2Characters("750")).thenReturn("50");
        when(groupDigitsLength2Handler.handleLength2("50", "5")).thenReturn(createExampleScenario("505", "55", 2));

        when(commonGroupDigitsHelper.combineGroupDigitsScenarios(previousScenarios, presentScenarios)).thenReturn(expected);


        ScenariosForDigitGroupings scenarios = groupDigitsLength3Handler.handleLength3("750", "5", "32");
        Assertions.assertAll("Test handleLength3 with group digit that ends with one zero and next group of digits has length 1",
                () -> Assertions.assertEquals("700505", scenarios.getScenarios().get(0), "We expect the first element to come as mocked from expected list"),
                () -> Assertions.assertEquals(2, scenarios.getDigitGroupingsCovered(), "Two group digits were covered (750 and 5). The method should get 2 since it understand that it covered 50 and 5 so no adding one is needed")

        );
    }

    @Test
    void testHandleLength3_length3ZeroInTheMiddle() {


        when(commonGroupDigitsHelper.replaceFinalDigit("705", "0")).thenReturn("700");


        ScenariosForDigitGroupings scenarios = groupDigitsLength3Handler.handleLength3("705", "5", "32");
        Assertions.assertAll("Test handleLength3 with group digit that zero in the middle",
                () -> Assertions.assertEquals("705", scenarios.getScenarios().get(0), "We expect the first element to come 705 which the original group digit"),
                () -> Assertions.assertEquals("7005", scenarios.getScenarios().get(1), "We expect the second element to come 7005 "),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "One group digits was covered (705). ")

        );
    }

    @Test
    void testHandleLength3_length3NoZero() {

        List<String> previousScenarios = Arrays.asList("600", "6");
        List<String> presentScenarios = Arrays.asList("403", "43");
        List<String> expected = Arrays.asList("60043", "600403", "643", "6403");
        when(commonGroupDigitsHelper.removeLast2Characters("643")).thenReturn("6").thenReturn("6");
        when(commonGroupDigitsHelper.getLast2Characters("643")).thenReturn("43");
        when(groupDigitsLength2Handler.scenariosNotEndsWithZero("43")).thenReturn(createExampleScenario("403", "43", 1));

        when(commonGroupDigitsHelper.combineGroupDigitsScenarios(previousScenarios, presentScenarios)).thenReturn(expected);


        ScenariosForDigitGroupings scenarios = groupDigitsLength3Handler.handleLength3("643", "5", "32");
        Assertions.assertAll("Test handleLength3 with group digit that has no zero",
                () -> Assertions.assertEquals("60043", scenarios.getScenarios().get(0), "We expect the first element to come as mocked from expected list"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "One group digits was covered (643). ")

        );
    }

    static ScenariosForDigitGroupings createExampleScenario(String scenario1, String scenario2, int scenariosCovered) {
        return new ScenariosForDigitGroupings(Arrays.asList(scenario1, scenario2), scenariosCovered);
    }

}
