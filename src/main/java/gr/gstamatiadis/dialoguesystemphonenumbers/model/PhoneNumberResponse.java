package gr.gstamatiadis.dialoguesystemphonenumbers.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhoneNumberResponse {
    List<PhoneScenario> allScenarios;

    public PhoneNumberResponse(List<PhoneScenario> allScenarios ){
        this.allScenarios = allScenarios;
    }
}
