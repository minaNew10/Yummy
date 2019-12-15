package new10.example.com.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import new10.example.com.myapplication.Activity.MainActivity;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {
    private static final String TAG = "MainActivityScreenTest";
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSampleRecyclerVisible(){
        onView(ViewMatchers.withId(R.id.recycler_recipes_main))
                .inRoot(RootMatchers.withDecorView(Matchers.is(mMainActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCaseForRecyclerScroll(){
        RecyclerView recyclerView = mMainActivityTestRule.getActivity().findViewById(R.id.recycler_recipes_main);
        int itemCount = recyclerView.getAdapter().getItemCount();

        onView(ViewMatchers.withId(R.id.recycler_recipes_main))
                .inRoot(RootMatchers.withDecorView(Matchers.is(mMainActivityTestRule.getActivity().getWindow().getDecorView())))
                .perform(RecyclerViewActions.scrollToPosition(itemCount-1));
    }

    @Test
    public void clickRecyclerView_opensRecipeActivity(){

     onView(ViewMatchers.withId(R.id.recycler_recipes_main))
             .inRoot(RootMatchers.withDecorView(Matchers.is(mMainActivityTestRule.getActivity().getWindow().getDecorView())))
             .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

     onView(allOf(instanceOf(TextView.class),
                withParent(withResourceName("action_bar"))))
                .check(matches(withText("Nutella Pie")));
    }

//    @Test
//    public void testCaseForRecyclerItemView(){
//        onView(ViewMatchers.withId(R.id.recycler_recipes_main))
//                .inRoot(RootMatchers.withDecorView(Matchers.is(mMainActivityTestRule.getActivity().getWindow().getDecorView())))
//                .check(matches(withViewAtPosition(0,Matchers.allOf(ViewMatchers.withId(R.id.constraint_itemview),isDisplayed()))));
//    }
//
//    public Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher){
//        return new BoundedMatcher<View,RecyclerView>(RecyclerView.class) {
//            @Override
//            public void describeTo(Description description) {
//                itemMatcher.describeTo(description);
//            }
//
//            @Override
//            protected boolean matchesSafely(RecyclerView item) {
//
//                final RecyclerView.ViewHolder viewHolder = item.findViewHolderForAdapterPosition(position);
//                Log.i(TAG, "matchesSafely: viewHolderid = " + viewHolder.getItemId());
//                Log.i(TAG, "matchesSafely: viewHolder itemView = " + viewHolder.itemView);
//                Log.i(TAG, "matchesSafely: itemMatcheer = " + itemMatcher.matches(viewHolder.itemView) );
//                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
//            }
//        };
//    }
}
