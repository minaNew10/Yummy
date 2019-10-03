package new10.example.com.myapplication.Fragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import new10.example.com.myapplication.Adapter.RecipeDetailsAdapter;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.R;

public class RecipeFragment extends Fragment {
    @BindView(R.id.recycler_recipe_detail)
    RecyclerView recyclerView;
    Recipe currRecipe;
    RecipeDetailsAdapter adapter;
}
