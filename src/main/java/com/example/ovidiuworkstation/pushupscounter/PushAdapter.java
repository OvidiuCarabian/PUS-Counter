package com.example.ovidiuworkstation.pushupscounter;

/**
 * Created by Ovidiu Workstation on 1/6/2017.
 */

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.ovidiuworkstation.pushupscounter.database.PushLink;
import static com.example.ovidiuworkstation.pushupscounter.database.PushLink.PushEntry.*;

public class PushAdapter extends CursorAdapter {
    public PushAdapter(Context context, Cursor c){
        super(context, c, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView dateText = (TextView) view.findViewById(R.id.date_text);
        TextView countText = (TextView) view.findViewById(R.id.count_text);
        TextView caloriesText = (TextView) view.findViewById(R.id.calories_text);
        TextView durationText = (TextView) view.findViewById(R.id.duration_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.delete_icon);
        imageView.setTag(cursor.getInt(cursor.getColumnIndex(PushLink.PushEntry.COLUMN_ID)));
        long time = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
        int count = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT));
        int calories = cursor.getInt(cursor.getColumnIndex(COLUMN_CALORIES))/4200;
        int duration = cursor.getInt(cursor.getColumnIndex(COLUMN_DURATION));

        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat durationFormat = new SimpleDateFormat("mm:ss");
        String displayDuration = durationFormat.format(duration);
        String timeString = simpleDateFormat.format(date);
        dateText.setText(timeString);
        countText.setText(""+count);
        caloriesText.setText(""+calories);
        durationText.setText(displayDuration);

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int id = (Integer) v.getTag();
                Uri uri = null;
                if(!MainActivity.insue){
                    uri = ContentUris.withAppendedId(PushLink.PushEntry.CONTENT_URI,id);
                    v.getContext().getContentResolver().delete(uri,null, null);
                }

            }
        });

    }
}