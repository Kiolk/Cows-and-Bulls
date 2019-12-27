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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimerTest {

    UiDevice device;

    @Rule
    public ActivityTestRule<GameActivity> activityRule = new ActivityTestRule<>(GameActivity.class);

    @Before
    public void setup(){
        device = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void checkCorrectInitTimerState(){
        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:00")));
    }

    @Test
    public void checkInitRotationTimerState() throws RemoteException {
        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:00")));

        UiDevice device = UiDevice.getInstance(getInstrumentation());

        device.setOrientationLeft();

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:00")));

        device.setOrientationNatural();

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:00")));
    }

    @Test
    public void checkTimerTic(){
        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:00")));

        onView(withId(R.id.btn_start))
                .perform(click());

        SystemClock.sleep(1200);

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:01")));

        SystemClock.sleep(5200);

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:06")));

        SystemClock.sleep(1200);

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:07")));
    }


    @Test
    public void checkWorkDuringRotation() throws RemoteException {
        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:00")));

        onView(withId(R.id.btn_start))
                .perform(click());

        device.setOrientationLeft();

        SystemClock.sleep(800);

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:00")));

        device.setOrientationLeft();

        SystemClock.sleep(1200);

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:01")));

        device.setOrientationNatural();

        SystemClock.sleep(1200);

        onView(withId(R.id.timer_tv))
                .check(matches(withText("00:02")));
    }
}
