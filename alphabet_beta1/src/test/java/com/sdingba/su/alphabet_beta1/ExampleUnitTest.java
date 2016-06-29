package com.sdingba.su.alphabet_beta1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        ChineseCharToEn ww = new ChineseCharToEn();
        System.out.println(ww.getAllFirstLetter("xx我省将哦"));
        System.out.println(ww.getFirstLetter("是"));
    }
}