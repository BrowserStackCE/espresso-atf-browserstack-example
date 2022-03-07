package com.sample.browserstack.samplecalculator;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.accessibility.AccessibilityChecks;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Espresso tests to ensure that editText box is updated appropriately
 * whenever buttons are clicked
 */

@SmallTest
@RunWith(AndroidJUnit4.class)
public class BrowserstackAccessibilityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity;

    @BeforeClass
    public static void enableAccessibilityChecks() {
        System.out.println("Accessing");
        AccessibilityChecks.enable().setRunChecksFromRootView(true);
//        mainActivity = activityRule.getActivity();
    }
    @Before
    public void setUp() {
//        AccessibilityChecks.enable();
        System.out.println("Setting UP");
        mainActivity = activityRule.getActivity();
    }

    @Test
    public void ensureSingleInputIsHandled() {
        Espresso.onView(withId(R.id.buttonOne)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.editText)).check(matches(withText("1")));
    }



}
