package project.newstylefood;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.newstylefood.List.CartList;
import project.newstylefood.Model.Order;
import project.newstylefood.Model.Request;
import project.newstylefood.Model.User;

public class Cart extends AppCompatActivity
{
    ListView listViewOrder;

    DatabaseReference userOder,request,user,history;
    FirebaseAuth firebaseAuth;

    TextView txtTotalPrice;
    Button btnOrder;

    List<Order> orderList;

    private String[] temp;

    int count = 0;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        orderList = new ArrayList<>();
        listViewOrder = (ListView) findViewById(R.id.listViewOrder);

        firebaseAuth = FirebaseAuth.getInstance();

        userOder = FirebaseDatabase.getInstance().getReference("userOrder").child(firebaseAuth.getCurrentUser().getUid());
        request = FirebaseDatabase.getInstance().getReference("request");
        user = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid());
        history = FirebaseDatabase.getInstance().getReference("history").child(firebaseAuth.getCurrentUser().getUid());

        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnOrder = (Button) findViewById(R.id.btnOrder);

        btnOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(total==0)
                {
                    Toast.makeText(Cart.this,"您尚未點任何食物，無法點出！",Toast.LENGTH_LONG).show();
                }
                else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(Cart.this);
                    dialog.setTitle("警告");
                    dialog.setMessage("送出訂單後無法改變任何資料及刪除，是否繼續對此動作？");

                    dialog.setNeutralButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userQuest();
                        }
                    });

                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    dialog.show();
                }
            }
        });

        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                final String[] choose = {"修改數量","刪除資料","取消"};
                AlertDialog.Builder dialog_list = new AlertDialog.Builder(Cart.this);
                dialog_list.setTitle("修改/刪除");
                dialog_list.setItems(choose, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(which==0)
                        {
                            final Order order = orderList.get(position);
                            AlertDialog.Builder changeNumber = new AlertDialog.Builder(Cart.this);
                            changeNumber.setTitle("修改數量");

                            final ElegantNumberButton quantity = new ElegantNumberButton(Cart.this);
                            quantity.setRange(1,10);
                            quantity.setNumber(String.valueOf(order.getQuantity()));
                            quantity.setGravity(Gravity.CENTER);
                            changeNumber.setView(quantity);

                            changeNumber.setPositiveButton("確定", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    userOder.child(order.getSortId()).child("quantity").setValue(Integer.parseInt(quantity.getNumber()));
                                }
                            });
                            changeNumber.show();
                        }
                        else if(which==1)
                        {
                            Order order = orderList.get(position);
                            userOder.child(order.getSortId()).removeValue();
                        }
                    }
                });
                dialog_list.show();
            }
        });
    }

    protected void onStart()
    {
        super.onStart();

        userOder.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                orderList.clear();
                total = 0;
                count = 0;
                temp = new String[(int) dataSnapshot.getChildrenCount()];

                for(DataSnapshot userOrderSnapshot : dataSnapshot.getChildren())
                {
                    Order order = userOrderSnapshot.getValue(Order.class);
                    orderList.add(order);
                    total += order.getQuantity() * order.getPrice();
                    temp[count] = order.getStoreId();
                    count++;
                }

                CartList adapter = new CartList(Cart.this,orderList);
                listViewOrder.setAdapter(adapter);
                txtTotalPrice.setText(""+total);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


    }

    public void userQuest()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss:SSS");
        Date curDate = new Date(System.currentTimeMillis());
        final String time = formatter.format(curDate);
        count=0;

        userOder.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final int children = (int)dataSnapshot.getChildrenCount();
                history.child(time).child("time").setValue(time);
                for(DataSnapshot userOrderSnapshot : dataSnapshot.getChildren())
                {
                    final Order order = userOrderSnapshot.getValue(Order.class);
                    history.child(time).child("food").child(order.getSortId()).setValue(order);
                    history.child(time).child("food").child(order.getSortId()).child("status").setValue("未完成");
                    history.child(time).child("food").child(order.getSortId()).child("notification").setValue("未通知");
                    user.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            User user = dataSnapshot.getValue(User.class);
                            Request myRequest = new Request(user.getName(),user.getPhone(),time,firebaseAuth.getCurrentUser().getUid());
                            Order myOrder = new Order(order.getProductName(),order.getQuantity(),order.getPrice(),order.getStoreId(),order.getSortId());

                            if(count==0)
                            {
                                request.child(order.getStoreId()).child(time).setValue(myRequest);
                                request.child(order.getStoreId()).child(time).child("food").child(order.getSortId()).setValue(myOrder);
                            }
                            else
                            {
                                for(int i=0;i<count;i++)
                                {
                                    if(temp[count].equals(temp[i]))
                                    {
                                        request.child(order.getStoreId()).child(time).child("food").child(order.getSortId()).setValue(myOrder);
                                        break;
                                    }
                                    else
                                    {
                                        if(i==count-1)
                                        {
                                            request.child(order.getStoreId()).child(time).setValue(myRequest);
                                            request.child(order.getStoreId()).child(time).child("food").child(order.getSortId()).setValue(myOrder);
                                        }
                                    }
                                }
                            }
                            count++;
                            if(count==children)
                            {
                                userOder.removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });
                }

                Toast.makeText(Cart.this,"點出成功",Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }


}
