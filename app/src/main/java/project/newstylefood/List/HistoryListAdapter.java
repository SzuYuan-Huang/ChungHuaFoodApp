package project.newstylefood.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import project.newstylefood.Model.HistoryTime;
import project.newstylefood.Model.Order;
import project.newstylefood.R;

/**
 * Created by SzuYuan Huang on 2017/12/8.
 */

public class HistoryListAdapter extends ArrayAdapter<HistoryTime>
{
    private Activity context;
    private List<HistoryTime> historyList;

    public HistoryListAdapter(Activity context,List<HistoryTime> historyList)
    {
        super(context, R.layout.hisory_time_list_layout,historyList);
        this.context =context;
        this.historyList = historyList;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.hisory_time_list_layout,null,true);

        TextView txt_history_time = (TextView) listViewItem.findViewById(R.id.history_time);

        HistoryTime historyTime = historyList.get(position);

        txt_history_time.setText(historyTime.getTime());

        return listViewItem;
    }
}
