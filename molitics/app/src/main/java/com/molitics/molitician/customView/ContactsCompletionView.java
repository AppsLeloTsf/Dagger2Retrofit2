package com.molitics.molitician.customView;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * Created by rahul on 17/11/17.
 */

public class ContactsCompletionView extends TokenCompleteTextView {

    public ContactsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(Object object) {
        AllLeaderModel p = (AllLeaderModel) object;

        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        assert l != null;
        TokenTextView view = (TokenTextView) l.inflate(R.layout.contact_token, (ViewGroup) ContactsCompletionView.this.getParent(), false);
        view.setText(p.getName());
        // ((TextView) view.findViewById(R.id.name)).setText(p.getName());

        return view;
    }

    @Override
    protected Object defaultObject(String completionText) {
        //Stupid simple example of guessing if we have an email or not
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new AllLeaderModel();
        } else {
            return new AllLeaderModel();
        }
    }
}
