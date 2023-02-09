package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import org.json.JSONObject;

public class Login_Act extends AppCompatActivity {

    Button bt_summit;
    EditText ed_mobile,ed_password;
    RequestQueue requestQueue;

    void Login_Data(String mo,String pa){
        requestQueue = Volley.newRequestQueue(Login_Act.this);
        String url = "https://shakibmohd.000webhostapp.com/api_shop/login.php";
        JSONObject object = new JSONObject();
        try {
            object.put("mobile",mo);
            object.put("password",pa);
        }catch (Exception e){

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.equals("Valid")){
                    Intent intent = new Intent(Login_Act.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_summit = findViewById(R.id.bt_summit);
        ed_mobile = findViewById(R.id.ed_number);
        ed_password = findViewById(R.id.ed_password);

        bt_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String mob = ed_mobile.getText().toString();
               String ps = ed_password.getText().toString();
              // Login_Data(mob,ps);
                Intent intent = new Intent(Login_Act.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}