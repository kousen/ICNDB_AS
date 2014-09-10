package com.nfjs.icndb.app;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testMainActivity() {
        solo.assertCurrentActivity("MainActivity", MainActivity.class);
    }

    public void testJokeButton() {
        solo.clickOnButton("Get Joke");
        assertTrue(solo.searchText("Champeau"));
    }

}
