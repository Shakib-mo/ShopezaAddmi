package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shakib.shoprezaaddmi.adapter.MySubcategory_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Edt_Sub_Category_Spinner extends AppCompatActivity {

    Spinner spinner;
    EditText ed_sub_cutegory;
    Button button;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    RequestQueue requestQueue_up;
    RecyclerView recyclerView;

    ArrayList<String> arrayList= new ArrayList<>();
    ArrayList<String> arrayList1= new ArrayList<>();

    ArrayList<SubCotegory_Get_Set> array_display = new ArrayList<>();
    MySubcategory_Adapter adapter;

    void Display_Data_spin(){
            requestQueue = Volley.newRequestQueue(Edt_Sub_Category_Spinner.this);
            String url = "https://shakibmohd.000webhostapp.com/api_shop/reza_display.php";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONArray array = response;
                    JSONObject object = new JSONObject();
                    try {
                        for (int i = 0;i<array.length();i++){
                            object =array.getJSONObject(i);
                            arrayList.add(object.getString("name"));
                            arrayList1.add(object.getString("catid"));
                        }
                        spinner.setAdapter(new ArrayAdapter<String>(Edt_Sub_Category_Spinner.this,
                            android.R.layout.simple_spinner_dropdown_item,arrayList));

                    }catch (Exception e){
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   Toast.makeText(Edt_Sub_Category_Spinner.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            });
            requestQueue.add(jsonArrayRequest);

    }

    void DisPlayData_Sub(){
        progressBar.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(Edt_Sub_Category_Spinner.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/subid_catid_name_disply.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray array = response;
                JSONObject object;
                try {
                    for (int i = 0;i<array.length();i++){
                        object = array.getJSONObject(i);

                        array_display.add(new SubCotegory_Get_Set(object.getString("subcatid"),object.getString("sub_name"),
                                object.getString("catid"),object.getString("pic")));
                    }
                    adapter = new MySubcategory_Adapter(Edt_Sub_Category_Spinner.this,array_display);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }catch (Exception e){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edt_Sub_Category_Spinner.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

   void Uplode_Data(String sub_name,String cat){
        requestQueue_up = Volley.newRequestQueue(Edt_Sub_Category_Spinner.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/subname_catname_save.php";
        JSONObject object = new JSONObject();
        try {
            object.put("sub_name",sub_name);
            object.put("cat_id",cat);

        }catch (Exception e){
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(),response.getString("status"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue_up.add(jsonObjectRequest);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt_sub_category_spinner_);
        spinner = findViewById(R.id.spinner_);
        ed_sub_cutegory = findViewById(R.id.edt_sub_name);
        button = findViewById(R.id.bt_sub);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view_);
        Display_Data_spin();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Edt_Sub_Category_Spinner.this));
        DisPlayData_Sub();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub_cutegory = ed_sub_cutegory.getText().toString();
                String cat_id=arrayList1.get(spinner.getSelectedItemPosition());
                Uplode_Data(sub_cutegory,cat_id);
                Intent intent = new Intent(Edt_Sub_Category_Spinner.this,Add_Sub_Cat_Category.class);
                startActivity(intent);

            }
        });
    }
}