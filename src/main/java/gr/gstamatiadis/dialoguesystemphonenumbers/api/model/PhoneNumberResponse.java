package gr.gstamatiadis.dialoguesystemphonenumbers.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhoneNumberResponse {
    List<String> allScenarios;

    public PhoneNumberResponse(List<String> allScenarios ){
        this.allScenarios = allScenarios;
    }
}
