package project.newstylefood.Detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

import project.newstylefood.Home;
import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.Model.Food;
import project.newstylefood.Model.Order;
import project.newstylefood.Model.userRate;
import project.newstylefood.R;
import project.newstylefood.ViewHolder.FoodViewHolder;
import project.newstylefood.ViewHolder.RateViewHolder;

public class FoodDetail extends AppCompatActivity
{
    TextView food_name,food_price,store_name,store_phone;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart,btnRating;
    ElegantNumberButton numberButton;
    private String FoodName,FoodImage,StoreId,SortNumber;
    private int FoodPrice;
    DatabaseReference userOrder,store,rate;
    FirebaseAuth firebaseAuth;
    RecyclerView recycler_rate;
    RecyclerView.LayoutManager layoutManager;
    int beforeStar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Bundle extras = getIntent().getExtras();
        FoodName = extras.getString("FoodName");
        FoodImage = extras.getString("FoodImage");
        StoreId = extras.getString("StoreId");
        FoodPrice = extras.getInt("FoodPrice");
        SortNumber = extras.getString("SortNumber");
        firebaseAuth = FirebaseAuth.getInstance();
        userOrder = FirebaseDatabase.getInstance().getReference("userOrder");
        store = FirebaseDatabase.getInstance().getReference("titles").child(StoreId);
        rate = FirebaseDatabase.getInstance().getReference("rate").child(SortNumber);

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        btnRating = (FloatingActionButton) findViewById(R.id.btnRate);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(FoodDetail.this,"已成功加入購物車",Toast.LENGTH_SHORT).show();
                Order myOrder = new Order(FoodName,Integer.parseInt(numberButton.getNumber()),FoodPrice,StoreId,SortNumber);
                userOrder.child(firebaseAuth.getCurrentUser().getUid()).child(SortNumber).setValue(myOrder);
            }
        });

        btnRating.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showRatingDialog();
            }
        });

        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.food_image);
        store_name = (TextView) findViewById(R.id.store_name);
        store_phone = (TextView) findViewById(R.id.store_phone);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        recycler_rate = (RecyclerView) findViewById(R.id.recycler_rate);
        recycler_rate.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_rate.setLayoutManager(layoutManager);
        recycler_rate.setNestedScrollingEnabled(false);

        getDetailFoodAndRate();
    }

    private void getDetailFoodAndRate()
    {
        Picasso.with(getBaseContext()).load(FoodImage).fit().centerInside()
                .memoryPolicy(MemoryPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE).into(food_image);

        food_name.setText(FoodName);
        food_price.setText(String.valueOf(FoodPrice));

        store.child("titleName").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                store_name.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        store.child("titlephone").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                store_phone.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        FirebaseRecyclerAdapter<userRate, RateViewHolder> adapter = new FirebaseRecyclerAdapter<userRate, RateViewHolder>
                (userRate.class, R.layout.rate_list_layout, RateViewHolder.class, rate.child("message"))
        {
            @Override
            protected void populateViewHolder(RateViewHolder viewHolder, userRate model, int position)
            {
                viewHolder.txtUserEmail.setText(model.getEmail());
                viewHolder.txtUserComment.setText(model.getComment());
                TextDrawable drawable = TextDrawable.builder().buildRound(""+model.getStar(), Color.RED);
                viewHolder.imageViewUserStar.setImageDrawable(drawable);

                final userRate local = model;
                viewHolder.setItemClickListener(new ItemClickListener()
                {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {

                    }
                });
            }
        };
        recycler_rate.setAdapter(adapter);

    }

    private void showRatingDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetail.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_rate,null);
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.userStar);
        final EditText editText = (EditText) view.findViewById(R.id.userComment);
        final Button submit = (Button) view.findViewById(R.id.userSubmit);
        final Button cancel = (Button) view.findViewById(R.id.userCancel);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.RatingDialogSlideAnim;
        dialog.show();

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if((int)ratingBar.getRating() == 0)
                {
                    Toast.makeText(FoodDetail.this,"星星數不可為零顆",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(editText.getText().toString().isEmpty())
                    {
                        Toast.makeText(FoodDetail.this,"評論不可為空白",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        final int userStar = (int) ratingBar.getRating();

                        rate.child("message").child(firebaseAuth.getCurrentUser().getUid()).child("star").addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot)
                            {
                                if(dataSnapshot.getValue(Integer.class)==null)
                                {
                                    beforeStar = 0;
                                }
                                else
                                {
                                    beforeStar = dataSnapshot.getValue(Integer.class);
                                }


                                rate.child("starCount").addListenerForSingleValueEvent(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        userRate myRate = new userRate(editText.getText().toString(),(int)ratingBar.getRating(), firebaseAuth.getCurrentUser().getEmail());
                                        rate.child("message").child(firebaseAuth.getCurrentUser().getUid()).setValue(myRate);
                                        int starCount = dataSnapshot.getValue(Integer.class);
                                        starCount = starCount + (userStar - beforeStar);
                                        rate.child("starCount").setValue(starCount);
                                        Toast.makeText(FoodDetail.this,"感謝您的回饋",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {

                            }
                        });

                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

    }

}
