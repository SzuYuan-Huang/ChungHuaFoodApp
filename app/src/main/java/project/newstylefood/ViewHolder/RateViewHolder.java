package project.newstylefood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.newstylefood.Interface.ItemClickListener;
import project.newstylefood.R;

/**
 * Created by PC on 2017/12/5.
 */

public class RateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtUserEmail;
    public TextView txtUserComment;
    public ImageView imageViewUserStar;

    private ItemClickListener itemClickListener;

    public RateViewHolder(View itemView)
    {
        super(itemView);
        txtUserEmail = (TextView) itemView.findViewById(R.id.user_email);
        txtUserComment = (TextView) itemView.findViewById(R.id.user_comment);
        imageViewUserStar = (ImageView) itemView.findViewById(R.id.user_star);

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
