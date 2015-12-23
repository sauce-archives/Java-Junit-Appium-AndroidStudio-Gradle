package com.saucelabs.guineapig.Utils;

/**
 * Created by mehmetgerceker on 12/21/15.
 */
public class Utilities {
    public static String getSysInfo()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("OS:")
                .append(System.getProperty("os.version"))
                .append("+")
                .append(android.os.Build.VERSION.INCREMENTAL)
                .append(":")
                .append("API:")
                .append(android.os.Build.VERSION.SDK_INT)
                .append(":")
                .append("Device:")
                .append(android.os.Build.DEVICE)
                .append(":")
                .append( android.os.Build.MODEL)
                .append(".")
                .append(android.os.Build.PRODUCT);
        return sb.toString();
    }
}
