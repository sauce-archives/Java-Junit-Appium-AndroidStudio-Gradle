package com.saucelabs.guineapig.Fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by mehmetgerceker on 12/21/15.
 */
public class FragmentFactory {

    public enum GuineaPigFragmentType {
        Native,
        WebView
    }

    public  static final String SECTION_LABEL = "section_label";

    public static Fragment newInstance(GuineaPigFragmentType fragmentType) {
        Fragment fragment = null;
        if (fragmentType == GuineaPigFragmentType.Native){
            fragment = GuineaPigFragment.newInstance();
        } else if (fragmentType == GuineaPigFragmentType.WebView) {
           fragment = WebViewFragment.newInstance("https://saucelabs.com/test/guinea-pig");
        }
        return fragment;
    }
    public static String[] getGuineaPigFragmentTypeLabels(){
        GuineaPigFragmentType[] types = GuineaPigFragmentType.values();
        String [] labelList = new String [types.length];

        for(int idx = 0; idx < labelList.length; idx++) {
            labelList[idx] = types[idx].name();
        }
        return labelList;
    }
}
