package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class operation extends AppCompatActivity {
    EditText name, uni, pas1;
    Button insert, update, delete, view;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        name = findViewById(R.id.name);
        uni = findViewById(R.id.usnlog);
        pas1 = findViewById(R.id.passw);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String usnTXT = uni.getText().toString();
                String pasTXT = pas1.getText().toString();
                Boolean checkinsertdata = DB.insertData(nameTXT, usnTXT, pasTXT);
                if(usnTXT.equals("")) {
                    Toast.makeText(getApplicationContext(), "Do not Let USN Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(checkinsertdata==true)
                        Toast.makeText(getApplicationContext(), "New Entry Inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usnTXT = uni.getText().toString();
                String pasTXT = pas1.getText().toString();
                Boolean che = DB.updateuserdata(usnTXT, pasTXT);
                if(che)
                    Toast.makeText(getApplicationContext(), "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usnTXT = uni.getText().toString();
                Boolean checkudeletedata = DB.deletedata(usnTXT);
                if(usnTXT.equals("")) {
                    Toast.makeText(getApplicationContext(), "Don't Let USN Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (checkudeletedata)
                        Toast.makeText(getApplicationContext(), "Entry Deleted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(getApplicationContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("USN :"+res.getString(1)+"\n");
                    buffer.append("Password :"+res.getString(2)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(operation.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });





    }
}
