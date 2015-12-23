package com.saucelabs.guineapig.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;

/**
 * Created by mehmetgerceker on 12/21/15.
 */

public class GuineaPigPage extends PageBase {

    @FindBy(id = "unchecked_checkbox")
    private WebElement uncheckedCheckbox;

    @FindBy(id = "checked_checkbox")
    private WebElement checkedCheckbox;

    @FindBy(id = "i_am_a_link")
    private WebElement theActiveLink;

    @FindBy(id = "i_am_a_textbox")
    private WebElement textInput;

    @FindBy(id = "your_comments")
    private WebElement yourCommentsSpan;

    @FindBy(id = "fbemail")
    private WebElement emailTextInput;

    @FindBy(id = "comments")
    private WebElement commentsTextInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public static GuineaPigPage getPage(WebDriver driver) {
        return PageFactory.initElements(driver, GuineaPigPage.class);
    }

    public void checkUncheckedCheckBox() {
        setCheckCheckBoxState(this.uncheckedCheckbox, true);
    }

    public boolean getUncheckedCheckBoxState() {
        String attr = this.uncheckedCheckbox.getAttribute("checked");
        return attr.toLowerCase().contentEquals("true");
    }

    public void uncheckCheckedCheckBox() {
        setCheckCheckBoxState(this.checkedCheckbox, false);
    }

    public boolean getCheckedCheckBoxState() {
        String attr = this.checkedCheckbox.getAttribute("checked");
        return attr.toLowerCase().contentEquals("true");
    }

    public void enterCommentText(String text) {
        this.commentsTextInput.click();
        setTextInputValue(this.commentsTextInput, text);
    }
    public void clickCommentText() {
        this.commentsTextInput.click();
    }
    public void clearCommentText() {
        this.commentsTextInput.clear();
    }
    public String getCommentText() {
        return this.commentsTextInput.getText();
    }
    public void submitForm() {
        clickButton(this.submitButton);
    }
    public String getSubmittedCommentText() {
        return this.yourCommentsSpan.getText();
    }

    public void enterEmailText(String email) {
        setTextInputValue(this.emailTextInput, email);
    }
    public void clickEmailText() {
        this.emailTextInput.click();
    }
    public void clearEmailText() {
        this.emailTextInput.clear();
    }
    public String getEmailText() {
        return this.emailTextInput.getText();
    }

}

