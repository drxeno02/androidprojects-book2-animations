package com.example.springanimationdemo.activity;

import android.os.Bundle;
import android.view.View;

import com.example.springanimationdemo.R;
import com.example.springanimationdemo.utils.Utils;

public class ScaleSpringAnimation extends BaseFragmentActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_spring);

        // instantiate views
        initializeViews();
        initializeHandlers();
        initializeListeners();
    }

    /**
     * Method is used to initialize views
     */
    private void initializeViews() {

    }


    @Override
    public void onClick(View v) {
        if (!Utils.isViewClickable()) {
            return;
        }

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
