package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Dairy_Breakfast_Edt_Act extends AppCompatActivity {
    ImageView im_back;
    EditText ed_name,ed_kg,ed_price;
    RequestQueue requestQueue;
    Button bt_save;
    void Save_Data(String name,String kg,String price){
        requestQueue = Volley.newRequestQueue(Dairy_Breakfast_Edt_Act.this);
        String Url ="https://shakibmohd.000webhostapp.com/api_shop/breakfast_save.php";
        JSONObject object = new JSONObject();
        try {
            object.put("b_name",name);
            object.put("b_kg",kg);
            object.put("b_price",price);
        }catch (Exception e){}
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(Dairy_Breakfast_Edt_Act.this, response.getString("Status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dairy_Breakfast_Edt_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_breakfast_edt);
        im_back = findViewById(R.id.im_back);
        ed_name = findViewById(R.id.edt_dairy_);
        ed_kg = findViewById(R.id.edt_dairy_gram);
        ed_price = findViewById(R.id.edt_price);
        bt_save = findViewById(R.id.bt_save);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                String kg = ed_kg.getText().toString();
                String price = ed_price.getText().toString();

                Save_Data(name,kg,price);
            }
        });


        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}