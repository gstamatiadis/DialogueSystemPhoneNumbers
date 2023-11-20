package gr.gstamatiadis.dialoguesystemphonenumbers.service;


import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.PhoneNumberResponse;
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
class ValidateGreekPhoneNumberTest {

    private ValidateGreekPhoneNumber validateGreekPhoneNumber;

    @BeforeEach
    void setUp() {
        validateGreekPhoneNumber = new ValidateGreekPhoneNumber();
    }


    @Test
    void test_validateAllPhoneScenarios_Method() {
        List<String> phoneScenarios = Arrays.asList("302558", "2106930664", "21069306640", "00302106930664", "00306906930664", "6906930664");
        PhoneNumberResponse phoneNumber = validateGreekPhoneNumber.validateAllPhoneScenarios(phoneScenarios);


        Assertions.assertAll("Test validateAllPhoneScenarios method",
                () -> Assertions.assertEquals("302558", phoneNumber.getAllScenarios().get(0).getPossiblePhone(), "302558 is the first phone scenario added to the list"),
                () -> Assertions.assertFalse(phoneNumber.getAllScenarios().get(0).getIsValidGreekPhone(), "302558 is not a valid greek phone"),
                () -> Assertions.assertEquals("2106930664", phoneNumber.getAllScenarios().get(1).getPossiblePhone(), "2106930664 is the second phone scenario added to the list"),
                () -> Assertions.assertTrue(phoneNumber.getAllScenarios().get(1).getIsValidGreekPhone(), "2106930664 is a valid greek phone"),
                () -> Assertions.assertEquals("21069306640", phoneNumber.getAllScenarios().get(2).getPossiblePhone(), "21069306640 is the third phone scenario added to the list"),
                () -> Assertions.assertFalse(phoneNumber.getAllScenarios().get(2).getIsValidGreekPhone(), "21069306640 is not a valid greek phone"),
                () -> Assertions.assertEquals("00302106930664", phoneNumber.getAllScenarios().get(3).getPossiblePhone(), "00302106930664 is the fourth phone scenario added to the list"),
                () -> Assertions.assertTrue(phoneNumber.getAllScenarios().get(3).getIsValidGreekPhone(), "00302106930664 is a valid greek phone"),
                () -> Assertions.assertEquals("00306906930664", phoneNumber.getAllScenarios().get(4).getPossiblePhone(), "00306906930664 is the fifth phone scenario added to the list"),
                () -> Assertions.assertTrue(phoneNumber.getAllScenarios().get(4).getIsValidGreekPhone(), "00302106930664 is a valid greek phone"),
                () -> Assertions.assertEquals("6906930664", phoneNumber.getAllScenarios().get(5).getPossiblePhone(), "6906930664 is the sixth phone scenario added to the list"),
                () -> Assertions.assertTrue(phoneNumber.getAllScenarios().get(5).getIsValidGreekPhone(), "6906930664 is a valid greek phone")
        );
    }

}
