package project.newstylefood.Detail;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import project.newstylefood.Home;
import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.List.FoodList;
import project.newstylefood.Model.Food;
import project.newstylefood.Model.Store;
import project.newstylefood.Model.userRate;
import project.newstylefood.R;
import project.newstylefood.ViewHolder.FoodViewHolder;
import project.newstylefood.ViewHolder.RateViewHolder;

public class StoreDetail extends AppCompatActivity {
    TextView store_name, store_address, store_phone;
    ImageView store_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnDirection;
    private String StoreName, StoreImage, StoreAddress, StorePhone, StoreId;
    private double StoreLatitude,StoreLongitude;
    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        Bundle extra = getIntent().getExtras();
        StoreName = extra.getString("storeName");
        StoreImage = extra.getString("storeImage");
        StoreId = extra.getString("storeId");
        StoreAddress = extra.getString("storeAddress");
        StorePhone = extra.getString("storePhone");
        StoreLongitude = extra.getDouble("storeLongitude");
        StoreLatitude = extra.getDouble("storeLatitude");

        store_name = (TextView) findViewById(R.id.store_name);
        store_address = (TextView) findViewById(R.id.store_address);
        store_phone = (TextView) findViewById(R.id.store_phone);
        store_image = (ImageView) findViewById(R.id.store_image);
        btnDirection = (FloatingActionButton) findViewById(R.id.btnDirection);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        recycler_food = (RecyclerView) findViewById(R.id.recycler_food);
        recycler_food.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_food.setLayoutManager(layoutManager);
        recycler_food.setItemViewCacheSize(4);
        recycler_food.setDrawingCacheEnabled(true);
        recycler_food.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recycler_food.setNestedScrollingEnabled(false);

        food = FirebaseDatabase.getInstance().getReference("titles").child(StoreId).child("food");

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getCurrentLocation();
            }
        });

        loadStoreDetail();
    }


    private void loadStoreDetail()
    {
        Picasso.with(getBaseContext()).load(StoreImage).fit().centerInside()
                .memoryPolicy(MemoryPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE).into(store_image);

        store_name.setText(StoreName);
        store_address.setText(StoreAddress);
        store_phone.setText(StorePhone);

        FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>
                (Food.class, R.layout.food_list_layout, FoodViewHolder.class, food)
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position)
            {
                viewHolder.txtFoodName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).fit().centerInside()
                        .memoryPolicy(MemoryPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(viewHolder.imageViewFood);
                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener()
                {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {
                        Intent intent = new Intent(StoreDetail.this, FoodDetail.class);
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

    private void getCurrentLocation()
    {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + StoreLongitude + "," + StoreLatitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
