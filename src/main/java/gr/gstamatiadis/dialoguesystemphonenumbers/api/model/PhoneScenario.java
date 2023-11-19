package gr.gstamatiadis.dialoguesystemphonenumbers.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneScenario {

    String possiblePhone;
    Boolean isValidGreekPhone;


    public PhoneScenario(String phoneScenario , Boolean isValidGreekPhone){

        this.possiblePhone = phoneScenario;
        this.isValidGreekPhone = isValidGreekPhone;
    }

}
