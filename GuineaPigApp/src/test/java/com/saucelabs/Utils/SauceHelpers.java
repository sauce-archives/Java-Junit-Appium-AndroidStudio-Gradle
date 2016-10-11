package com.saucelabs.Utils;

import com.google.common.io.Files;
import com.saucelabs.saucerest.SauceREST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by mehmetgerceker on 12/21/15.
 */
public class SauceHelpers {

    /**
     * Will generate the URI that will be used to send commands to the Se instance.
     * If SauceConnect tunnel in use and not directed not to use it will use the SC command relay.
     *
     * @param doNotUseSauceConnectCmdRelay Even if available do not use the relay.
     * @return String formatted uri for Sauce Se commands.
     */
    public static String buildSauceUri(boolean doNotUseSauceConnectCmdRelay) {
        String seleniumURI = "@ondemand.saucelabs.com:443";
        String seleniumPort = System.getenv("SELENIUM_PORT");
        String seleniumHost = System.getenv("SELENIUM_HOST");
        if (!doNotUseSauceConnectCmdRelay &&
                seleniumPort != null &&
                seleniumHost != null &&
                !seleniumHost.contentEquals("ondemand.saucelabs.com")) {
            //While running in CI, if Sauce Connect is running the SELENIUM_PORT env var will be set.
            //use SC relay port
            seleniumURI = String.format("@localhost:%s", seleniumPort);

        }
        return seleniumURI;
    }

    /**
     * Will generate the URI that will be used to send commands to the Se instance.
     * If SauceConnect tunnel in use it will use the SC command relay.
     *
     * @return String formatted uri for Sauce Se commands.
     */
    public static String buildSauceUri() {
        return buildSauceUri(false);
    }

    public static String uploadAppToSauceStorage(
            String appFullPath, String username, String password) throws Exception {
        String appURI = null;
        String fileURI = "sauce-storage:%s";
        //upload the app file to sauce storage.
        File file = new File(appFullPath);
        String srcMD5 = getFileMD5(file);
        String fileName = file.getName();
        SauceREST sauceREST = new SauceREST(username, password);
        String dstMD5 = sauceREST.uploadFile(new File(appFullPath));
        if (!srcMD5.contentEquals(dstMD5)) {
            throw new Exception("File upload failed! MD5 signatures do not match!");
        } else {
            appURI = String.format(fileURI, fileName);
            System.out.printf("File: %s uploaded successfully!\n", appFullPath);
        }
        return appURI;
    }

    public static String getFileMD5(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException(
                    "Please set your APP file location in APP build config field in gradle and " +
                            "try again!" +
                            "File name: " + file.getAbsolutePath());
        }
        FileInputStream fis = new FileInputStream(file);
        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        fis.close();
        return md5;
    }

}