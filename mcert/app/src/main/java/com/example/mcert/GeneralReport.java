package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GeneralReport extends AppCompatActivity {
    public static final int TASK_REQ = 1;
    public static final int TASK_RES = 1;

    public static final int GOOD_RES = 2;

    public static final int TOG_RES = 2;

    public static final int FLO_RES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_report);
    }
    public void onSubmit1(View v){
        Intent gen_ini = new Intent();
        setResult(TabsActivity.RES,gen_ini);
        finish();
    }
    public void onSubmit(View v) {
        Intent ini = new Intent();
        setResult(TabsActivity.GOOD_RES, ini);
        finish();
    }

    public void getLocationAction(View v) {
        Intent ini = new Intent(this, MapsActivity.class);
        startActivity(ini);
    }

    public void getDisasterType(View v) {
        Intent disaster_ini = new Intent(this, ImagesActivity.class);
        startActivityForResult(disaster_ini, TASK_REQ);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent disasterInt) {
        if (requestCode == TASK_REQ) {
            if (resultCode == TASK_RES) {
                String str = disasterInt.getStringExtra("name");
                EditText edt = findViewById(R.id.editText5);
                edt.setText(str);
            }
            else if (resultCode == GOOD_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.editText5);
                edt.setText(str);
            }
            else if(resultCode == TOG_RES){
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.editText5);
                edt.setText(str);
            }
            else if(resultCode == FLO_RES){
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.editText5);
                edt.setText(str);
            }
        }
    }
}

