package com.mobilap.turnKey;

import com.mobilap.turnKey.services.RandomString;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class RandomStringUnitTest {

    @Test
    public void getAlphaNumericString() throws Exception{
        RandomString random = new RandomString();
        int size = 0;
        String result = random.getAlphaNumericString();
        size = result.length();
        Assert.assertEquals("Test to getStrigAlea", 8,size);
    }
}
