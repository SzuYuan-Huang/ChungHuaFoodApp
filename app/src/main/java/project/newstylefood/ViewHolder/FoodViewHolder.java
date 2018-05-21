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

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtFoodName;
    public ImageView imageViewFood;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView)
    {
        super(itemView);
        txtFoodName = (TextView) itemView.findViewById(R.id.textViewFood);
        imageViewFood = (ImageView) itemView.findViewById(R.id.imageViewFood);

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
