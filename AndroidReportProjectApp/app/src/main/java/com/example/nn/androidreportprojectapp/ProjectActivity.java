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

import com.example.nn.androidreportprojectapp.helper.IPaddress;
import com.example.nn.androidreportprojectapp.helper.WebConnect;
import com.example.nn.androidreportprojectapp.models.Project;

import java.util.List;

/**
 * Created by NN on 6.1.2016.
 */
public class ProjectActivity  extends AppCompatActivity{

    public static List<Project> projects = WebConnect.projects;
    private ProjectView projectView;
    private ProjectAdapter projectAdapter;
    private RecyclerView recyclerView;
    public static String url = IPaddress.getIpAddress();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_feed);

        recyclerView = (RecyclerView) findViewById(R.id.project_recycler_view);
        projectAdapter = new ProjectAdapter(projects);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static Intent newIntent(Context packageContext, List<Project> projectList ){

        Intent i = new Intent(packageContext, ProjectActivity.class);
        projects = projectList;
        return i;

    }

    private class ProjectView extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Project mProject;
        private TextView mProjectName;
        private Button addReport;
        private int mPosition;

        public ProjectView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mProjectName = (TextView) itemView.findViewById(R.id.project_name);
            addReport = (Button) itemView.findViewById(R.id.report_button);
        }

        @Override
        public void onClick(View v) {
            WebConnect.getReports(ProjectActivity.this, mProject);

        }

        public void bindProject(Project project, int position){

            mProject = project;
            mProjectName.setText(mProject.getName());
            mPosition = position;
            addReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = NewReportActivity.newIntent(getApplicationContext(), mProject);
                    startActivity(i);
                }
            });
        }
    }

    private class ProjectAdapter  extends RecyclerView.Adapter<ProjectView>{

        private List<Project> projectList;

        public ProjectAdapter(List<Project> projectList) {
            this.projectList = projectList;
        }

        @Override
        public ProjectView onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(ProjectActivity.this);

            View view = layoutInflater.inflate(R.layout.project_fragment, parent, false);
            return new ProjectView(view);
        }

        @Override
        public void onBindViewHolder(ProjectView holder, int position) {
            final Project project = projectList.get(position);
            holder.bindProject(project, position);

        }

        @Override
        public int getItemCount() {
            return projectList.size();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WebConnect.projects.clear();
        WebConnect.projectName.clear();
        WebConnect.projectWebId.clear();
    }
}
