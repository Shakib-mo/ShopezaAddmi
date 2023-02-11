package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Edt_add_categry_Act extends AppCompatActivity {
    RequestQueue requestQueue;
    Button bt_save;
    EditText ed_name;

    void Save_Data(String name){
        requestQueue = Volley.newRequestQueue(Edt_add_categry_Act.this);
        JSONObject object = new JSONObject();
        try {
            object.put("cat_name",name);
        }catch (Exception e){
        }
            String url = "https://shakib0000087655444.000webhostapp.com/shop_reza/category_save.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(Edt_add_categry_Act.this,response.getString("status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edt_add_categry_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_add_categry);
        bt_save = findViewById(R.id.pro_save);
        ed_name=findViewById(R.id.ed_pro_name);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                Save_Data(name);
                Intent intent = new Intent(Edt_add_categry_Act.this, DisPlay_Add_Categry_Act.class);
                startActivity(intent);
            }
        });
    }
}