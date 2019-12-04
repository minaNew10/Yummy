package new10.example.com.myapplication.Fragment.MainActivityFragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import new10.example.com.myapplication.Activity.MainActivity;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.Model.Recipe;
import new10.example.com.myapplication.Model.Step;
import new10.example.com.myapplication.R;
import new10.example.com.myapplication.Repository.FavRecipeRepository;
import new10.example.com.myapplication.Utils.CursorViewModel;
import new10.example.com.myapplication.ViewModel.MainActivityViewModels.FavRecipesFragmentViewModel;

public class ChildFavListFragment extends ParentFragmentForMainlist {
    private FavRecipesFragmentViewModel viewModel;
    private void setupViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(FavRecipesFragmentViewModel.class);
//        viewModel.getFavRecipes(getActivity()).observe(getViewLifecycleOwner(), new Observer<Cursor>() {
//            @Override
//            public void onChanged(Cursor cursor) {
//                List<Recipe> recipesList = new ArrayList<>();
//                int id_index = cursor.getColumnIndex(Recipe.COLUMN_ID);
//                int name_index = cursor.getColumnIndex(Recipe.COLUMN_NAME);
//                int serving_index = cursor.getColumnIndex(Recipe.COLUMN_SERVINGS);
//                int image_index = cursor.getColumnIndex(Recipe.COLUMN_IMAGE);
//
//                while (cursor.moveToNext()) {
//                    int id = cursor.getInt(id_index);
//                    String name = cursor.getString(name_index);
//                    String image = cursor.getString(image_index);
//                    int servings = cursor.getInt(serving_index);
//                    Log.i("recipe changed ", "onChanged: " + name);
//
//                    Recipe recipe = new Recipe(id, name, servings, image);
//                    viewModel.getFavRecipeSteps(getActivity(), recipe.getId()).observe(getViewLifecycleOwner(), new Observer<Cursor>() {
//                        @Override
//                        public void onChanged(Cursor cursor) {
//                            List<Step> steps = new ArrayList<>();
//                            int id_index = cursor.getColumnIndex(Step.COLUMN_ID);
//                            int desc_index = cursor.getColumnIndex(Step.COLUMN_DESCRIPTION);
//                            int short_desc_index = cursor.getColumnIndex(Step.COLUMN_SHORT_DESCRIPTION);
//                            int thumbnail_index = cursor.getColumnIndex(Step.COLUMN_THUMBNAIL_URL);
//                            int video_index = cursor.getColumnIndex(Step.COLUMN_VIDEO_URL);
//                            int recipe_id_index = cursor.getColumnIndex(Step.COLUMN_RECIPE_ID);
//                            while (cursor.moveToNext()) {
//                                int id = cursor.getInt(id_index);
//                                String desc = cursor.getString(desc_index);
//                                String short_desc = cursor.getString(short_desc_index);
//                                String videoUrl = cursor.getString(video_index);
//                                String thumb = cursor.getString(thumbnail_index);
//                                int recipe_id = cursor.getInt(recipe_id_index);
//                                Step step = new Step(id, short_desc, desc, videoUrl, thumb, recipe_id);
//                                steps.add(step);
//                            }
//                            recipe.setSteps(steps);
//                        }
//                    });
//                    viewModel.getFavRecipeIngredients(getActivity(), recipe.getId()).observe(getViewLifecycleOwner(), new Observer<Cursor>() {
//                        @Override
//                        public void onChanged(Cursor cursor) {
//                            List<Ingredient> ingredients = new ArrayList<>();
//                            int id_index = cursor.getColumnIndex(Ingredient.COLUMN_ID);
//                            int name_index = cursor.getColumnIndex(Ingredient.COLUMN_NAME);
//                            int measure_index = cursor.getColumnIndex(Ingredient.COLUMN_MEASURE);
//                            int quan_index = cursor.getColumnIndex(Ingredient.COLUMN_QUANTITY);
//                            int recipe_id_index = cursor.getColumnIndex(Ingredient.COLUMN_RECIPE_ID);
//                            while (cursor.moveToNext()) {
//                                int id = cursor.getInt(id_index);
//                                String name = cursor.getString(name_index);
//                                String measure = cursor.getString(measure_index);
//                                float quan = cursor.getFloat(quan_index);
//                                int recipe_id = cursor.getInt(recipe_id_index);
//                                Ingredient ingredient = new Ingredient(quan, measure, name);
//                                ingredients.add(ingredient);
//                            }
//                            recipe.setIngredients(ingredients);
//                        }
//                    });
//                    recipesList.add(recipe);
//                }
//                adapter.setRecipes(recipesList);
//
//            }
//        });
        viewModel.getMyLiveDataFavRecipes(getActivity()).observe(getViewLifecycleOwner(), new Observer<Cursor>() {
            @Override
            public void onChanged(Cursor cursor) {
                List<Recipe> recipesList = new ArrayList<>();
                int id_index = cursor.getColumnIndex(Recipe.COLUMN_ID);
                int name_index = cursor.getColumnIndex(Recipe.COLUMN_NAME);
                int serving_index = cursor.getColumnIndex(Recipe.COLUMN_SERVINGS);
                int image_index = cursor.getColumnIndex(Recipe.COLUMN_IMAGE);

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(id_index);
                    String name = cursor.getString(name_index);
                    String image = cursor.getString(image_index);
                    int servings = cursor.getInt(serving_index);
                    Recipe recipe = new Recipe(id, name, servings, image);
                    viewModel.getFavRecipeSteps(getActivity(), recipe.getId()).observe(getViewLifecycleOwner(), new Observer<Cursor>() {
                        @Override
                        public void onChanged(Cursor cursor) {
                            List<Step> steps = new ArrayList<>();
                            int id_index = cursor.getColumnIndex(Step.COLUMN_ID);
                            int desc_index = cursor.getColumnIndex(Step.COLUMN_DESCRIPTION);
                            int short_desc_index = cursor.getColumnIndex(Step.COLUMN_SHORT_DESCRIPTION);
                            int thumbnail_index = cursor.getColumnIndex(Step.COLUMN_THUMBNAIL_URL);
                            int video_index = cursor.getColumnIndex(Step.COLUMN_VIDEO_URL);
                            int recipe_id_index = cursor.getColumnIndex(Step.COLUMN_RECIPE_ID);
                            while (cursor.moveToNext()) {
                                int id = cursor.getInt(id_index);
                                String desc = cursor.getString(desc_index);
                                String short_desc = cursor.getString(short_desc_index);
                                String videoUrl = cursor.getString(video_index);
                                String thumb = cursor.getString(thumbnail_index);
                                int recipe_id = cursor.getInt(recipe_id_index);
                                Step step = new Step(id, short_desc, desc, videoUrl, thumb, recipe_id);
                                steps.add(step);
                            }
                            recipe.setSteps(steps);
                        }
                    });
                    viewModel.getFavRecipeIngredients(getActivity(), recipe.getId()).observe(getViewLifecycleOwner(), new Observer<Cursor>() {
                        @Override
                        public void onChanged(Cursor cursor) {
                            List<Ingredient> ingredients = new ArrayList<>();
                            int id_index = cursor.getColumnIndex(Ingredient.COLUMN_ID);
                            int name_index = cursor.getColumnIndex(Ingredient.COLUMN_NAME);
                            int measure_index = cursor.getColumnIndex(Ingredient.COLUMN_MEASURE);
                            int quan_index = cursor.getColumnIndex(Ingredient.COLUMN_QUANTITY);
                            int recipe_id_index = cursor.getColumnIndex(Ingredient.COLUMN_RECIPE_ID);
                            while (cursor.moveToNext()) {
                                int id = cursor.getInt(id_index);
                                String name = cursor.getString(name_index);
                                String measure = cursor.getString(measure_index);
                                float quan = cursor.getFloat(quan_index);
                                int recipe_id = cursor.getInt(recipe_id_index);
                                Ingredient ingredient = new Ingredient(quan, measure, name);
                                ingredients.add(ingredient);
                            }
                            recipe.setIngredients(ingredients);
                        }
                    });
                    recipesList.add(recipe);
                }
                adapter.setRecipes(recipesList);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //we intialize the view model here and not in oncreate because we want the data to be updated every time we create a view as
        // there is alife cycle for the fragmet instance and another for the view it contains so if we set the view model in oncreate
        //the data will not be updated as it is linked to the life cycle of the fragment itself we also want to be sure that the Activity is created
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.show_fav_recipes));
        setupViewModel();
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setTitle(R.string.show_main_list);
    }
}
