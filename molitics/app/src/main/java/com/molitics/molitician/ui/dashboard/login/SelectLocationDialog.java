package com.molitics.molitician.ui.dashboard.login;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.constantData.StateAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rahul on 12/15/2016.
 */

public class SelectLocationDialog {

    static Dialog dialog;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.title)
    TextView title_view;

    private RecyclerTouchWithType recyclerTouchWithType;

    static SelectLocationDialog locationDialog;

    public static SelectLocationDialog getInstance() {
        locationDialog = new SelectLocationDialog();
        return locationDialog;
    }

    public void showDialog(Context mContext, String title, List<ConstantModel> list, RecyclerTouchWithType recyclerTouchWithType) {
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_location);

        ButterKnife.bind(this, dialog);
        if (!title.isEmpty()) {
            title_view.setVisibility(View.VISIBLE);
            title_view.setText(title);
        } else
            title_view.setVisibility(View.GONE);
        this.recyclerTouchWithType = recyclerTouchWithType;

        StateAdapter mAdapter = new StateAdapter(mContext, list, recyclerTouchWithType);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setAdapter(mAdapter);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
    }

    @OnClick(R.id.close_dialog)
    public void onCloseDialog() {
        if (recyclerTouchWithType != null) {
            recyclerTouchWithType.onCloseClick();
        }
        dialog.dismiss();
    }

    public static void dismissDialog() {
        dialog.dismiss();
    }
}
