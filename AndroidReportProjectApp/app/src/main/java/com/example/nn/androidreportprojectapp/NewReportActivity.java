package com.example.nn.androidreportprojectapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nn.androidreportprojectapp.helper.WebConnect;
import com.example.nn.androidreportprojectapp.models.Project;

/**
 * Created by NN on 7.1.2016.
 */
public class NewReportActivity extends AppCompatActivity {

    public static Project mproject;
    private EditText context;
    private EditText startDate;
    private EditText endDate;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_report);

        context = (EditText) findViewById(R.id.description);
        startDate = (EditText) findViewById(R.id.start_Date);
        endDate = (EditText) findViewById(R.id.end_Date);
        addButton = (Button) findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = context.getText().toString();
                String start = startDate.getText().toString();
                String end = endDate.getText().toString();

                WebConnect.newReport(description, start, end, NewReportActivity.this);
            }
        });

    }

    public static Intent newIntent(Context context, Project project) {
        Intent i = new Intent(context, NewReportActivity.class);
        mproject = project;
        return i;
    }

}
