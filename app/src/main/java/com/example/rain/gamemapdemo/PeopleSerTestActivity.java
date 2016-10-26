package com.example.rain.gamemapdemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import java.util.ArrayList;

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
    private ArrayList<People> peoples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serializable);

        name = null;
        age = 0;

        people = new People(name, age);
        peoples = new ArrayList<People>();


        serTextview = (TextView) findViewById(R.id.serializableText);
        serTextview.setMovementMethod(ScrollingMovementMethod.getInstance());

        nameEdit = (EditText) findViewById(R.id.nameEditText);
        ageEdit = (EditText) findViewById(R.id.ageEditText);
        add = (Button) findViewById(R.id.serAddbtn);

        sharedPreferences = getSharedPreferences("Testdata", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        peopleNum = sharedPreferences.getInt("peopleNumber", 0);
        String s = sharedPreferences.getString("peopleData", null);

        try {
            try {
                ArrayList<People> ps = deSerialization(s);
                peoples = ps;
            }
            catch (ClassNotFoundException ex) {

            }

        }

        catch (IOException e) {

        }

        print();


       /* if(peopleNum != 0) {
            try {
                peoples = deSerialization(s);
            }
            catch (IOException e) {
                Toast.makeText(PeopleSerTestActivity.this, "IoException: " + e.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (ClassNotFoundException ex) {
                Toast.makeText(PeopleSerTestActivity.this, "ClassNotFoundException: " + ex.toString(), Toast.LENGTH_SHORT).show();
            }

            print();
        }*/


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                age = Integer.parseInt(ageEdit.getText().toString());

                people.setName(name);
                people.setAge(age);
                peoples.add(people);

               /* try {
                    editor.putString("peopleData", serialize());
                    peopleNum ++;
                    editor.putInt("peopleNumber", peopleNum);
                    editor.commit();

                }
                catch (IOException e) {
                    Toast.makeText(PeopleSerTestActivity.this, "IoException: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
                */

                print();
            }
        });

    }

    private void print() {
        serTextview.setText("");
        /*for(People p: peoples) {
            serTextview.append("name: " + p.getName());
            serTextview.append("age: " + p.getAge() + "\n");
        }*/
        try{
            String ser = serialize();
            //serTextview.setText("" + ser + "\n");
            editor.putString("peopleData", ser);
            peopleNum ++;
            editor.putInt("peopleNumber", peopleNum);
            editor.commit();

            for(People p: peoples) {
                serTextview.append("name: " + p.getName());
                serTextview.append("age: " + p.getAge() + "\n");
            }


        }
        catch (IOException e) {

        }



    }

    //序列化对象

    private String serialize() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(peoples);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    //反序列化对象

    private ArrayList<People> deSerialization(String str) throws IOException,
            ClassNotFoundException {

        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);

        ArrayList<People> p = (ArrayList<People>) objectInputStream.readObject();

        objectInputStream.close();
        byteArrayInputStream.close();
        return p;
    }

    /*
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
    */
}
