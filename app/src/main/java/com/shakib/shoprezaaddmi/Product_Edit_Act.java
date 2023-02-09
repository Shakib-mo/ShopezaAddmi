package com.shakib.shoprezaaddmi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product_Edit_Act extends AppCompatActivity {
    Spinner spinner_cat,spinner_sub;
    ImageView bt_back,pic;
    Button bt_save;
    String string_image;
    Bitmap bitmap;
    private static int i = 0;
    EditText ed_description,ed_pro_name,ed_price,ed_offer_price;
    RequestQueue requestQueue,requestQueue_cat,requestQueue_sub;

    ArrayList<String> arrayList_cat= new ArrayList<>();
    ArrayList<String> arrayList_cat1= new ArrayList<>();
    ArrayList<String> arrayList_sub= new ArrayList<>();
    ArrayList<String> arrayList_sub1= new ArrayList<>();

    void Data_Cat_spin(){
        requestQueue_cat = Volley.newRequestQueue(Product_Edit_Act.this);
        String url = "https://shakibmohd.000webhostapp.com/api_shop/reza_display.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray array = response;
                JSONObject object = new JSONObject();
                try {
                    for (int i = 0;i<array.length();i++){
                        object =array.getJSONObject(i);
                        arrayList_cat.add(object.getString("name"));
                        arrayList_cat1.add(object.getString("catid"));
                    }
                    spinner_cat.setAdapter(new ArrayAdapter<String>(Product_Edit_Act.this,
                            android.R.layout.simple_spinner_dropdown_item,arrayList_cat));

                }catch (Exception e){
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_Edit_Act.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue_cat.add(jsonArrayRequest);

    }
    void Data_Sub_spin(String cid){
        requestQueue_sub = Volley.newRequestQueue(Product_Edit_Act.this);
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            object.put("catid",cid);
            array.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/subname_catid_disply.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url, array, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray array = response;
                JSONObject object= new JSONObject();
                arrayList_sub.clear();
                arrayList_sub1.clear();
                try {
                    for (int i = 0;i<array.length();i++){
                        object = array.getJSONObject(i);
                        arrayList_sub.add(object.getString("sub_name"));
                        arrayList_sub1.add(object.getString("subcatid"));
                    }
                    spinner_sub.setAdapter(new ArrayAdapter<String>(Product_Edit_Act.this,
                            android.R.layout.simple_spinner_dropdown_item,arrayList_sub));

                }catch (Exception e){
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_Edit_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue_sub.add(jsonArrayRequest);
    }

    void Save_product(String desce,String pr_name,String price,String offer_price,String subcat){
        requestQueue = Volley.newRequestQueue(Product_Edit_Act.this);
        String Url = "https://shakibmohd.000webhostapp.com/api_shop/product_save.php";
        JSONObject object = new JSONObject();
        try {
            object.put("productname",pr_name);
            object.put("price",price);
            object.put("offerprice",offer_price);
            object.put("subcatid",subcat);
            object.put("desc",desce);

        }catch (Exception e){
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(Product_Edit_Act.this, response.getString("Status"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_Edit_Act.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        bt_back = findViewById(R.id.back_);
        ed_description = findViewById(R.id.ed_description_edt);
        ed_pro_name = findViewById(R.id.ed_product_name_edt);
        ed_price = findViewById(R.id.ed_product_price_edt);
        ed_offer_price = findViewById(R.id.ed_product_offer_price_edt);
        pic = findViewById(R.id.img_product_edt);
        bt_save = findViewById(R.id.bt_save);
        spinner_cat = findViewById(R.id.sp_cat);
        spinner_sub = findViewById(R.id.sp_sub);

        spinner_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Data_Sub_spin(arrayList_cat1.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Data_Cat_spin();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = ed_description.getText().toString();
                String name = ed_pro_name.getText().toString();
                String price = ed_price.getText().toString();
                String offer = ed_offer_price.getText().toString();

                String sub_catid=arrayList_sub1.get(spinner_sub.getSelectedItemPosition());

                Save_product(desc,name,price,offer,sub_catid);
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.camera:
                                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(in, i);
                                return true;
                            case R.id.gallery:
                                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent,i);
                                return true;
                        }
                        return true;
                    }
                });
            }
        });
    }
}