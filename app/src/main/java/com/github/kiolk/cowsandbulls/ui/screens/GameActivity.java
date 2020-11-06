package com.github.kiolk.cowsandbulls.ui.screens;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.kiolk.cowsandbulls.App;
import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.data.models.GameResult;
import com.github.kiolk.cowsandbulls.data.models.Move;
import com.github.kiolk.cowsandbulls.logic.CustomTimer;
import com.github.kiolk.cowsandbulls.logic.TimerChange;
import com.github.kiolk.cowsandbulls.ui.adapters.GameAdapter;
import com.github.kiolk.cowsandbulls.ui.dialogs.PublishDialog;
import com.github.kiolk.cowsandbulls.ui.screens.result.BestResultFragment;
import com.github.kiolk.cowsandbulls.ui.screens.rules.RulesFragment;
import com.github.kiolk.cowsandbulls.ui.views.DisplayLayout;
import com.github.kiolk.cowsandbulls.ui.views.KeyboardLayout;
import com.github.kiolk.cowsandbulls.ui.views.StartSpannable;
import com.github.kiolk.cowsandbulls.utils.ANALYTICS;
import com.github.kiolk.cowsandbulls.utils.NumberUtil;
import com.github.kiolk.cowsandbulls.utils.ThemeHelper;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements KeyboardLayout.OnKeyBoardListener, TimerChange, StartSpannable.StartSpannableOnclickListener {

    public static final int LENGTH_CODED_NUMBER = 4;
    public static final String BUNDLE_INPUT = "BUNDLE_INPUT";
    public static final String BUNDLE_CODED_NUMBER = "BUNDLE_CODED_NUMBER";
    public static final String BUNDLE_MOVES = "BUNDLE_MOVES";
    public static final String BUNDLE_MOVES_VALUES = "BUNDLE_MOVES_VALUES";
    public static final String BUNDLE_SHOW_BEST = "BUNDLE_SHOW_BEST";

    private int mMoves = 0;
    private String mInput = "";
    private String mCodedNumber = "";
    private String mOpenChildScreen;

    private CustomTimer mCustomTimer;
    private TextView mTimerTV;
    private DisplayLayout mDisplayLayout;
    private KeyboardLayout mKeyboardLayout;
    private RecyclerView mRecyclerView;
    private FrameLayout mContainer;
    private ImageButton mResult;
    private ImageButton mRules;
    private ImageButton mThemeMode;
    private GameAdapter mAdapter;

    private BestResultFragment mBestFragment;
    private RulesFragment mRulesFragment;

    private FirebaseAnalytics mAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Default);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTheme();

        mDisplayLayout = findViewById(R.id.display_input);
        mKeyboardLayout = findViewById(R.id.keyboard_game);
        mKeyboardLayout.setOnKeyBoardListener(this);
        mCustomTimer = new CustomTimer(this);
        mTimerTV = findViewById(R.id.timer_tv);
        initRecyclerView();
        mContainer = findViewById(R.id.fl_container);
        mResult = findViewById(R.id.btn_result);
        mResult.setOnClickListener(v -> {
            mAnalytics.logEvent(ANALYTICS.OPEN_RESULTS_EVENT, null);
            showResults();
        });
        mRules = findViewById(R.id.btn_rules);
        mRules.setOnClickListener(v -> {
            mAnalytics.logEvent(ANALYTICS.OPEN_RULES_EVENT, null);
            showRules();
        });
        mThemeMode = findViewById(R.id.btn_theme_mode);
        mThemeMode.setOnClickListener(v -> {
            if (App.getPrefsInstance().getThemePref().equals(ThemeHelper.LIGHT_MODE)) {
                App.getPrefsInstance().setThemePref(ThemeHelper.DARK_MODE);
                ThemeHelper.applyTheme(ThemeHelper.DARK_MODE);
            } else {
                App.getPrefsInstance().setThemePref(ThemeHelper.LIGHT_MODE);
                ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE);
            }
        });
        mAnalytics = FirebaseAnalytics.getInstance(getBaseContext());
    }

    private void initTheme() {

        ThemeHelper.applyTheme(App.getPrefsInstance().getThemePref());

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_moves);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new GameAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_INPUT, mInput);
        outState.putString(BUNDLE_CODED_NUMBER, mCodedNumber);
        outState.putInt(BUNDLE_MOVES, mMoves);
        outState.putParcelableArrayList(BUNDLE_MOVES_VALUES, (ArrayList<? extends Parcelable>) mAdapter.getMoves());
        if (mContainer.getVisibility() == View.VISIBLE) {
            outState.putString(BUNDLE_SHOW_BEST, mOpenChildScreen);
        }
        super.onSaveInstanceState(mCustomTimer.saveState(outState));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mInput = savedInstanceState.getString(BUNDLE_INPUT);
        mCodedNumber = savedInstanceState.getString(BUNDLE_CODED_NUMBER);
        mDisplayLayout.setText(mInput);
        mMoves = savedInstanceState.getInt(BUNDLE_MOVES);
        mAdapter.setMoves(savedInstanceState.getParcelableArrayList(BUNDLE_MOVES_VALUES));
        mOpenChildScreen = savedInstanceState.getString(BUNDLE_SHOW_BEST);
        if (mOpenChildScreen != null && mOpenChildScreen.equals(BestResultFragment.TAG)) {
            showResults();
        } else if (mOpenChildScreen != null && mOpenChildScreen.equals(RulesFragment.TAG)) {
            showRules();
        }
        this.mCustomTimer = CustomTimer.restoreTimer(savedInstanceState, this);
    }

    @Override
    public void onKeyPressed(String input) {
        mInput = input;
        mDisplayLayout.setText(input);
    }

    @Override
    public void onStartPressed() {
        mCodedNumber = NumberUtil.generateRandom(LENGTH_CODED_NUMBER);
        mAdapter.setStartPressed();
        mAdapter.onClear();
        mCustomTimer.reset();
        mCustomTimer.start();
        mMoves = 0;
    }

    @Override
    public void onStopPressed() {
        mDisplayLayout.setText(mCodedNumber);
        mCustomTimer.stop();
        mInput = mCodedNumber;
        mMoves = 0;
    }

    @Override
    public void onEnterPressed() {
        if (!NumberUtil.checkCorrectInput(mInput, LENGTH_CODED_NUMBER)) {
            Toast.makeText(getBaseContext(), R.string.error_incorrect_number, Toast.LENGTH_LONG).show();
            return;
        }
        mMoves++;
        int cows = NumberUtil.getCowsNumber(mCodedNumber, mInput);
        int bulls = NumberUtil.getBullsNumber(mCodedNumber, mInput);

        if (bulls == LENGTH_CODED_NUMBER) {
            mDisplayLayout.setText(mCodedNumber);
            mCustomTimer.stop();
            PublishDialog dialog = new PublishDialog();
            dialog.setResult(new GameResult(mMoves, mCustomTimer.getTimeLong()));
            mMoves = 0;
            dialog.show(getSupportFragmentManager(), PublishDialog.TAG);
            mKeyboardLayout.stop();
        }

        Move nextMove = new Move(mInput, cows, bulls);
        mAdapter.addNextMove(nextMove);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void updateView(String text) {
        runOnUiThread(() -> mTimerTV.setText(text));
    }

    private void showResults() {
        enableNavigationButtons(false);
        mContainer.setVisibility(View.VISIBLE);
        mOpenChildScreen = BestResultFragment.TAG;

        mBestFragment = (BestResultFragment) getSupportFragmentManager().findFragmentByTag(BestResultFragment.TAG);

        if (mBestFragment == null) {
            mBestFragment = new BestResultFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fl_container, mBestFragment, BestResultFragment.TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        TranslateAnimation animation = new TranslateAnimation(mContainer.getWidth(), 0, 0, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        mContainer.startAnimation(animation);
    }

    private void showRules() {

        enableNavigationButtons(false);
        mContainer.setVisibility(View.VISIBLE);
        mOpenChildScreen = RulesFragment.TAG;

        mRulesFragment = (RulesFragment) getSupportFragmentManager().findFragmentByTag(RulesFragment.TAG);

        if (mRulesFragment == null) {
            mRulesFragment = new RulesFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fl_container, mRulesFragment, RulesFragment.TAG);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        TranslateAnimation animation = new TranslateAnimation(mContainer.getWidth(), 0, 0, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        mContainer.startAnimation(animation);

    }

    public void hideResults() {
        enableNavigationButtons(true);
        TranslateAnimation animation = new TranslateAnimation(0, mContainer.getWidth(), 0, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                mContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mContainer.startAnimation(animation);
    }

    private void enableNavigationButtons(Boolean isEnabled) {
        mResult.setEnabled(isEnabled);
        mRules.setEnabled(isEnabled);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        if (mContainer.getVisibility() == View.VISIBLE) {
            hideResults();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void start() {
        hideResults();
        mKeyboardLayout.startOutside();
    }

}