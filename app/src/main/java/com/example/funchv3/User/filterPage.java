package com.example.funchv3.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.funchv3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class filterPage extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String,List<String>> Filter;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Button discard,setFilter,backButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_page);

        backButton4 = findViewById(R.id.backButton4);
        backButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(filterPage.this, MainActivity.class));
            }
        });
        createGroupList();
        createCollection();
        discard = findViewById(R.id.discard);
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discardItems();
            }
        });
        expandableListView = findViewById(R.id.elvFilter);
        expandableListAdapter = new MyExpandableListAdapter(this,groupList,Filter);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i!= lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                Toast.makeText(filterPage.this, "selected " + selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void discardItems() {

    }

    private void createCollection() {
        String[] cuisines = {"Breakfast","Borek","Kebap","Dessert","Burger","Coffee","Seafood","Toast&Sandwich"};
        String[] ratings ={"1","2","3","4","5"};
        Filter = new HashMap<String,List<String>>();
        for(String group: groupList){
            if (group.equals("Cuisine")) {
                loadChild(cuisines);
            }else if(group.equals("Rating")){
                loadChild(ratings);
            }
            Filter.put(group,childList);
        }
    }

    private void loadChild(String[] ratings) {
        childList = new ArrayList<>();
        for(String rating: ratings){
            childList.add(rating);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Cuisine");
        groupList.add("Rating");

    }
}