package project.newstylefood.BackgroundService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Measure;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import project.newstylefood.Home;
import project.newstylefood.Model.HistoryTime;
import project.newstylefood.Model.Order;
import project.newstylefood.R;

/**
 * Created by PC on 2017/11/22.
 */

public class Message extends Service
{
    DatabaseReference history,store;
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        firebaseAuth = FirebaseAuth.getInstance();
        history = FirebaseDatabase.getInstance().getReference("history").child(firebaseAuth.getCurrentUser().getUid());
        store = FirebaseDatabase.getInstance().getReference("titles");

        history.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot date : dataSnapshot.getChildren())
                {
                    final HistoryTime historyTime = date.getValue(HistoryTime.class);

                    history.child(historyTime.getTime()).child("food").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            for(DataSnapshot order : dataSnapshot.getChildren())
                            {
                                final Order myOrder = order.getValue(Order.class);

                                store.child(myOrder.getStoreId()).child("titleName").addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot)
                                    {
                                        if(myOrder.getNotification().equals("已通知"))
                                        {

                                        }
                                        else
                                        {
                                            if(myOrder.getStatus().equals("已完成"))
                                            {
                                                Notification.Builder builder;

                                                builder = new Notification.Builder(getBaseContext())
                                                        .setContentTitle("提醒您")
                                                        .setContentText(dataSnapshot.getValue(String.class) +"訂單已完成，請至店裡付款領取")
                                                        .setDefaults(Notification.DEFAULT_SOUND)
                                                        .setAutoCancel(true)
                                                        .setSmallIcon(R.drawable.icon);

                                                Notification notification = builder.build();

                                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                notificationManager.notify(1, notification);
                                                history.child(historyTime.getTime()).child("food").child(myOrder.getSortId()).child("notification").setValue("已通知");
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
