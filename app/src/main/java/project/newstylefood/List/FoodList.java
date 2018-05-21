package project.newstylefood.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import project.newstylefood.Detail.FoodDetail;
import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.Model.Food;
import project.newstylefood.R;
import project.newstylefood.ViewHolder.FoodViewHolder;

public class FoodList extends AppCompatActivity
{
    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference food,store;
    private String SortId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        recycler_food = (RecyclerView) findViewById(R.id.recycler_food);
        recycler_food.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_food.setLayoutManager(layoutManager);
        recycler_food.setItemViewCacheSize(4);
        recycler_food.setDrawingCacheEnabled(true);
        recycler_food.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        Bundle extras = getIntent().getExtras();
        SortId = extras.getString("SortId");
        food = FirebaseDatabase.getInstance().getReference("classification").child("menus").child(SortId);

        loadListFood();
    }

    private void loadListFood()
    {
        FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>
                (Food.class,R.layout.food_list_layout,FoodViewHolder.class,food)
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position)
            {
                viewHolder.txtFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).fit().centerInside()
                        .memoryPolicy(MemoryPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(viewHolder.imageViewFood);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(FoodList.this, FoodDetail.class);
                        intent.putExtra("FoodImage",local.getImage());
                        intent.putExtra("FoodPrice",local.getPrice());
                        intent.putExtra("FoodName",local.getName());
                        intent.putExtra("StoreId",local.getStore());
                        intent.putExtra("SortNumber",local.getSortNumber());
                        startActivity(intent);
                    }
                });
            }
        };

        recycler_food.setAdapter(adapter);
    }
}
