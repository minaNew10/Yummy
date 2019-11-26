package new10.example.com.myapplication.ViewModel;

import androidx.lifecycle.ViewModel;

import new10.example.com.myapplication.Fragment.ChildFavListFragment;
import new10.example.com.myapplication.Fragment.ChildMainListFragment;

public class MainActivityViewModel extends ViewModel {
    private ChildMainListFragment mainListFragment;
    private ChildFavListFragment favListFragment;

    public ChildFavListFragment getFavFragment(){
        if(favListFragment == null){
            favListFragment = new ChildFavListFragment();
        }
        return favListFragment;
    }

    public ChildMainListFragment getMainFragment(){
        if(mainListFragment == null){
            mainListFragment = new ChildMainListFragment();
        }
        return mainListFragment;
    }
}
