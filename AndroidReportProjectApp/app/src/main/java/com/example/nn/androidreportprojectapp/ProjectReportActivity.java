package com.example.nn.androidreportprojectapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nn.androidreportprojectapp.models.Project;
import com.example.nn.androidreportprojectapp.models.Report;

import java.util.List;

/**
 * Created by NN on 6.1.2016.
 */
public class ProjectReportActivity extends AppCompatActivity{

    private static List<Report> reports;
    private static Project currentProject;
    private TextView mProjectName;
    private ReportView reportView;
    private ReportAdapter reportAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);

        mProjectName = (TextView) findViewById(R.id.project_name);
        mProjectName.setText(currentProject.getName());

        recyclerView = (RecyclerView) findViewById(R.id.report_recycler);
        reportAdapter = new ReportAdapter(reports);
        recyclerView.setAdapter(reportAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public static Intent newIntent(Context packageContext, Project project, List<Report> reportList){
        Intent i = new Intent(packageContext, ProjectReportActivity.class);
        currentProject = project;
        reports = reportList;
        return i;
    }

    private class ReportView  extends RecyclerView.ViewHolder{

        private Report mReport;
        private TextView mDescription;
        private TextView mStart;
        private TextView mEnd;
        private int mPosition;


        public ReportView(View itemView) {
            super(itemView);

            mDescription = (TextView) itemView.findViewById(R.id.report_fragment_report_description);
            mStart = (TextView) itemView.findViewById(R.id.report_fragment_report_start);
            mEnd = (TextView) itemView.findViewById(R.id.report_fragment_report_end);

        }

        public void bindReport(Report report, int position){
            mReport = report;
            mDescription.setText(report.getDescription());
            mStart.setText(report.getStartDate());
            mEnd.setText(report.getEndDate());
            mPosition = position;

        }
    }

    private class ReportAdapter extends RecyclerView.Adapter<ReportView> {

        private List<Report> reportList;

        public ReportAdapter(List<Report> reportList) {
            this.reportList = reportList;
        }

        @Override
        public ReportView onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(ProjectReportActivity.this);

            View view = layoutInflater.inflate(R.layout.report_fragment, parent, false);
            return new ReportView(view);
        }

        @Override
        public void onBindViewHolder(ReportView holder, int position) {

            final Report report = reports.get(position);
            holder.bindReport(report, position);

        }

        @Override
        public int getItemCount() {
            return reports.size();
        }
    }
}
