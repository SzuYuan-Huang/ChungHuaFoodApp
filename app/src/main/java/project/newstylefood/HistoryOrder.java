package project.newstylefood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import project.newstylefood.List.CartList;
import project.newstylefood.List.HistoryOrderList;
import project.newstylefood.Model.HistoryTime;
import project.newstylefood.Model.Order;

public class HistoryOrder extends AppCompatActivity
{
    ListView listViewHistoryOrder;

    DatabaseReference history;
    FirebaseAuth firebaseAuth;
    List<Order> orderList;

    TextView txtTotalPrice;

    private String HistoryTime;

    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        Bundle extra = getIntent().getExtras();
        HistoryTime = extra.getString("HistoryTime");

        orderList = new ArrayList<>();
        listViewHistoryOrder = (ListView) findViewById(R.id.listViewOrder);

        firebaseAuth = FirebaseAuth.getInstance();

        history = FirebaseDatabase.getInstance().getReference("history").child(firebaseAuth.getCurrentUser().getUid()).child(HistoryTime).child("food");
        txtTotalPrice = (TextView) findViewById(R.id.total);
    }

    protected void onStart()
    {
        super.onStart();

        history.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                orderList.clear();
                total = 0;

                for(DataSnapshot userOrderSnapshot : dataSnapshot.getChildren())
                {
                    Order order = userOrderSnapshot.getValue(Order.class);
                    orderList.add(order);
                    total += order.getQuantity() * order.getPrice();
                }

                HistoryOrderList adapter = new HistoryOrderList(HistoryOrder.this,orderList);
                listViewHistoryOrder.setAdapter(adapter);
                txtTotalPrice.setText(""+total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

}
