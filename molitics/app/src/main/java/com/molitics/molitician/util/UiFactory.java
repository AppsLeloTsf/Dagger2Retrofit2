package com.molitics.molitician.util;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.customView.BottomOffsetDecoration;

/**
 * Created by rahul on 11/04/18.
 */

public class UiFactory {


    public static void recyclerBottomSpace(Context mContext, RecyclerView recyclerView, int bottom_gap) {

        float offsetPx = mContext.getResources().getDimension(bottom_gap);
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
        recyclerView.addItemDecoration(bottomOffsetDecoration);

    }

}
