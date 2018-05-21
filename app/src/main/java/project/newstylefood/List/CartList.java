package project.newstylefood.List;

import android.app.Activity;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import project.newstylefood.Model.Order;
import project.newstylefood.R;

/**
 * Created by PC on 2017/11/6.
 */

public class CartList extends ArrayAdapter<Order>
{
    private Activity context;
    private List<Order> orderList;

    public CartList(Activity context,List<Order> orderList)
    {
        super(context, R.layout.cart_list_layout,orderList);
        this.context =context;
        this.orderList = orderList;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.cart_list_layout,null,true);

        TextView txt_cart_name = (TextView) listViewItem.findViewById(R.id.cart_item_name);
        TextView txt_cart_price = (TextView) listViewItem.findViewById(R.id.cart_item_price);
        ImageView image_cart_count = (ImageView) listViewItem.findViewById(R.id.cart_item_count);

        Order order = orderList.get(position);

        TextDrawable drawable = TextDrawable.builder().buildRound(""+order.getQuantity(), Color.RED);

        txt_cart_name.setText(order.getProductName());
        txt_cart_price.setText(String.valueOf(order.getQuantity() * order.getPrice()));
        image_cart_count.setImageDrawable(drawable);

        return listViewItem;
    }
}
