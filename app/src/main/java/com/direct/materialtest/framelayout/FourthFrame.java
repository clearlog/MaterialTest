package com.direct.materialtest.framelayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.direct.materialtest.R;

/**
 * Created by HeQiang on 2017/10/20 0020.
 */

public class FourthFrame extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_fourth, container, false);
        return view;
    }

}
