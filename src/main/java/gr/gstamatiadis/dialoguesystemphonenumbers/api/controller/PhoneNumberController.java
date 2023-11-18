package gr.gstamatiadis.dialoguesystemphonenumbers.api.controller;

import gr.gstamatiadis.dialoguesystemphonenumbers.model.PhoneNumberResponse;
import gr.gstamatiadis.dialoguesystemphonenumbers.model.PhoneScenario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class PhoneNumberController {



    @GetMapping("/getPhoneScenarios")
    public ResponseEntity<PhoneNumberResponse> getAllScenarios(@RequestParam String phoneNumber){



        //TODO temporary until algorithmic logic is added
        return ResponseEntity.ok( new PhoneNumberResponse(Collections.singletonList(new PhoneScenario(phoneNumber,false))));
    };
}
