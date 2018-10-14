package com.example.uzzal.sqliteprojectgithub_01;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    MyDatabaseHelper myDatabaseHelper;
    private EditText etName, etAge, etGender,etId;
    private Button btnAddData,btnDisplayData,btnUpdateData,btnDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        etName = (EditText) findViewById(R.id.editTextName_id);
        etAge = (EditText) findViewById(R.id.editTextAge_id);
        etGender = (EditText) findViewById(R.id.editTextGender_id);
        etId = (EditText) findViewById(R.id.editTextId_id);


        btnAddData = (Button) findViewById(R.id.buttonAddData_id);
        btnDisplayData = (Button) findViewById(R.id.buttonDisplayData_id);
        btnUpdateData = (Button) findViewById(R.id.buttonUpdateData_id);
        btnDeleteData = (Button) findViewById(R.id.buttonDeleteData_id);


        btnAddData.setOnClickListener(this);
        btnDisplayData.setOnClickListener(this);
        btnUpdateData.setOnClickListener(this);
        btnDeleteData.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

             //** now data is get in String
        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        String gender = etGender.getText().toString();
        String id = etId.getText().toString();

        if(v.getId()==R.id.buttonAddData_id){

           long rowId =  myDatabaseHelper.insertData(name,age,gender);
           if(rowId==-1){

               Toast.makeText(getApplicationContext(),"Unsuccessfully",Toast.LENGTH_SHORT).show();


           }else {


               Toast.makeText(getApplicationContext(), "Row "+rowId+" is Successfully Inserted", Toast.LENGTH_SHORT).show();

           }
        }

        if(v.getId()==R.id.buttonDisplayData_id){

           Cursor cursor =  myDatabaseHelper.displayAllData();

           if(cursor.getCount()==0){

                //** there is no data so we wil display message

               showData("Error.!!","No data found");

               return;
           }

           StringBuffer stringBuffer = new StringBuffer();
           while (cursor.moveToNext())
           {
               stringBuffer.append("ID: "+cursor.getString(0)+"\n");
               stringBuffer.append("Name: "+cursor.getString(1)+"\n");
               stringBuffer.append("Age: "+cursor.getString(2)+"\n");
               stringBuffer.append("Gender: "+cursor.getString(3)+"\n\n\n");
           }
           showData("ResultSet",stringBuffer.toString());

        }
          else if(v.getId()==R.id.buttonUpdateData_id){

        Boolean isUpdated =  myDatabaseHelper.updateData(id,name,age,gender);

        if(isUpdated==true)
        {

                Toast.makeText(getApplicationContext(),"Successfully Data is, Updated.!!",Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(getApplicationContext(),"Unsuccessfully, Update",Toast.LENGTH_SHORT).show();
        }

        }

        if(v.getId()==R.id.buttonDeleteData_id)
        {
         int value =  myDatabaseHelper.deleteData(id);

         if(value>0){

           Toast.makeText(getApplicationContext()," Successfully, Deleted.!!",Toast.LENGTH_SHORT).show();
         }else {

           Toast.makeText(getApplicationContext(),"Unsuccessfully Deleted",Toast.LENGTH_SHORT).show();
         }

        }



    }

    public void showData(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }


}
