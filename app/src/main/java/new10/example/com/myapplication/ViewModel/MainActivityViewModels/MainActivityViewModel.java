package new10.example.com.myapplication.ViewModel.MainActivityViewModels;

import androidx.lifecycle.ViewModel;

import new10.example.com.myapplication.Fragment.MainActivityFragments.ChildFavListFragment;
import new10.example.com.myapplication.Fragment.MainActivityFragments.ChildMainListFragment;

public class MainActivityViewModel extends ViewModel {
    private ChildMainListFragment mainListFragment;
    private ChildFavListFragment favListFragment;

    public ChildFavListFragment getFavFragment(){
            favListFragment = new ChildFavListFragment();
        return favListFragment;
    }

    public ChildMainListFragment getMainFragment(){
        if(mainListFragment == null){
            mainListFragment = new ChildMainListFragment();
        }
        return mainListFragment;
    }
}
