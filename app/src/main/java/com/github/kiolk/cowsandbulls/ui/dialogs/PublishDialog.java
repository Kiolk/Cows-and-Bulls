package com.github.kiolk.cowsandbulls.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.GameResult;
import com.github.kiolk.cowsandbulls.data.models.GameResultRemote;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PublishDialog extends DialogFragment {

    private static final String BUNDLE__GAME_RESULT = "BUNDLE__GAME_RESULT";
    public static final String TAG = "PublishDialog";
    private Dialog dialog;
    private String userName;
    private GameResult result;

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

        builder.setView(view);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> onCancel());
        view.findViewById(R.id.btn_publish).setOnClickListener(v -> onPublish());
        ((TextView) view.findViewById(R.id.tv_time_value)).setText(new SimpleDateFormat("mm:ss", Locale.getDefault()).format(new Date(result.getTime() * 1000)));
        ((TextView) view.findViewById(R.id.tv_moves_value)).setText(String.valueOf(result.getMoves()));
        EditText input = view.findViewById(R.id.et_nik_name_input);
        String name = null; //TODO implement logic for get preview value

        if (name != null) {
            input.setText(name);
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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog = builder.create();
        return dialog;
    }

    private void onCancel() {
        dialog.dismiss();

    }


    private void onPublish() {
        GameResultRemote resultRemote = new GameResultRemote();
        resultRemote.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).format(new Date()));
        resultRemote.setMoves(result.getMoves());
        resultRemote.setUserName(userName);
        resultRemote.setUuid("hkdjfksjfhsdnbfsdkjfk"); //TODO implement logic for getting UUID
        resultRemote.setTime(result.getTime());

        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                //TODO implement logic for publish result
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        }).start();
    }
}
