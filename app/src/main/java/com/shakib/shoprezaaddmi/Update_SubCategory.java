package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class Update_SubCategory extends AppCompatActivity {
    TextView te_subcotegory,te_cotegory;
    ImageView im_back;
    RequestQueue requestQueue;
    EditText ed_subname;
    Button bt_update;
    String sub_id = "";
    void update_cot(String sub_id,String su){
        requestQueue = Volley.newRequestQueue(Update_SubCategory.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/subname_catname_update.php";
        JSONObject object = new JSONObject();
        try {
            object.put("subcatid",sub_id);
            object.put("subcatname",su);

        }catch (Exception e){
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(Update_SubCategory.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_SubCategory.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sub_category);
        te_cotegory = findViewById(R.id.te_cat_id);
        te_subcotegory = findViewById(R.id.te_sub_id);
        im_back = findViewById(R.id.im_back);
        ed_subname = findViewById(R.id.ed_up_subcategory);
        bt_update = findViewById(R.id.update_);

        Intent intent = getIntent();
        String sub = intent.getStringExtra("sub");
        String cat = intent.getStringExtra("cat");
        sub_id = intent.getStringExtra("sub_id");
        te_cotegory.setText(cat);
        te_subcotegory.setText(sub);

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String su = ed_subname.getText().toString();
                update_cot(sub_id,su);
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