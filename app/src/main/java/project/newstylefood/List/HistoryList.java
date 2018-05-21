package project.newstylefood.List;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import project.newstylefood.Detail.FoodDetail;
import project.newstylefood.HistoryOrder;
import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.Model.Food;
import project.newstylefood.Model.HistoryTime;
import project.newstylefood.Model.Order;
import project.newstylefood.R;
import project.newstylefood.ViewHolder.FoodViewHolder;
import project.newstylefood.ViewHolder.HistoryTimeViewHolder;

public class HistoryList extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    private DatabaseReference history;
    List<HistoryTime> historyTimeList;
    ListView listViewHistoryTime;
    int count = 0;
    int temp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        historyTimeList = new ArrayList<>();
        listViewHistoryTime = (ListView) findViewById(R.id.listViewHistory);
        firebaseAuth = FirebaseAuth.getInstance();
        history = FirebaseDatabase.getInstance().getReference("history").child(firebaseAuth.getCurrentUser().getUid());

        listViewHistoryTime.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(HistoryList.this,HistoryOrder.class);
                intent.putExtra("HistoryTime",historyTimeList.get(position).getTime());
                startActivity(intent);
            }
        });

        listViewHistoryTime.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(HistoryList.this);
                    alertDialog.setTitle("警告");
                    alertDialog.setMessage("請問您是否刪除這筆歷史資料？");
                    alertDialog.setNegativeButton("是", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            history.child(historyTimeList.get(position).getTime()).child("food").addValueEventListener(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot)
                                {
                                    count = (int) dataSnapshot.getChildrenCount();
                                    temp = 0;
                                    for(DataSnapshot order : dataSnapshot.getChildren())
                                    {
                                        Order myOrder = order.getValue(Order.class);
                                        if(myOrder.getNotification().equals("已通知"))
                                        {
                                            temp++;
                                        }
                                    }

                                    if(temp == count)
                                    {
                                        history.child(historyTimeList.get(position).getTime()).removeValue();
                                    }
                                    else
                                    {
                                        Toast.makeText(HistoryList.this,"尚有店家未完成您的訂單，暫時無法刪除！",Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError)
                                {

                                }
                            });
                        }
                    });
                    alertDialog.setPositiveButton("否", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    });
                    alertDialog.show();
                return true;
            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        history.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                historyTimeList.clear();
                for(DataSnapshot historyTime : dataSnapshot.getChildren())
                {
                    HistoryTime myHistoryTime = historyTime.getValue(HistoryTime.class);
                    historyTimeList.add(myHistoryTime);
                }
                HistoryListAdapter adapter = new HistoryListAdapter(HistoryList.this,historyTimeList);
                listViewHistoryTime.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
