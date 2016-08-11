package com.example.piotr.robotium_showcase;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.test.runner.AndroidJUnit4;

import com.example.piotr.robotium_showcase.rule.MyActivityTestRule;
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

    @Rule
    public MyActivityTestRule<MainActivity> mActivityRule = new MyActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(mActivityRule.getInstrumentation());
        mActivityRule.getActivity();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Test(timeout = 5000)
    public void thisIsJustAnExampleOfMyWork() {
        solo.waitForActivity("MainActivity", 2000);
        solo.getText("Hello World").isCursorVisible();
    }
}

