package adapter;

import java.util.ArrayList;

import com.example.mealdelivery.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<String> {
	Context context;
    int layoutResourceId;   
    ArrayList<String> data=new ArrayList<String>();
    public MenuAdapter(Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RegardingTypeHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new RegardingTypeHolder();
            holder.textName=(TextView)row.findViewById(R.id.link);
            row.setTag(holder);
        }
        else
        {
            holder = (RegardingTypeHolder)row.getTag();
        }
       
        holder.textName.setText(data.get(position));
        return row;
       
    }
   
    static class RegardingTypeHolder
    {
        
        TextView textName;
    }
}


