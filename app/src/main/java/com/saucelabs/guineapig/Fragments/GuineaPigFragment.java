package com.saucelabs.guineapig.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.saucelabs.guineapig.R;
import com.saucelabs.guineapig.Utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mehmetgerceker on 12/21/15.
 */
public class GuineaPigFragment extends Fragment {

    @Bind(R.id.i_am_a_textbox) protected EditText noFocus;
    @Bind(R.id.fbemail) protected EditText eMail;
    @Bind(R.id.comments) protected EditText comments;
    @Bind(R.id.your_comments) protected TextView yourComments;
    @Bind(R.id.client_time) protected TextView clientTime;
    @Bind(R.id.user_agent) protected TextView userAgent;
    @Bind(R.id.submit) protected Button submitButton;
    @Bind(R.id.i_am_a_link) protected TextView link;
    @Bind(R.id.checked_checkbox) protected CheckBox checkedCheckBox;
    @Bind(R.id.unchecked_checkbox) protected CheckBox UnCheckedCheckBox;


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public GuineaPigFragment() {
        super();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance() {
        return new GuineaPigFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guineapig, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(FragmentFactory.SECTION_LABEL)));
        ButterKnife.bind(this, rootView);
        this.link.setMovementMethod(LinkMovementMethod.getInstance());
        return rootView;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        this.clientTime.setText(String.valueOf(System.currentTimeMillis()));
        this.userAgent.setText(Utilities.getSysInfo());
    }

    @OnClick(R.id.submit)
    public void onSubmitClick(View view) {
        this.yourComments.setText(this.comments.getText());
        this.comments.setText("");
        this.checkedCheckBox.setSelected(true);
        this.UnCheckedCheckBox.setSelected(false);
        Snackbar.make(view, "Thank you for your feedback!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    @OnClick(R.id.i_am_a_link)
    public void onLinkClick(View view) {

    }
}

