package gr.gstamatiadis.dialoguesystemphonenumbers.api.controller;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.PhoneNumberResponse;
import gr.gstamatiadis.dialoguesystemphonenumbers.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@RestController
@Validated
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberController(PhoneNumberService phoneNumberService){
        this.phoneNumberService = phoneNumberService;
    }


    @GetMapping("/getPhoneScenarios")
    public ResponseEntity<PhoneNumberResponse> getAllScenarios( @RequestParam @Size(min = 2,max=50) @Pattern(regexp = "[0-9 ]+")   String phoneNumber){


        return ResponseEntity.ok( phoneNumberService.getAllPossibleScenarios(phoneNumber));
    }


}
