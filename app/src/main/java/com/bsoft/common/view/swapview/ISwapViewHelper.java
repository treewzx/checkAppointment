package com.bsoft.common.view.swapview;

import android.content.Context;
import android.view.View;

public interface ISwapViewHelper {

    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    void showLayout(int layoutId);

    View inflate(int layoutId);

    Context getContext();

    View getView();

}
