package project.newstylefood.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import project.newstylefood.Model.Food;
import project.newstylefood.Model.Store;
import project.newstylefood.R;

/**
 * Created by PC on 2017/11/29.
 */

public class RankListAdapter extends ArrayAdapter<Food>
{
    private Activity context;
    private List<Food> rankList;

    public RankListAdapter(Activity context,List<Food> rankList)
    {
        super(context, R.layout.rank_list_layout,rankList);
        this.context = context;
        this.rankList = rankList;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.rank_list_layout,null,true);

        TextView txt_rank_name = (TextView) listViewItem.findViewById(R.id.textViewRank);
        ImageView image_rank_image = (ImageView) listViewItem.findViewById(R.id.imageViewRank);
        ImageView image_rank_number = (ImageView) listViewItem.findViewById(R.id.imageViewRankNumber);

        Food food = rankList.get(position);

        txt_rank_name.setText(food.getName());
        Picasso.with(getContext()).load(food.getImage()).fit().centerInside()
                .memoryPolicy(MemoryPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE).into(image_rank_image);

        if(position==0)
        {
            image_rank_number.setImageResource(R.drawable.rank_1);
        }
        else if(position==1)
        {
            image_rank_number.setImageResource(R.drawable.rank_2);
        }
        else if(position==2)
        {
            image_rank_number.setImageResource(R.drawable.rank_3);
        }

        return listViewItem;
    }
}
