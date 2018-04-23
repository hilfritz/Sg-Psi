package com.hilfritz.mvp.ui.placelist;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import com.hilfritz.mvp.BaseInstrumentationTest2;
import com.hilfritz.mvp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Hilfritz Camallere on 17/7/17.
 */

public class PlaceListFragmentTest extends BaseInstrumentationTest2 {

    @Rule
    public ActivityTestRule<PlaceListActivity> placeListActivity = new ActivityTestRule<>(PlaceListActivity.class);

    @Test
    public void loadAndClickPlacesList(){
        sleep(3000);
        final Fragment fragment = placeListActivity.getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        final int id = fragment.getView().findViewById(R.id.list_view).getId();
        onView(withId(id)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        sleep(2000);
        onView(withId(id)).perform(RecyclerViewActions.scrollToPosition(5));
        onView(withId(id)).perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        sleep(3000);
    }
}
