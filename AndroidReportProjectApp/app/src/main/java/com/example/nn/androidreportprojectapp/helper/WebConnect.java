package com.example.nn.androidreportprojectapp.helper;

import com.example.nn.androidreportprojectapp.ProjectActivity;
import com.example.nn.androidreportprojectapp.ProjectReportActivity;
import com.example.nn.androidreportprojectapp.models.Project;
import com.example.nn.androidreportprojectapp.models.Report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NN on 6.1.2016.
 */
public class WebConnect {

    //List for extracting projects from Json object
    public static List<String> projectName = new ArrayList<>();
    public static List<String> projectWebId = new ArrayList<>();
    public static List<Project> projects = new ArrayList<>();

    //Lists for extracting reports from Json object
    public static List<String> reportDescription = new ArrayList<>();
    public static List<String> reportStartDate = new ArrayList<>();
    public static List<String> reportEndTime = new ArrayList<>();
    public static List<Report> reports = new ArrayList<>();

    private static String url;
    private static Project currentProject;
    private static AppCompatActivity currentActivity;

    public static void getWebProject(AppCompatActivity activity) {
        JSONObject json = new JSONObject();
        try {
            json.put("project_id", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ServiceRequest.post(url + "api/project", json.toString(), getProjects(currentActivity));

    }

    private static Callback getProjects(AppCompatActivity activity) {

        currentActivity = activity;

        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseJSON = response.body().string();
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(responseJSON);
                    JSONObject object = jsonArray.getJSONObject(0);
                    JSONArray array = object.getJSONArray("names");
                    for (int i = 0; i < array.length(); i++) {
                        projectName.add(String.valueOf(array.get(i)));
                    }
                    JSONObject object1 = jsonArray.getJSONObject(1);
                    JSONArray array1 = object1.getJSONArray("project_id");
                    for (int i = 0; i < array1.length(); i++) {
                        projectWebId.add(String.valueOf(array1.get(i)));
                    }

                    for (int i = 0; i < projectName.size(); i++) {
                        projects.add(new Project(UUID.randomUUID(), projectName.get(i), projectWebId.get(i)));
                    }

                    Intent main = ProjectActivity.newIntent(currentActivity.getApplicationContext(), projects);
                    currentActivity.startActivity(main);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

        public static void getReports(AppCompatActivity appCompatActivity, Project project){

        JSONObject json = new JSONObject();

            try {
                json.put("project_id", project.getWebId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ServiceRequest.post(url + "api/reports", json.toString(), receiveReports(appCompatActivity, project));
        }

    public static Callback receiveReports (AppCompatActivity appCompatActivity, final Project project){

        currentActivity = appCompatActivity;
        currentProject = project;
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String responseJSON = response.body().string();
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(responseJSON);
                    JSONObject object = jsonArray.getJSONObject(0);
                    JSONArray array = object.getJSONArray("description");

                    for ( int i = 0; i < array.length(); i++){
                        reportDescription.add(String.valueOf(array.get(i)));
                    }

                    JSONObject object1 = jsonArray.getJSONObject(1);
                    JSONArray array1 = object1.getJSONArray("start");

                    for ( int i = 0; i < array.length(); i++){

                        reportStartDate.add(String.valueOf(array1.get(i)));
                    }

                    JSONObject object2 = jsonArray.getJSONObject(2);
                    JSONArray array2 = object2.getJSONArray("end");

                    for( int i = 0; i < array2.length(); i++){

                        reportEndTime.add(String.valueOf(array2.get(i)));
                    }

                    for ( int i = 0; i < reportDescription.size(); i++){

                        reports.add(new Report(UUID.randomUUID(), reportEndTime.get(i), reportStartDate.get(i), reportDescription.get(i)));
                    }

                    Intent main = ProjectReportActivity.newIntent(currentActivity.getApplicationContext(), currentProject, reports);
                    currentActivity.startActivity(main);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };


    }

    public static void newReport(String description, String start, String end, AppCompatActivity activity ){

        JSONObject json = new JSONObject();

        try {
            json.put("description", description);
            json.put("start", start);
            json.put("end", end);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ServiceRequest.post(url + "api/project/new", json.toString(), getProjects(activity));
    }
}
