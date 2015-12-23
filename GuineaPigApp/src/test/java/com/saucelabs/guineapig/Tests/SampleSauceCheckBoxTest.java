package com.saucelabs.guineapig.Tests;

import com.saucelabs.guineapig.Pages.GuineaPigPage;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;

import static org.junit.Assert.*;

/**
 * Created by mehmetgerceker on 12/22/15.
 */

public class SampleSauceCheckBoxTest extends SampleSauceTestBase {

    public SampleSauceCheckBoxTest(String os,
                                   String version, String browser, String deviceName, String deviceOrientation) {
        super(os, version, browser, deviceName, deviceOrientation);
    }

    /**
     * Runs a simple test verifying the checked checkbox state
     *
     * @throws InvalidElementStateException
     */
    @Test
    public void verifyUncheckedCheckBoxInputTest() throws InvalidElementStateException {

        // get page object
        GuineaPigPage page = GuineaPigPage.getPage(driver);

        /*
         checkUncheckedCheckBox is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        page.checkUncheckedCheckBox();

        /*
         Assertions should be part of test and not part of Page object.
         Each test should be verifying one piece of functionality (atomic testing)
        */
        assertEquals(page.getUncheckedCheckBoxState(), true);

    }

    /**
     * Runs a simple test verifying the checked checkbox state
     *
     * @throws InvalidElementStateException
     */
    @Test
    public void verifyCheckedCheckBoxInputTest() throws InvalidElementStateException {

        //get the page object
        GuineaPigPage page = GuineaPigPage.getPage(driver);

        /*
         checkUncheckedCheckBox is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        page.uncheckCheckedCheckBox();

        /*
         Assertions should be part of test and not part of Page object.
         Each test should be verifying one piece of functionality (atomic testing)
        */
        assertEquals(page.getCheckedCheckBoxState(), false);

    }
}