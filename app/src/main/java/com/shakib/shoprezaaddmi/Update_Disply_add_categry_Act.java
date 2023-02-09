package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Update_Disply_add_categry_Act extends AppCompatActivity {

    RequestQueue requestQueue;
    Button bt_update;
    EditText ed_name;
    TextView te_name;
    String cat_id ="";
    void Update_name(String cat_id,String nam){
        requestQueue = Volley.newRequestQueue(Update_Disply_add_categry_Act.this);
        JSONObject object = new JSONObject();
        try {
            object.put("cat_id",cat_id);
            object.put("name",nam);
        }catch (Exception e){
        }

        String url = "https://shakibmohd.000webhostapp.com/api_shop/reza_update_cat.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(Update_Disply_add_categry_Act.this, response.getString("Status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(Update_Disply_add_categry_Act.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_Disply_add_categry_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_disply_add_categry);
        ed_name = findViewById(R.id.ed_update_ca);
        bt_update = findViewById(R.id.bt_update);
        te_name = findViewById(R.id.te_name);

        Intent intent= getIntent();
        String name = intent.getStringExtra("name");
        cat_id = intent.getStringExtra("cat_id");

        te_name.setText(name);

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                Update_name(cat_id,name);
                Intent intent = new Intent(Update_Disply_add_categry_Act.this, DisPlay_Add_Categry_Act.class);
                startActivity(intent);
                finish();
            }
        });
    }
}