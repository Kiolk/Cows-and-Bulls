package com.github.kiolk.cowsandbulls.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.kiolk.cowsandbulls.App;
import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.GameResult;
import com.github.kiolk.cowsandbulls.data.models.result.remote.ResultRemote;
import com.github.kiolk.cowsandbulls.utils.InternetConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PublishDialog extends DialogFragment {

    private static final String BUNDLE__GAME_RESULT = "BUNDLE__GAME_RESULT";
    public static final String TAG = "PublishDialog";
    private Dialog dialog;
    private String userName;
    private GameResult result;
    private  EditText input;
    private TextView btnPublish;
    private TextView btnCancel;
    private ProgressBar pbProgress;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.setCancelable(false);
        if (savedInstanceState != null) {
            result = savedInstanceState.getParcelable(BUNDLE__GAME_RESULT);
        }
        return show();
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(BUNDLE__GAME_RESULT, result);
        super.onSaveInstanceState(outState);
    }

    private Dialog show() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext()).setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.layout_publish_dialog, null);

        String name = App.getSettingsRepository().getUserName();

        builder.setView(view);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> onCancel());
        btnPublish = view.findViewById(R.id.btn_publish);
        btnPublish.setOnClickListener(v -> onPublish());
        pbProgress = view.findViewById(R.id.pb_publish);
        ((TextView) view.findViewById(R.id.tv_time_value)).setText(new SimpleDateFormat("mm:ss", Locale.getDefault()).format(new Date(result.getTime() * 1000)));
        ((TextView) view.findViewById(R.id.tv_moves_value)).setText(String.valueOf(result.getMoves()));
        input = view.findViewById(R.id.et_nik_name_input);

        if (name != null) {
            input.setText(name);
            userName = name;
        } else {
            input.setHint(R.string.your_nick_name);
        }

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = s.toString();
                input.setBackground(getContext().getResources().getDrawable(R.drawable.bg_edit_text_background));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog = builder.create();
        return dialog;
    }

    private void onCancel() {
        App.getSettingsRepository().setUserName(userName);
        dialog.dismiss();

    }

    private void onPublish() {
        if(getContext() == null){
            return;
        }

        if(TextUtils.isEmpty(userName)){
            if(input != null){
                input.setBackground(getContext().getResources().getDrawable(R.drawable.bg_edit_text_background_error));
            }
            return;
        }

        if(!InternetConnection.isInternetAvialable(getContext())){
            return;
        }

        App.getSettingsRepository().setUserName(userName);

        if(btnCancel != null && btnPublish != null){
            btnCancel.setEnabled(false);
            btnPublish.setEnabled(false);
        }

        if(pbProgress != null){
            pbProgress.setVisibility(View.VISIBLE);
        }

        ResultRemote resultRemote = new ResultRemote();
        resultRemote.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(new Date()));
        resultRemote.setMoves(result.getMoves());
        resultRemote.setUserName(userName);
        String uuid = App.getSettingsRepository().getIdentification();
        resultRemote.setUuid(uuid);
        resultRemote.setTime(result.getTime());

        Handler handler = new Handler();

        new Thread(() -> {
            App.getGameRepository().publishResult(resultRemote);
            handler.post(() -> dialog.dismiss());
        }).start();
    }
}
