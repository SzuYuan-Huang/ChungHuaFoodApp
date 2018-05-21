package project.newstylefood.ViewHolder;

import android.media.Image;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.R;

/**
 * Created by PC on 2017/11/4.
 */

public class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtStoreName;
    public ImageView imageViewStore;

    private ItemClickListener itemClickListener;

    public StoreViewHolder(View itemView)
    {
        super(itemView);

        txtStoreName = (TextView) itemView.findViewById(R.id.textViewTitle);
        imageViewStore = (ImageView) itemView.findViewById(R.id.imageViewTitle);

        itemView.setOnClickListener(this);
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
}
