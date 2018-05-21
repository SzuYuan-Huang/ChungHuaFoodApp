package project.newstylefood.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import project.newstylefood.Detail.FoodDetail;
import project.newstylefood.Detail.StoreDetail;
import project.newstylefood.Home;
import project.newstylefood.Model.Food;
import project.newstylefood.Model.Rank;
import project.newstylefood.Model.Rate;
import project.newstylefood.Model.Store;
import project.newstylefood.R;

public class RankList extends AppCompatActivity
{

    Toolbar rankTitle;

    ListView listViewRank;

    DatabaseReference rate,menu;

    List<Food> foodList;

    int[] starCount;
    String[] sortNumber;
    String[] sort;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);

        rankTitle = (Toolbar) findViewById(R.id.rankTitle);
        rankTitle.setTitle("美食排名");
        rankTitle.setTitleTextColor(Color.WHITE);
        listViewRank = (ListView) findViewById(R.id.listViewRank);
        listViewRank.setDrawingCacheEnabled(true);
        listViewRank.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rate = FirebaseDatabase.getInstance().getReference("rate");
        menu = FirebaseDatabase.getInstance().getReference("classification").child("menus");
        foodList = new ArrayList<>();

        listViewRank.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Food myFood = foodList.get(position);
                Intent intent = new Intent(RankList.this, FoodDetail.class);
                intent.putExtra("FoodName",myFood.getName());
                intent.putExtra("FoodImage",myFood.getImage());
                intent.putExtra("StoreId",myFood.getStore());
                intent.putExtra("FoodPrice",myFood.getPrice());
                intent.putExtra("SortNumber",myFood.getSortNumber());
                startActivity(intent);
            }
        });

        loadRank();

    }


    public void loadRank()
    {
        rate.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                foodList.clear();
                starCount = new int[(int) dataSnapshot.getChildrenCount()];
                sortNumber = new String[(int) dataSnapshot.getChildrenCount()];
                sort = new String[(int) dataSnapshot.getChildrenCount()];
                int temp=0;
                for(DataSnapshot rate : dataSnapshot.getChildren())
                {
                    Rate rankRate = rate.getValue(Rate.class);
                    starCount[temp] = rankRate.getStarCount();
                    sortNumber[temp] = rankRate.getSortNumber();
                    sort[temp] = rankRate.getSort();
                    temp++;
                }

                for(int i=0;i<dataSnapshot.getChildrenCount();i++)
                {
                    for(int j=i;j<dataSnapshot.getChildrenCount();j++)
                    {
                        if(starCount[j] > starCount[i])
                        {
                            int startCountTemp = starCount[j];
                            starCount[j] = starCount[i];
                            starCount[i] = startCountTemp;

                            String sortNumberTemp = sortNumber[j];
                            sortNumber[j] = sortNumber[i];
                            sortNumber[i] = sortNumberTemp;

                            String sortTemp = sort[j];
                            sort[j] = sort[i];
                            sort[i] = sortTemp;
                        }
                    }
                }

                for(int i=0;i<10;i++)
                {
                    menu.child(sort[i]).child(sortNumber[i]).addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            Food rankFood = dataSnapshot.getValue(Food.class);
                            foodList.add(rankFood);
                            RankListAdapter adapter = new RankListAdapter(RankList.this,foodList);
                            listViewRank.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
}
