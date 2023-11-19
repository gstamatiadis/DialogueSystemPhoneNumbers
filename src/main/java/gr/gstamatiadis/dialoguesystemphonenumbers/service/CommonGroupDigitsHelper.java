package gr.gstamatiadis.dialoguesystemphonenumbers.service;

import org.springframework.stereotype.Component;

@Component
public class CommonGroupDigitsHelper {

    protected  boolean isNotPhoneticallyUniqueNumber(String s) {
        return !s.equals("11") && !s.equals("12");
    }

    protected  String replaceFinalDigit(String currentDigits, String nextDigits) {
        return currentDigits.substring(0, currentDigits.length() - 1) + nextDigits;
    }

    protected String removeLast2Characters(String string) {
        return string.substring(0, string.length() - 2);
    }

    protected String getLast2Characters(String string) {
        return string.substring(string.length() - 2);
    }

}
