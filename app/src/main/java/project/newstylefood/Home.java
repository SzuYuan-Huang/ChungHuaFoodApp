package project.newstylefood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;

import project.newstylefood.BackgroundService.Message;
import project.newstylefood.Detail.StoreDetail;
import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.List.FoodList;
import project.newstylefood.List.HistoryList;
import project.newstylefood.List.RankList;
import project.newstylefood.Model.Sort;
import project.newstylefood.Model.Store;
import project.newstylefood.ViewHolder.SortViewHolder;
import project.newstylefood.ViewHolder.StoreViewHolder;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference store,user,sort;
    private FirebaseAuth firebaseAuth;
    private TextView txtFullName;
    private Toolbar toolbar;

    RecyclerView recycler_store,recycler_sort;
    RecyclerView.LayoutManager layoutManager;

    Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        service = new Intent(Home.this, Message.class);
        startService(service);

        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("店家搜尋");
        setSupportActionBar(toolbar);

        store = FirebaseDatabase.getInstance().getReference("titles");
        user = FirebaseDatabase.getInstance().getReference("users");
        sort = FirebaseDatabase.getInstance().getReference("sort");
        firebaseAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.myCart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,Cart.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);

        user.child(firebaseAuth.getCurrentUser().getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtFullName.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        
        loadStore();
    }

    private void loadStore()
    {
        toolbar.setTitle("店家搜尋");
        recycler_store = (RecyclerView) findViewById(R.id.recycler_list);
        recycler_store.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_store.setLayoutManager(layoutManager);
        recycler_store.setItemViewCacheSize(4);
        recycler_store.setDrawingCacheEnabled(true);
        recycler_store.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        FirebaseRecyclerAdapter<Store,StoreViewHolder> adapter = new FirebaseRecyclerAdapter<Store, StoreViewHolder>
                (Store.class,R.layout.store_list_layout,StoreViewHolder.class,store)
        {
            @Override
            protected void populateViewHolder(StoreViewHolder viewHolder, Store model, int position)
            {
                viewHolder.txtStoreName.setText(model.getTitleName());
                Picasso.with(getBaseContext()).load(model.getTitleImage()).fit().centerInside()
                        .memoryPolicy(MemoryPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(viewHolder.imageViewStore);
                final Store clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {
                        //Toast.makeText(Home.this,""+clickItem.getTitleName(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Home.this, StoreDetail.class);
                        intent.putExtra("storeId",clickItem.getTitleId());
                        intent.putExtra("storeImage",clickItem.getTitleImage());
                        intent.putExtra("storeName",clickItem.getTitleName());
                        intent.putExtra("storeAddress",clickItem.getTitleAddress());
                        intent.putExtra("storePhone",clickItem.getTitlephone());
                        intent.putExtra("storeLatitude",clickItem.getLatitude());
                        intent.putExtra("storeLongitude",clickItem.getLongitude());
                        startActivity(intent);
                    }
                });
            }
        };
        recycler_store.setAdapter(adapter);
    }

    private void loadSort()
    {
        toolbar.setTitle("菜單搜尋");
        recycler_sort = (RecyclerView) findViewById(R.id.recycler_list);
        recycler_sort.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_sort.setLayoutManager(layoutManager);
        recycler_sort.setItemViewCacheSize(4);
        recycler_sort.setDrawingCacheEnabled(true);
        recycler_sort.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        FirebaseRecyclerAdapter<Sort,SortViewHolder> adapter = new FirebaseRecyclerAdapter<Sort, SortViewHolder>
                (Sort.class,R.layout.sort_list_layout,SortViewHolder.class,sort)
        {
            @Override
            protected void populateViewHolder(SortViewHolder viewHolder, final Sort model, int position)
            {
                viewHolder.txtSortName.setText(model.getChineseName());
                Picasso.with(getBaseContext()).load(model.getImage()).fit().centerInside()
                        .memoryPolicy(MemoryPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(viewHolder.imageViewSort);
                final Sort clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick)
                    {
                        Intent intent = new Intent(Home.this, FoodList.class);
                        intent.putExtra("SortId",model.getEnglishName());
                        startActivity(intent);
                    }
                });
            }
        };
        recycler_sort.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_store)
        {
            // Handle the camera action
            loadStore();
        }
        else if (id == R.id.nav_menu)
        {
            loadSort();
        }
        else if(id == R.id.nav_rank)
        {
            Intent intent = new Intent(Home.this, RankList.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_random)
        {
            Intent intent = new Intent(Home.this, Random.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(Home.this,Cart.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_history)
        {
            Intent intent = new Intent(Home.this, HistoryList.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_log_out)
        {
            Intent intent = new Intent(Home.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            firebaseAuth.signOut();
            stopService(service);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
