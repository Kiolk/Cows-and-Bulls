package com.github.kiolk.cowsandbulls;


import android.content.pm.ActivityInfo;
import android.os.RemoteException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import com.github.kiolk.cowsandbulls.ui.screens.GameActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GameScreenTest {

    @Rule
    public ActivityTestRule<GameActivity> activityRule =
            new ActivityTestRule<>(GameActivity.class);


    @Test
    public void checkStartButton() {
        onView(withText("Start")).check(matches(isDisplayed()));

        checkButtonsEnabled(false);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_start))
                .perform(click())
                .check(matches(withText("Stop")));

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_one))
                .perform(click());

        onView(withId(R.id.tv_first))
                .check(matches(withText("1")));

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_two))
                .perform(click());

        onView(withId(R.id.tv_second))
                .check(matches(withText("2")));

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_three))
                .perform(click());

        onView(withId(R.id.tv_third))
                .check(matches(withText("3")));

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_four))
                .perform(click());

        onView(withId(R.id.tv_forth))
                .check(matches(withText("4")));

        checkButtonsEnabled(true);
        checkEnterEnabled(true);
    }

    private void checkButtonsEnabled(Boolean isEnable) {
        if (isEnable) {
            onView(withId(R.id.btn_one))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_two))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_three))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_four))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_five))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_six))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_seven))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_eight))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_nine))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_zero))
                    .check(matches(isEnabled()));

            onView(withId(R.id.btn_clear))
                    .check(matches(isEnabled()));

        } else {
            onView(withId(R.id.btn_two))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_three))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_four))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_five))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_six))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_seven))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_eight))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_nine))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_zero))
                    .check(matches(not(isEnabled())));

            onView(withId(R.id.btn_clear))
                    .check(matches(not(isEnabled())));
        }
    }

    private void checkEnterEnabled(Boolean isEnable) {
        if (isEnable) {
            onView(withId(R.id.btn_enter))
                    .check(matches(isEnabled()));
        } else {
            onView(withId(R.id.btn_enter))
                    .check(matches(not(isEnabled())));
        }

    }

    @Test
    public void checkRotation(){
        onView(withText("Start")).check(matches(isDisplayed()));

        checkButtonsEnabled(false);
        checkEnterEnabled(false);

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        checkButtonsEnabled(false);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_start))
                .perform(click())
                .check(matches(withText("Stop")));

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_start))
                .check(matches(withText("Stop")));
    }

    @Test
    public void checkRotationWithAutomate() throws RemoteException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());

        onView(withText("Start")).check(matches(isDisplayed()));

        checkButtonsEnabled(false);
        checkEnterEnabled(false);

        device.setOrientationLeft();

        checkButtonsEnabled(false);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_start))
                .perform(click())
                .check(matches(withText("Stop")));

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        device.setOrientationNatural();

        checkButtonsEnabled(true);
        checkEnterEnabled(false);

        onView(withId(R.id.btn_start))
                .check(matches(withText("Stop")));
    }
}
