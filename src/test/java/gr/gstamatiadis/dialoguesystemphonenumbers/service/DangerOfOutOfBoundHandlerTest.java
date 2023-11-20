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

import static gr.gstamatiadis.dialoguesystemphonenumbers.service.GroupDigitsLength3HandlerTest.createExampleScenario;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DangerOfOutOfBoundHandlerTest {

    @Mock
    private CommonGroupDigitsHelper commonGroupDigitsHelper;

    @Mock
    private GroupDigitsLength2Handler groupDigitsLength2Handler;


    @InjectMocks
    private DangerOfOutOfBoundHandler dangerOfOutOfBoundHandler;

    @Test
    void test_isDangerOutOfBounds_Method() {


        Assertions.assertAll("Test isDangerOutOfBounds method",
                () -> Assertions.assertFalse(dangerOfOutOfBoundHandler.isDangerOutOfBounds(6, 2, 4), "It's second to last group digit with  length 2, so no danger of out of bounds"),
                () -> Assertions.assertTrue(dangerOfOutOfBoundHandler.isDangerOutOfBounds(6, 3, 4), "It's second to last group digit with  length 3, so it is in danger of out of bounds (for example for digits '800 50 4')"),
                () -> Assertions.assertTrue(dangerOfOutOfBoundHandler.isDangerOutOfBounds(6, 3, 5), "It's last group digit with  length 3, so it is in danger of out of bounds (for example for digit '850')"),
                () -> Assertions.assertFalse(dangerOfOutOfBoundHandler.isDangerOutOfBounds(6, 3, 3), "It has to more group digits ahead of it so no danger of out of bounds "),
                () -> Assertions.assertTrue(dangerOfOutOfBoundHandler.isDangerOutOfBounds(6, 2, 5), "It's last group digit with  length 2, so it is in danger of out of bounds (for example if it was 50 it would look for the next element)")
        );
    }

    @Test
    void test_handleDangerOutOfBoundsCases_length2_endsWithZero() {


        String[] arrOfStr = {"1", "20"};
        ScenariosForDigitGroupings scenarios = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfStr, 1);

        Assertions.assertAll("Test handleDangerOutOfBoundsCases method for length 2 that ends zero",
                () -> Assertions.assertEquals("20", scenarios.getScenarios().get(0), "Only One scenario for 20 when it is last, no other possible scenario"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "Only One group covered since there is only one remaining")
        );
    }

    @Test
    void test_handleDangerOutOfBoundsCases_length2_noZero() {


        String[] arrOfStr = {"1", "24"};
        when(groupDigitsLength2Handler.scenariosNotEndsWithZero("24")).thenReturn(createExampleScenario("24", "204", 1));
        ScenariosForDigitGroupings scenarios = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfStr, 1);

        Assertions.assertAll("Test handleDangerOutOfBoundsCases method for length 2 with no zeroes",
                () -> Assertions.assertEquals("24", scenarios.getScenarios().get(0), "We expect the first element to come as mocked from expected list 24"),
                () -> Assertions.assertEquals("204", scenarios.getScenarios().get(1), "We expect the first element to come as mocked from expected list 204"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "Only One group covered since there is only one remaining")
        );
    }

    @Test
    void test_handleDangerOutOfBoundsCases_length3_lastElement() {
        String[] arrOfStr = {"1", "753"};
        List<String> previousScenarios = Arrays.asList("700", "7");
        List<String> presentScenarios = Arrays.asList("503", "53");
        when(commonGroupDigitsHelper.removeLast2Characters("753")).thenReturn("7").thenReturn("7");
        when(commonGroupDigitsHelper.getLast2Characters("753")).thenReturn("53");
        List<String> expected = Arrays.asList("753", "7503", "700503");
        when(groupDigitsLength2Handler.scenariosNotEndsWithZero("53")).thenReturn(createExampleScenario("503", "53", 1));
        when(commonGroupDigitsHelper.combineGroupDigitsScenarios(previousScenarios, presentScenarios)).thenReturn(expected);

        ScenariosForDigitGroupings scenarios = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfStr, 1);
        Assertions.assertAll("Test handleDangerOutOfBoundsCases last group with length 3",
                () -> Assertions.assertEquals("753", scenarios.getScenarios().get(0), "It should return the list returned by combine elements"),
                () -> Assertions.assertEquals("7503", scenarios.getScenarios().get(1), "It should return the list returned by combine elements"),
                () -> Assertions.assertEquals("700503", scenarios.getScenarios().get(2), "It should return the list returned by combine elements"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "Only One group covered since there is only one remaining")
        );
    }


    @Test
    void test_handleDangerOutOfBoundsCases_length3_secondToLastElement_DoubleZeroes_nextGroupLength1() {
        String[] arrOfStr = {"700", "5"};
        when(commonGroupDigitsHelper.replaceFinalDigit("700", "5")).thenReturn("705");

        ScenariosForDigitGroupings scenarios = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfStr, 0);
        Assertions.assertAll("Test handleDangerOutOfBoundsCases, length 3 ends with zeroes, second to last, next group length 1",
                () -> Assertions.assertEquals("7005", scenarios.getScenarios().get(0), "The first scenario is the two group digits combined '7005'"),
                () -> Assertions.assertEquals("705", scenarios.getScenarios().get(1), "The second scenario is what is returned from  '705'"),
                () -> Assertions.assertEquals(2, scenarios.getDigitGroupingsCovered(), "Two group digits covered '700' and '5')")
        );
    }


    @Test
    void test_handleDangerOutOfBoundsCases_length3_secondToLastElement_DoubleZeroes_nextGroupLength3() {
        String[] arrOfStr = {"700", "500"};


        ScenariosForDigitGroupings scenarios = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfStr, 0);
        Assertions.assertAll("Test handleDangerOutOfBoundsCases, length 3 ends with zeroes, second to last, next group length 3",
                () -> Assertions.assertEquals("700", scenarios.getScenarios().get(0), "We expect 700"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "One group digits covered '700' )")
        );
    }

    @Test
    void test_handleDangerOutOfBoundsCases_length3_secondToLastElement_DoubleZeroes_nextGroupLength2() {
        String[] arrOfStr = {"700", "53"};
        List<String> previousScenarios = Arrays.asList("700", "7");
        List<String> presentScenarios = Arrays.asList("503", "53");
        when(commonGroupDigitsHelper.removeLast2Characters("700")).thenReturn("7").thenReturn("7");

        List<String> expected = Arrays.asList("753", "7503", "700503");
        when(groupDigitsLength2Handler.scenariosNotEndsWithZero("53")).thenReturn(createExampleScenario("503", "53", 1));
        when(commonGroupDigitsHelper.combineGroupDigitsScenarios(previousScenarios, presentScenarios)).thenReturn(expected);


        ScenariosForDigitGroupings scenarios = dangerOfOutOfBoundHandler.handleDangerOutOfBoundsCases(arrOfStr, 0);
        Assertions.assertAll("Test handleDangerOutOfBoundsCases, length 3 ends with zeroes, second to last, next group length 2",
                () -> Assertions.assertEquals("753", scenarios.getScenarios().get(0), "It should return the list returned by combine elements"),
                () -> Assertions.assertEquals("7503", scenarios.getScenarios().get(1), "It should return the list returned by combine elements"),
                () -> Assertions.assertEquals("700503", scenarios.getScenarios().get(2), "It should return the list returned by combine elements"),
                () -> Assertions.assertEquals(2, scenarios.getDigitGroupingsCovered(), "Two group digits covered '700' and '53')")
        );
    }

}