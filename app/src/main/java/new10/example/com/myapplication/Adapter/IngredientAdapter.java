package new10.example.com.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import new10.example.com.myapplication.Model.Ingredient;
import new10.example.com.myapplication.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    List<Ingredient> items = new ArrayList<>();

    Context context;

    public IngredientAdapter() {

    }

    public void setItems(List<Ingredient> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public IngredientAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient item = items.get(position);
        Log.i(TAG, "onBindViewHolder: " + item.getIngredient());
        holder.txtvIngredientName.setText(item.getIngredient());
        holder.txtvIngredientMeasure.setText(item.getMeasure());
        holder.txtvIngredientQuant.setText(""+item.getQuantity());
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        return items.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtv_ingredient_name) TextView txtvIngredientName;
        @BindView(R.id.txtv_ingredient_quantity) TextView txtvIngredientQuant;
        @BindView(R.id.txtv_ingredient_measure) TextView txtvIngredientMeasure;
        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }
}
