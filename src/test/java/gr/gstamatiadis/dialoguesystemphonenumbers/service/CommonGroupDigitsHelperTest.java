package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommonGroupDigitsHelperTest {

    private CommonGroupDigitsHelper commonGroupDigitsHelper;

    @BeforeEach
    void setUp() {
        commonGroupDigitsHelper = new CommonGroupDigitsHelper();
    }

    @Test
    void testIsNotPhoneticallyUniqueNumber() {

        Assertions.assertAll("Test IsNotPhoneticallyUniqueNumber method ",
                () -> Assertions.assertFalse(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("12"), "12 is phonetically unique in greek"),
                () -> Assertions.assertFalse(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("11"), "12 is phonetically unique in greek"),
                () -> Assertions.assertTrue(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("13"), "13 is NOT phonetically unique in greek"),
                () -> Assertions.assertTrue(commonGroupDigitsHelper.isNotPhoneticallyUniqueNumber("12241421"), "12241421 is NOT phonetically unique in greek")
        );
    }

    @Test
    void testRemoveLast2Characters() {

        Assertions.assertAll("Test removeLast2Characters method ",

                () -> Assertions.assertEquals("8", commonGroupDigitsHelper.removeLast2Characters("852"), "removing the last two digits from 852 should result 8")
        );
    }

    @Test
    void testGetLast2Characters() {

        Assertions.assertAll("Test getLast2Characters method ",

                () -> Assertions.assertEquals("52", commonGroupDigitsHelper.getLast2Characters("852"), "getting the last two digits from 852 should result 52")
        );
    }


    @Test
    void testReplaceFinalDigit() {

        Assertions.assertAll("Test replaceFinalDigit method ",

                () -> Assertions.assertEquals("856", commonGroupDigitsHelper.replaceFinalDigit("852", "6"), "replacing the final digit of 852 with 6 should result 856")
        );
    }


    @Test
    void testCombineGroupDigitsScenarios() {
        List<String> previousScenarios = Arrays.asList("1", "2");
        List<String> presentScenarios = Arrays.asList("3", "4");


        List<String> combiinedList = commonGroupDigitsHelper.combineGroupDigitsScenarios(previousScenarios, presentScenarios);

        Assertions.assertAll("Test combineGroupDigitsScenarios method ",

                () -> Assertions.assertEquals("13", combiinedList.get(0), "the first digit combination is between 1 and 3 so 13"),
                () -> Assertions.assertEquals("14", combiinedList.get(1), "the second digit combination is between 1 and 4 so 14"),
                () -> Assertions.assertEquals("23", combiinedList.get(2), "the third digit combination is between 2 and 3 so 23"),
                () -> Assertions.assertEquals("24", combiinedList.get(3), "the forth digit combination is between 2 and 4 so 24")
        );

    }
}
