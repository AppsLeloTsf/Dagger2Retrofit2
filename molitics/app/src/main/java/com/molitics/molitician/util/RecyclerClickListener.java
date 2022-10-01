package com.molitics.molitician.util;

import android.view.View;

/**
 * Created by om on 16/03/18.
 */

public interface RecyclerClickListener {

    /**
     * Interface for Recycler View Click listener
     **/

    void onClick(View view, int position);

    void onLongClick(View view, int position);
}