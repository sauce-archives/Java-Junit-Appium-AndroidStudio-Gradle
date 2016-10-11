package com.saucelabs.guineapig.Tests;

/**
 * Created by mehmetgerceker on 12/21/15.
 */


import com.saucelabs.common.SauceOnDemandAuthentication;

import com.saucelabs.TestRules.RetryRule;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.guineapig.BuildConfig;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import java.net.URL;
import java.util.LinkedList;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.Utils.*;

import io.appium.java_client.AppiumDriver;


/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple browsers
 * in parallel.
 * <p>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke the Sauce REST API
 * to mark the test as passed or failed.
 *
 * @author Mehmet Gerceker
 */
@Ignore
@RunWith(ConcurrentParameterized.class)
public class SampleSauceTestBase implements SauceOnDemandSessionIdProvider {

    public static String seleniumURI;
    public static String buildTag = System.getenv("BUILD_TAG");
    public static String app = null;
    public static String username = System.getenv("SAUCE_USERNAME");
    public static String accessKey = System.getenv("SAUCE_ACCESS_KEY");
    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access
     * key.  To use the authentication supplied by environment variables or from an external file,
     * use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accessKey);

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    /**
     * Test decorated with @Retry will be run 3 times in case they fail using this rule.
     */
    @Rule
    public RetryRule rule = new RetryRule(3);

    /**
     * Represents the browser to be used as part of the test run.
     */
    protected String platformName;
    /**
     * Represents the operating system to be used as part of the test run.
     */
    protected String appiumVersion;
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    protected String platformVersion;
    /**
     * Represents the deviceName of mobile device
     */
    protected String deviceName;
    /**
     * Represents the device-orientation of mobile device
     */
    protected String deviceOrientation;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    protected String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    protected AppiumDriver driver;

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters,
     * which represent the operating system, version and browser to be used when launching a Sauce
     * VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     *
     * @param platformName      name of the platformName. (Android, iOS, etc.)
     * @param deviceName        name of the device
     * @param platformVersion   Os version of the device
     * @param appiumVersion     appium version
     * @param deviceOrientation device orientation
     */

    public SampleSauceTestBase(
            String platformName,
            String deviceName,
            String platformVersion,
            String appiumVersion,
            String deviceOrientation) {
        super();
        this.platformName = platformName;
        this.deviceName = deviceName;
        this.platformVersion = platformVersion;
        this.appiumVersion = appiumVersion;
        this.deviceOrientation = deviceOrientation;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList<String[]> browsers = new LinkedList<>();

        browsers.add(new String[]
                {"Android", "Android Emulator", "4.3", "1.4.16", "portrait"});
        browsers.add(new String[]
                {"Android", "Google Nexus 7 HD Emulator", "4.4", "1.4.16", "portrait"});

        return browsers;
    }

    @BeforeClass
    public static void setupClass() throws Exception{
        //get the uri to send the commands to.
        seleniumURI = SauceHelpers.buildSauceUri();
        //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
        //You can set this manually on manual runs.
        app = BuildConfig.APP_PATH;
        if (app != null){
            app = SauceHelpers.uploadAppToSauceStorage(app, username, accessKey);
        } else {
            System.err.println("No app no test! " + BuildConfig.APP_PATH);
        }
    }

    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities
     * defined by the {@link #platformName}, {@link #platformVersion} and {@link #deviceName}
     * instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver}
     * instance.
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (this.platformName != null)
            capabilities.setCapability("platformName", this.platformName);
        if (this.platformVersion != null)
            capabilities.setCapability("platformVersion", this.platformVersion);
        if (this.deviceName != null)
            capabilities.setCapability("deviceName", this.deviceName);
        if (this.deviceOrientation != null)
            capabilities.setCapability("deviceOrientation", this.deviceOrientation);
        if (this.appiumVersion != null)
            capabilities.setCapability("appiumVersion", this.appiumVersion);
        if (app != null)
            capabilities.setCapability("app", app);
        else
            throw new Exception("App path needs to be specified for this test to run!");

        String methodName = name.getMethodName();
        capabilities.setCapability("name", methodName);

        //Getting the build name.
        //Using the Jenkins ENV var. You can use your own. If it is not set test will run without
        // a build id.
        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        this.driver = new AppiumDriver(
                new URL("https://" + username + ":" + accessKey + seleniumURI + "/wd/hub"),
                capabilities) {
            @Override
            public WebElement scrollTo(String text) {
                return null;
            }

            @Override
            public WebElement scrollToExact(String text) {
                return null;
            }
        };

        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }
}
