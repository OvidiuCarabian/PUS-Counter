package com.example.ovidiuworkstation.pushupscounter;

/**
 * Created by Ovidiu Workstation on 1/6/2017.
 */
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ovidiuworkstation.pushupscounter.database.PushLink;

public class ParametersActivity extends AppCompatActivity {
    private boolean isSI = true;
    private Button saveButton;
    private View siMassView;
    private View siHeightView;
    private int height = 0;
    private double weight;
    private EditText siMassEdit;
    private EditText siHeightEdit;
    private int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameter_activity);
        siMassView = (View) findViewById(R.id.weight_view_SI);
        siHeightView = (View) findViewById(R.id.height_view_SI);
        siMassEdit = (EditText) findViewById(R.id.mass_edit_text_SI);
        siHeightEdit = (EditText) findViewById(R.id.height_edit_cm);
        saveButton = (Button) findViewById(R.id.save_button);

        Cursor cursor = getContentResolver().query(PushLink.Parameter.CONTENT_URI, null, null, null, null);
        cursor.moveToPosition(0);
        id = cursor.getInt(cursor.getColumnIndex(PushLink.Parameter.COLUMN_ID));

        height = cursor.getInt(cursor.getColumnIndex(PushLink.Parameter.COLUMN_HEIGHT));
        weight = cursor.getDouble(cursor.getColumnIndex(PushLink.Parameter.COLUMN_WEIGHT));

        if (isSI)
       {
            siHeightEdit.setText(String.valueOf(height));
            siMassEdit.setText(String.valueOf(weight));
       }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean erOrcur = false;
                if (isSI) {
                    double ww = Double.parseDouble((siMassEdit.getText().toString()));
                    if ((ww > 20.0) && (ww < 150.0) && !TextUtils.isEmpty(siMassEdit.getText().toString()))
                        weight = Double.parseDouble(siMassEdit.getText().toString());
                    else erOrcur = true;

                    int hh = Integer.parseInt(siHeightEdit.getText().toString());
                    if ((hh > 100) && (hh < 220) && (!TextUtils.isEmpty(siHeightEdit.getText().toString())))
                        height = Integer.parseInt(siHeightEdit.getText().toString());
                    else erOrcur = true;
                    if (erOrcur) {
                        Toast.makeText(getApplicationContext(), "Invalid inputs", Toast.LENGTH_SHORT).show();
                    } else changeParameter(height, weight);
                }
            }
        });
    }

    private void changeParameter(int height, double weight) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PushLink.Parameter.COLUMN_HEIGHT, height);
        contentValues.put(PushLink.Parameter.COLUMN_WEIGHT, weight);
        int result = getContentResolver().update(ContentUris.withAppendedId(PushLink.Parameter.CONTENT_URI, id),contentValues, null, null);
        if(result!= 0) Toast.makeText(this, "Done changing", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}