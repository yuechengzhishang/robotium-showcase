package com.example.piotr.robotium_showcase;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author piotr on 10.08.16.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private Solo solo;

    private static final String MAIN_ACTIVITY = MainActivity.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), mActivityRule.getActivity());
    }

    @Test
    public void checkIfMainActivityIsProperlyDisplayed() throws InterruptedException {
        solo.waitForActivity("MainActivity", 2000);
        solo.assertCurrentActivity(mActivityRule.getActivity().getString(
                R.string.error_no_class_def_found, MAIN_ACTIVITY), MAIN_ACTIVITY);
        solo.getText("Hello World").isShown();

    }
}

