package tech.inscripts.ins_armman.mMitra.utility;

import android.content.Context;

/**
 * Created by Amol on 11/10/17.
 */

public interface IMvpView {

    Context getContext();

    interface OnFilterResultListener {
        void onZeroResult(String emptyMsg);

        void onMoreThanZeroResult(int resultSize);
    }

}
