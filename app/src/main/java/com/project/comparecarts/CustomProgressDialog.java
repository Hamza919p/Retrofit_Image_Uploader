package com.project.comparecarts;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

public class CustomProgressDialog extends Dialog {
    ProgressBar loadingIndicator;

    public CustomProgressDialog(@NonNull Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.progress_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        loadingIndicator = findViewById(R.id.rotate_loading);
    }

    @Override
    public void show() {
        super.show();
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        super.hide();
        loadingIndicator.setVisibility(View.GONE);
    }
}
