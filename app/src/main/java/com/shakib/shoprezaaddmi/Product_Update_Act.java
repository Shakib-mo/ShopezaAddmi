package com.shakib.shoprezaaddmi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class Product_Update_Act extends AppCompatActivity {

    ImageView bt_back;
    TextView te_desc,te_name,te_price,te_offer_price;
    EditText ed_description,ed_product_name,ed_price,ed_offer_price;
    Button bt_update;
    RequestQueue requestQueue;
    void Update_product(String pr_id,String name,String price,String offer_price,String desc){
        requestQueue = Volley.newRequestQueue(Product_Update_Act.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/product_update.php";
        JSONObject object = new JSONObject();
        try {
            object.put("productid",pr_id);
            object.put("productname",name);
            object.put("price",price);
            object.put("offerprice",offer_price);
            object.put("desc",desc);

        }catch (Exception e){
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(Product_Update_Act.this, response.getString("Status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_Update_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);
        bt_back = findViewById(R.id.back_);
        te_desc = findViewById(R.id.te_description);
        te_name = findViewById(R.id.te_product_name);
        te_price = findViewById(R.id.te_price);
        te_offer_price = findViewById(R.id.te_offer_price);
        ed_description = findViewById(R.id.ed_description);
        ed_product_name = findViewById(R.id.ed_product_name);
        ed_price = findViewById(R.id.ed_price);
        ed_offer_price = findViewById(R.id.ed_offer_price);
        bt_update = findViewById(R.id.bt_update);

        Intent intent = getIntent();
        String de = intent.getStringExtra("de");
        String na = intent.getStringExtra("na");
        String pr = intent.getStringExtra("pr");
        String of = intent.getStringExtra("off");
        String prid = intent.getStringExtra("prid");

        te_desc.setText(de);
        te_name.setText(na);
        te_price.setText(pr);
        te_offer_price.setText(of);


        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_product_name.getText().toString();
                String price = ed_price.getText().toString();
                String offer = ed_offer_price.getText().toString();
                String desc = ed_description.getText().toString();
                Update_product(prid,name,price,offer,desc);
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}