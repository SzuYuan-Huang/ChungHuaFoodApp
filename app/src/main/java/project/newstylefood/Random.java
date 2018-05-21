package project.newstylefood;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.newstylefood.Detail.FoodDetail;
import project.newstylefood.Model.Food;

public class Random extends AppCompatActivity
{

    Toolbar random;
    DatabaseReference randomFood;
    ImageButton randomFood1,randomFood2,randomFood3,randomFood4,randomFood5;
    List<Food> foodList;
    private int[] foodTemp;
    final static int FOODNUMBER = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        foodList = new ArrayList<>();
        foodList.clear();
        foodTemp = new int[FOODNUMBER];

        randomFood = FirebaseDatabase.getInstance().getReference("classification").child("menus");
        randomFood1 = (ImageButton) findViewById(R.id.randomFood1);
        randomFood2 = (ImageButton) findViewById(R.id.randomFood2);
        randomFood3 = (ImageButton) findViewById(R.id.randomFood3);
        randomFood4 = (ImageButton) findViewById(R.id.randomFood4);
        randomFood5 = (ImageButton) findViewById(R.id.randomFood5);

        random = (Toolbar) findViewById(R.id.random);
        random.setTitle("翻牌");
        random.setTitleTextColor(Color.WHITE);
        random.inflateMenu(R.menu.random_menu);

        random.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.noodle:
                        Toast.makeText(Random.this,"您選的是麵類",Toast.LENGTH_SHORT).show();
                        Noodle();
                        break;
                    case R.id.porridge:
                        Toast.makeText(Random.this,"您選的是粥類",Toast.LENGTH_SHORT).show();
                        Porridge();
                        break;
                    case R.id.rice:
                        Toast.makeText(Random.this,"您選的是飯類",Toast.LENGTH_SHORT).show();
                        Rice();
                        break;
                    case R.id.coverRice:
                        Toast.makeText(Random.this,"您選的是蓋飯類",Toast.LENGTH_SHORT).show();
                        coverRice();
                        break;
                }

                return true;
            }
        });

        randomFood1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(foodList.isEmpty())
                {
                    Toast.makeText(Random.this,"請選則您要哪一類？",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Food myChoice = foodList.get(foodTemp[0]);
                    Intent intent = new Intent(Random.this, FoodDetail.class);
                    intent.putExtra("FoodImage", myChoice.getImage());
                    intent.putExtra("FoodPrice", myChoice.getPrice());
                    intent.putExtra("FoodName", myChoice.getName());
                    intent.putExtra("StoreId", myChoice.getStore());
                    intent.putExtra("SortNumber", myChoice.getSortNumber());
                    startActivity(intent);
                }
            }
        });

        randomFood2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(foodList.isEmpty())
                {
                    Toast.makeText(Random.this,"請選則您要哪一類？",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Food myChoice = foodList.get(foodTemp[1]);
                    Intent intent = new Intent(Random.this, FoodDetail.class);
                    intent.putExtra("FoodImage", myChoice.getImage());
                    intent.putExtra("FoodPrice", myChoice.getPrice());
                    intent.putExtra("FoodName", myChoice.getName());
                    intent.putExtra("StoreId", myChoice.getStore());
                    intent.putExtra("SortNumber", myChoice.getSortNumber());
                    startActivity(intent);
                }
            }
        });

        randomFood3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(foodList.isEmpty())
                {
                    Toast.makeText(Random.this,"請選則您要哪一類？",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Food myChoice = foodList.get(foodTemp[2]);
                    Intent intent = new Intent(Random.this, FoodDetail.class);
                    intent.putExtra("FoodImage", myChoice.getImage());
                    intent.putExtra("FoodPrice", myChoice.getPrice());
                    intent.putExtra("FoodName", myChoice.getName());
                    intent.putExtra("StoreId", myChoice.getStore());
                    intent.putExtra("SortNumber", myChoice.getSortNumber());
                    startActivity(intent);
                }
            }
        });

        randomFood4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(foodList.isEmpty())
                {
                    Toast.makeText(Random.this,"請選則您要哪一類？",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Food myChoice = foodList.get(foodTemp[3]);
                    Intent intent = new Intent(Random.this, FoodDetail.class);
                    intent.putExtra("FoodImage", myChoice.getImage());
                    intent.putExtra("FoodPrice", myChoice.getPrice());
                    intent.putExtra("FoodName", myChoice.getName());
                    intent.putExtra("StoreId", myChoice.getStore());
                    intent.putExtra("SortNumber", myChoice.getSortNumber());
                    startActivity(intent);
                }
            }
        });

        randomFood5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(foodList.isEmpty())
                {
                    Toast.makeText(Random.this,"請選則您要哪一類？",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Food myChoice = foodList.get(foodTemp[4]);
                    Intent intent = new Intent(Random.this, FoodDetail.class);
                    intent.putExtra("FoodImage", myChoice.getImage());
                    intent.putExtra("FoodPrice", myChoice.getPrice());
                    intent.putExtra("FoodName", myChoice.getName());
                    intent.putExtra("StoreId", myChoice.getStore());
                    intent.putExtra("SortNumber", myChoice.getSortNumber());
                    startActivity(intent);
                }
            }
        });

    }

    public void Noodle()
    {
        randomFood.child("Noodles").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                foodList.clear();
                for(DataSnapshot noodle : dataSnapshot.getChildren())
                {
                    Food myFood = noodle.getValue(Food.class);
                    foodList.add(myFood);
                }

                for(int i=0;i < FOODNUMBER;i++)
                {
                    foodTemp[i] = (int) (Math.random() * dataSnapshot.getChildrenCount());
                    for(int j=0; j<i ; j++)
                    {
                        if(foodTemp[i]==foodTemp[j])
                        {
                            i--;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    public void Porridge()
    {
        randomFood.child("Porridge").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                foodList.clear();
                for(DataSnapshot porridge : dataSnapshot.getChildren())
                {
                    Food myFood = porridge.getValue(Food.class);
                    foodList.add(myFood);
                }

                for(int i=0;i < FOODNUMBER;i++)
                {
                    foodTemp[i] = (int) (Math.random() * dataSnapshot.getChildrenCount());
                    for(int j=0; j<i ; j++)
                    {
                        if(foodTemp[i]==foodTemp[j])
                        {
                            i--;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    public void Rice()
    {
        randomFood.child("Rice").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                foodList.clear();
                for(DataSnapshot rice : dataSnapshot.getChildren())
                {
                    Food myFood = rice.getValue(Food.class);
                    foodList.add(myFood);
                }

                for(int i=0;i < FOODNUMBER;i++)
                {
                    foodTemp[i] = (int) (Math.random() * dataSnapshot.getChildrenCount());
                    for(int j=0; j<i ; j++)
                    {
                        if(foodTemp[i]==foodTemp[j])
                        {
                            i--;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    public void coverRice()
    {
        randomFood.child("Coverrice").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                foodList.clear();
                for(DataSnapshot coverRice : dataSnapshot.getChildren())
                {
                    Food myFood = coverRice.getValue(Food.class);
                    foodList.add(myFood);
                }

                for(int i=0;i < FOODNUMBER;i++)
                {
                    foodTemp[i] = (int) (Math.random() * dataSnapshot.getChildrenCount());
                    for(int j=0; j<i ; j++)
                    {
                        if(foodTemp[i]==foodTemp[j])
                        {
                            i--;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

}
