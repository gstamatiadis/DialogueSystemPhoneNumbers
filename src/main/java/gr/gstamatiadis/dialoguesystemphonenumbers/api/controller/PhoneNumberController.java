package gr.gstamatiadis.dialoguesystemphonenumbers.api.controller;

import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.PhoneNumberResponse;
import gr.gstamatiadis.dialoguesystemphonenumbers.api.model.PhoneScenario;
import gr.gstamatiadis.dialoguesystemphonenumbers.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class PhoneNumberController {

    private PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberController(PhoneNumberService phoneNumberService){
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("/getPhoneScenarios")
    public ResponseEntity<PhoneNumberResponse> getAllScenarios(@RequestParam String phoneNumber){


        //TODO temporary until algorithmic logic is added
        return ResponseEntity.ok( new PhoneNumberResponse(phoneNumberService.getAllPossibleScenarios(phoneNumber)));
    }


}
