package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class IncidentReportActivity extends AppCompatActivity {
    public static final int TASK_REQ = 1;
    public static final int TASK_RES = 1;

    public static final int GOOD_RES = 2;

    public static final int TOG_RES = 2;

    public static final int FLO_RES = 2;

    String[] ImpactLevel = {"Low", "Medium", "High"};

    String[] ImpactLevel1 = {"Low", "Medium", "High"};
    static String token;
    EditText title;
    EditText datetime;
    TextView location;
    EditText description;
    EditText type;
    MaterialBetterSpinner impact;
    MaterialBetterSpinner impact1;
    EditText redCount;
    EditText greenCount;
    EditText yellowCount;
    EditText blackCount;
    EditText Hazmat;
    EditText Notes;
    String dateStr,timeStr;
    TextView dateTV;
    TextView timeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_report);

        title = findViewById(R.id.titleofreport);
        datetime = findViewById(R.id.dateandtime);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        type = findViewById(R.id.Type2);
        impact = findViewById(R.id.spinner1);
        impact1 = findViewById(R.id.spinner);
        redCount = findViewById(R.id.Count1);
        greenCount = findViewById(R.id.Count2);
        yellowCount = findViewById(R.id.Count3);
        blackCount = findViewById(R.id.Count4);
        Hazmat = findViewById(R.id.Hazmat);
        Notes = findViewById(R.id.Notes);
        dateTV=findViewById(R.id.dateTV);
        timeTV=findViewById(R.id.timeTV);
        //datetime.setText(dateStr+" "+timeStr);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ImpactLevel);
        MaterialBetterSpinner betterSpinner = findViewById(R.id.spinner1);
        betterSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ImpactLevel1);
        MaterialBetterSpinner betterSpinner1 = findViewById(R.id.spinner);
        betterSpinner1.setAdapter(arrayAdapter1);
    }

    public void onSubmit(View v) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "https://eoc-dm.herokuapp.com/api/report/createReport";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title", this.title.getText().toString());
            // Toast.makeText(getApplicationContext(),this.pwd.getText().toString()+"",Toast.LENGTH_SHORT).show();
           jsonBody.put("timeDate",datetime.getText().toString());
            //jsonBody.put("timeDate", dateTV.getText().toString()+" "+timeTV.getText().toString());
            jsonBody.put("location", this.location.getText().toString());
            jsonBody.put("description", this.description.getText().toString());
            jsonBody.put("typeOfIncident", this.type.getText().toString());
            jsonBody.put("impactLevel", String.valueOf(impact.getText().toString()));
            jsonBody.put("structuralDamageImpact", String.valueOf(impact1.getText().toString()));
            jsonBody.put("red", this.redCount.getText().toString());
            jsonBody.put("green", this.greenCount.getText().toString());
            jsonBody.put("yellow", this.yellowCount.getText().toString());
            jsonBody.put("black", this.blackCount.getText().toString());
            jsonBody.put("hazmatType", this.Hazmat.getText().toString());
            jsonBody.put("incidentId", IncidentListAdapter.incidentId);
            jsonBody.put("notes", this.Notes.getText().toString());

            final String requestBody = jsonBody.toString();
            final Intent tip_intent = new Intent(this, IncidentListResponse.class);
            //should be removed after fixing login issue
            //     startActivity(tip_intent);


            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override

                public void onResponse(JSONObject response) {
                    Log.i("VOLLEY", response.toString());
                    // json = new JSONObject(jsonResult);
                    try {
                        token = response.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ;
                    //  Intent ini = new Intent(this,TabsActivity.class)
                    startActivity(tip_intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());

                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";

                }

                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("Authorization", MainActivity.token);
                    Log.e("token", MainActivity.token);

                    return params;
                }

            };

            requestQueue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getLocationAction(View v) {
        Intent ini = new Intent(this, MapsActivity.class);
        startActivityForResult(ini, 11);
    }

    public void getDisasterType(View v) {
        Intent disaster_ini = new Intent(this, ImagesActivity.class);
        startActivityForResult(disaster_ini, TASK_REQ);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent disasterInt) {
        if (requestCode == TASK_REQ) {
            if (resultCode == TASK_RES) {
                String str = disasterInt.getStringExtra("name");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            } else if (resultCode == GOOD_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            } else if (resultCode == TOG_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            } else if (resultCode == FLO_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            }
        }
        if (requestCode == 11) {
            if (resultCode == 11) {
                String str = disasterInt.getStringExtra("LocationName");
                TextView incidentLocTV = findViewById(R.id.location);
                incidentLocTV.setText(str);
            }
        }
    }


    private int mYear, mMonth, mDay, mHour, mMinute;

    public void dateClick(View v) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateStr=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dateTV.setText((monthOfYear + 1)+"-"+dayOfMonth  + "-" + year);
datetime.setText((monthOfYear + 1)+"-"+dayOfMonth  + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timeClick(View v) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            TextView timeTV=findViewById(R.id.timeTV);
                            timeStr=hourOfDay + ":" + minute;
                            timeTV.setText(hourOfDay + ":" + minute);
                            datetime.setText(datetime.getText().toString()+ " "+hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

}


