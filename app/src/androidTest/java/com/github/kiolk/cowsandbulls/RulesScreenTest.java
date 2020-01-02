package com.github.kiolk.cowsandbulls;

import android.os.RemoteException;
import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import com.github.kiolk.cowsandbulls.ui.screens.GameActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RulesScreenTest {

    private UiDevice device;

    @Rule
    public ActivityTestRule<GameActivity> activityRule =
            new ActivityTestRule<>(GameActivity.class);

    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void checkRulesButton() throws RemoteException {
        checkButtonVisibility(true);

        device.setOrientationLeft();

        checkButtonVisibility(true);
    }

    @Test
    public void checkOpenRules() {
        checkButtonVisibility(true);

        checkRules(false);

        openRules();

        SystemClock.sleep(400);

        checkRules(true);

        device.pressBack();

        SystemClock.sleep(400);

        checkRules(false);

        openRules();

        SystemClock.sleep(400);

        checkRules(true);

        closeByBasckPressed();

        SystemClock.sleep(400);

        checkRules(false);
    }

    @Test
    public void checkRotationChangeForRulesScreen() throws RemoteException {
        checkButtonVisibility(true);

        checkRules(false);

        openRules();

        SystemClock.sleep(400);

        checkRules(true);

        onView(withId(R.id.tv_result_title))
                .check(matches(withText("Rules")))
                .check(matches(isDisplayed()));

        device.setOrientationLeft();

        checkRules(true);

        device.setOrientationNatural();

        checkRules(true);
    }

    @Test
    public void checkOpenAfterResult(){
        checkButtonVisibility(true);

        checkRules(false);

        openRules();

        SystemClock.sleep(400);

        checkRules(true);

        device.pressBack();

        SystemClock.sleep(400);

        checkRules(false);

        onView(withId(R.id.btn_result))
            .perform(click());

        SystemClock.sleep(400);

        onView(withId(R.id.tv_rules_description))
                .check(doesNotExist());

        device.pressBack();

        SystemClock.sleep(400);

        checkRules(false);

        openRules();

        SystemClock.sleep(400);

        checkRules(true);

        closeByBasckPressed();

        SystemClock.sleep(400);

        checkRules(false);
    }

    private void checkRules(Boolean isVisible) {
        if (isVisible) {
            onView(withId(R.id.tv_result_title))
                    .check(matches(withText("Rules")))
                    .check(matches(isDisplayed()));

            onView(withId(R.id.iv_result_back_button))
                    .check(matches(isDisplayed()));

            onView(withId(R.id.tv_rules_description))
                    .check(matches(isDisplayed()));
        }else{
            onView(withId(R.id.tv_result_title))
                    .check(doesNotExist());

            onView(withId(R.id.iv_result_back_button))
                    .check(doesNotExist());

            onView(withId(R.id.tv_rules_description))
                    .check(doesNotExist());
        }
    }

    private void closeByBasckPressed(){
        onView(withId(R.id.iv_result_back_button))
                .perform(click());
    }

    private void openRules() {
        onView(withId(R.id.btn_rules))
                .perform(click());
    }


    private void checkButtonVisibility(Boolean isVisible) {
        if (isVisible) {
            onView(withId(R.id.btn_rules))
                    .check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.btn_rules))
                    .check(matches(not(isCompletelyDisplayed())));
        }
    }
}
