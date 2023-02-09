package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shakib.shoprezaaddmi.adapter.MySubcategory_Adapter;
import com.shakib.shoprezaaddmi.adapter.SubItem_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Add_Sub_Cat_Category extends AppCompatActivity{

    ImageView back;
    ImageButton imageBt_add;
    RecyclerView recyclerView,recyclerView1;
    RequestQueue requestQueue,requestQueue1;
    MySubcategory_Adapter adapter;
    ProgressBar progressBar;
    ArrayList<SubCotegory_Get_Set> arrayList;
    ArrayList<Category_Set_get> arrayList1= new ArrayList<>();
    SubItem_Adapter adapter1;
    SubItem_Adapter.ClickListener listener;


    void DesPlayData(String cid){
        progressBar.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(Add_Sub_Cat_Category.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/subname_catid_disply.php";
        JSONObject obj=new JSONObject();
        JSONArray object = new JSONArray();

        try {
            obj.put("catid",cid);
            object.put(obj);
        }catch (Exception e){
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url,object, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray array = response;
                JSONObject object;
                arrayList = new ArrayList<SubCotegory_Get_Set>();
                try {
                    for (int i = 0;i<array.length();i++){
                        object = array.getJSONObject(i);

                        arrayList.add(new SubCotegory_Get_Set(object.getString("subcatid"),object.getString("sub_name"),
                                object.getString("catid"),object.getString("pic")));
                    }

                    adapter = new MySubcategory_Adapter(Add_Sub_Cat_Category.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Sub_Cat_Category.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    void Disply_Item_Data(){
        progressBar.setVisibility(View.VISIBLE);
        requestQueue1 = Volley.newRequestQueue(Add_Sub_Cat_Category.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/reza_display.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray array = response;
                    JSONObject object = new JSONObject();
                    for (int i = 0;i<array.length();i++){
                        object = array.getJSONObject(i);
                        arrayList1.add(new Category_Set_get(object.getString("catid")
                                ,object.getString("name"),object.getString("pic")));
                    }
                    adapter1 = new SubItem_Adapter(Add_Sub_Cat_Category.this,arrayList1,listener);
                    recyclerView1.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }catch (Exception e){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add_Sub_Cat_Category.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue1.add(jsonArrayRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_cat_category);
        back = findViewById(R.id.back_);
        progressBar = findViewById(R.id.progress_bar);
        imageBt_add = findViewById(R.id.image_add_cotegory_);
        recyclerView = findViewById(R.id.recycler_view_subCategory);
        recyclerView1 = findViewById(R.id.recycler_view_subCategory_name);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Add_Sub_Cat_Category.this));
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(Add_Sub_Cat_Category.this));
        Disply_Item_Data();

        listener = new SubItem_Adapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                DesPlayData(arrayList1.get(position).catid);
            }
        };


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imageBt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Edt_Sub_Category_Spinner.class);
                startActivity(i);
            }
        });

    }
}