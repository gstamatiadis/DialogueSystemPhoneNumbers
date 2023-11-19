package gr.gstamatiadis.dialoguesystemphonenumbers.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneScenario {

    String phoneScenario;
    Boolean isValidGreekPhone;


    public PhoneScenario(String phoneScenario , Boolean isValidGreekPhone){

        this.phoneScenario = phoneScenario;
        this.isValidGreekPhone = isValidGreekPhone;
    }

}
