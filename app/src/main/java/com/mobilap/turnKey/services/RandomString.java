package com.mobilap.turnKey.services;

import java.security.SecureRandom;

public class RandomString {

    public RandomString() { }

    public String getAlphaNumericString(){
        int n = 8;
        SecureRandom random = new SecureRandom();
        String char_lower = "abcdefghijklmnopqrstuvxyz";

        String AlphaNumericString = char_lower
                + "0123456789"
                + char_lower.toUpperCase()
                + "@%+/!#$?(:){}[]_-";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {

            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());


            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
