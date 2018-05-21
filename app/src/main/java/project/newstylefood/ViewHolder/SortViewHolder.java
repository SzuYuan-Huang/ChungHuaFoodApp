package project.newstylefood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.R;

/**
 * Created by PC on 2017/11/5.
 */

public class SortViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtSortName;
    public ImageView imageViewSort;

    private ItemClickListener itemClickListener;

    public SortViewHolder(View itemView)
    {
        super(itemView);

        txtSortName = (TextView) itemView.findViewById(R.id.textViewSort);
        imageViewSort = (ImageView) itemView.findViewById(R.id.imageViewSort);

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
