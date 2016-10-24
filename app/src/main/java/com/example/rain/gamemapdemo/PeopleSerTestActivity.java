package com.example.rain.gamemapdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        editor = sharedPreferences.edit();

        peopleNum = sharedPreferences.getInt("peopleNumber", 0);
        String s = sharedPreferences.getString("peopleData", null);


        if(peopleNum != 0) {
            try {
                people = deSerialization(s);
            }
            catch (IOException e) {
                Toast.makeText(PeopleSerTestActivity.this, "IoException: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (ClassNotFoundException ex) {
                Toast.makeText(PeopleSerTestActivity.this, "ClassNotFoundException: " + ex.toString(), Toast.LENGTH_SHORT).show();
            }

            print();
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                age = Integer.parseInt(ageEdit.getText().toString());

                people.setName(name);
                people.setAge(age);

                try {
                    editor.putString("peopleData", serialize(people));
                    peopleNum ++;
                    editor.putInt("peopleNumber", peopleNum);
                    editor.commit();

                }
                catch (IOException e) {
                    Toast.makeText(PeopleSerTestActivity.this, "IoException: " + e.toString(), Toast.LENGTH_SHORT).show();
                }


                print();
            }
        });

    }

    private void print() {
        serTextview.setText("");
        serTextview.append("name: " + people.getName());
        serTextview.append("age: " + people.getAge());
    }

    //序列化对象

    private String serialize(People p) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(p);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    //反序列化对象

    private People deSerialization(String str) throws IOException,
            ClassNotFoundException {

        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        People p = (People) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return p;
    }


}
