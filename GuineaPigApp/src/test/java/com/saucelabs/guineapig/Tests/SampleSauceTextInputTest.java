package com.saucelabs.guineapig.Tests;

import com.saucelabs.guineapig.Pages.*;
import com.saucelabs.TestRules.Retry;
import com.saucelabs.guineapig.Tests.SampleSauceTestBase;

import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;

import java.util.UUID;

import static org.junit.Assert.*;


/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class SampleSauceTextInputTest extends SampleSauceTestBase {

    public SampleSauceTextInputTest(String os,
                                    String version, String browser, String deviceName, String deviceOrientation) {
        super(os, version, browser, deviceName, deviceOrientation);
    }

    /**
     * Runs a simple test verifying if the email input is functional.
     *
     * @throws InvalidElementStateException
     */
    @Test
    public void verifyEmailInputTest() throws InvalidElementStateException {
        String emailInputText = "abc@gmail.com";

        // get page object
        GuineaPigPage page = GuineaPigPage.getPage(driver);

        /*
         enterEmailText page is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        page.clickEmailText();
        page.clearEmailText();
        page.enterEmailText(emailInputText);
        this.driver.hideKeyboard();
        /*
         Assertions should be part of test and not part of Page object.
         Each test should be verifying one piece of functionality (atomic testing)
        */
        assertEquals(page.getEmailText(), emailInputText);

    }

    /**
     * Runs a simple test verifying if the comment input is functional.
     *
     * @throws InvalidElementStateException
     */
    @Test
    @Retry
    public void verifyCommentInputTest() throws InvalidElementStateException {
        String commentInputText = UUID.randomUUID().toString();

        // Navigate to the page
        GuineaPigPage page = GuineaPigPage.getPage(driver);

        /*
         enterCommentText page is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        page.clickCommentText();
        page.clearCommentText();
        page.enterCommentText(commentInputText);
        driver.hideKeyboard();
        /*
         Assertions should be part of test and not part of Page object.
         Each test should be verifying one piece of functionality (atomic testing)
        */
        assertEquals(commentInputText, page.getCommentText());

    }
}