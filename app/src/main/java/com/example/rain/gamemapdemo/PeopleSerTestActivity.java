package com.example.rain.gamemapdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rain on 2016/10/24.
 */
public class PeopleSerTestActivity extends Activity {

    private TextView serTextview;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private People people;
    private int peopleNum, age;
    private String name;
    private Button add;
    private EditText nameEdit, ageEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serializable);

        name = null;
        age = 0;

        people = new People(name, age);

        serTextview = (TextView) findViewById(R.id.serializableText);
        nameEdit = (EditText) findViewById(R.id.nameEditText);
        ageEdit = (EditText) findViewById(R.id.ageEditText);
        add = (Button) findViewById(R.id.serAddbtn);

        sharedPreferences = getSharedPreferences("Testdata", MODE_PRIVATE);
        //editor = sharedPreferences.edit();

        peopleNum = sharedPreferences.getInt("peopleNumber", 0);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                age = Integer.parseInt(ageEdit.getText().toString());

                people.setName(name);
                people.setAge(age);

                print();
            }
        });

    }

    private void print() {
        serTextview.setText("");
        serTextview.append("name: " + people.getName());
        serTextview.append("age: " + people.getAge());
    }



}
