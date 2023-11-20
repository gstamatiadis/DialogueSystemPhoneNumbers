package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.ScenariosForDigitGroupings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class GroupDigitsLength2HandlerTest {

    @Mock
    private CommonGroupDigitsHelper commonGroupDigitsHelper;


    @InjectMocks
    private GroupDigitsLength2Handler groupDigitsLength2Handler;

    @Test
    void test_handleLength2_noZeroes_NoPhoneticallyUnique() {

        when(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("25")).thenReturn(true);
        ScenariosForDigitGroupings scenarios = groupDigitsLength2Handler.handleLength2("25", "2");


        Assertions.assertAll("Test handleLength2 where group digit has no zeroes and is not phonetically unique",
                () -> Assertions.assertEquals("25", scenarios.getScenarios().get(0), "first scenario for group digit 25 is '25'"),
                () -> Assertions.assertEquals("205", scenarios.getScenarios().get(1), "second scenario for group digit 25 is '205'"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "only '25' group digits was covered")

        );
    }


    @Test
    void test_handleLength2_noZeroes_PhoneticallyUnique() {
        when(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("12")).thenReturn(false);
        ScenariosForDigitGroupings scenarios = groupDigitsLength2Handler.handleLength2("12", "2");

        Assertions.assertAll("Test handleLength2 where group digit has no zeroes and is phonetically unique",
                () -> Assertions.assertEquals("12", scenarios.getScenarios().get(0), "first scenario for group digit 12 is '12'"),
                () -> Assertions.assertEquals(1, scenarios.getScenarios().size(), "there is no other scenario as 12 and 10 2 dont sound the same in greek"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "only '12' group digits was covered")

        );
    }

    @Test
    void test_handleLength2_EndsWithZero_NoPhoneticallyUnique_NextGroupLength1() {
        when(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("52")).thenReturn(true);
        when(commonGroupDigitsHelper.replaceFinalDigit("50", "2")).thenReturn("52");
        ScenariosForDigitGroupings scenarios = groupDigitsLength2Handler.handleLength2("50", "2");



        Assertions.assertAll("Test handleLength2 where group digit ends with zero is not phonetically unique and next group has length 1",
                () -> Assertions.assertEquals("52", scenarios.getScenarios().get(0), "first scenario for group digit 52 is '52'"),
                () -> Assertions.assertEquals("502", scenarios.getScenarios().get(1), "second scenario for group digit 52 is '502'"),
                () -> Assertions.assertEquals(2, scenarios.getDigitGroupingsCovered(), "The group digits were covered '50' and '2'")

        );
    }

    @Test
    void test_handleLength2_EndsWithZero_PhoneticallyUnique_NextGroupLength1() {


        when(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("12")).thenReturn(false);
        when(commonGroupDigitsHelper.replaceFinalDigit("10", "2")).thenReturn("12");

        ScenariosForDigitGroupings scenarios = groupDigitsLength2Handler.handleLength2("10", "2");


        Assertions.assertAll("Test handleLength2 where group digit ends with zero, next group has length 1 and their combination is phonetically unique",
                () -> Assertions.assertEquals("10", scenarios.getScenarios().get(0), "first scenario for group digit 10 followed by 2 is '10' we ignore 2 as the are not combined. We will handle it in the next iteration"),
                () -> Assertions.assertEquals(1, scenarios.getScenarios().size(), "there is no other scenario as 12 and 10 2 dont sound the same in greek"),
                () -> Assertions.assertEquals(1, scenarios.getDigitGroupingsCovered(), "Only group digit  '10' was covered")

        );
    }
}