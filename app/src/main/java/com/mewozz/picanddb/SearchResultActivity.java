package com.mewozz.picanddb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private ListView lvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        lvResult = (ListView) findViewById(R.id.lvResult);

        String name=getIntent().getStringExtra("name");
        List<People> results=PeopleDao.getPeopleByName(name);
        if(results.size()>0){
            ResultListAdapter adapter=new ResultListAdapter(this,results);
            lvResult.setAdapter(adapter);
        }
        else {
            ArrayAdapter<String> placeHolderAdapter=new ArrayAdapter<String>(this,R.layout.adapter_place_holder,new String[]{"No result found"});
            lvResult.setAdapter(placeHolderAdapter);
        }
    }
}
