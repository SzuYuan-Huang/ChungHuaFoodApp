package project.newstylefood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.R;

/**
 * Created by PC on 2017/11/15.
 */

public class HistoryTimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{
    public TextView txtHistoryTime;

    private ItemClickListener itemClickListener;

    public HistoryTimeViewHolder(View itemView)
    {
        super(itemView);
        txtHistoryTime = (TextView) itemView.findViewById(R.id.history_time);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v)
    {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}
