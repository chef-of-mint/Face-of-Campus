package com.example.patronus.List_from_search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.patronus.R;
import com.example.patronus.Result;
import com.example.patronus.databinding.ActivityListBinding;
import com.example.patronus.databinding.FragmentHomeBinding;
import com.example.patronus.rec_home.Adapter;
import com.example.patronus.rec_home.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    private ActivityListBinding activityListBinding;
    LinearLayoutManager layoutManager;
    List<Search_Model> myList = new ArrayList<>();
    Search_Adapter myAdapter;
    ProgressDialog dialog;

    private String URL1 = "https://faceofcampus1.herokuapp.com/predict";
    private String URL2 = "https://faceofcampus2.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListBinding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(activityListBinding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait ...");
        dialog.show();


        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        activityListBinding.listRec.setLayoutManager(layoutManager);

        myAdapter = new Search_Adapter(myList);

        Intent data=getIntent();
        String val=data.getStringExtra("val");
        int num=getIntent().getIntExtra("intval",0);
        if(num==1 && val!=null){
            api_call(val,"","","",URL1);
        }
        else if(num==2 && val!=null){
            api_call("","",val,"",URL1);
        }
        else if(num==3 && val!=null){
            api_call("",val,"","",URL1);
        }
        else if(num==4 && val!=null){
            api_call("","","",val,URL2);
        }
        else {
            Toast.makeText(getApplicationContext(), "Enter Something", Toast.LENGTH_LONG).show();
            finish();
        }

        activityListBinding.listRec.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void api_call(String sname,String splace,String sbranch,String sdomain,String url)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("hhhhhh", response);

               // Toast.makeText(ListActivity.this , "Success",Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray branch = jsonObject.getJSONArray("branch");
                    JSONArray image = jsonObject.getJSONArray("image");
                    JSONArray name = jsonObject.getJSONArray("name");
                    JSONArray place = jsonObject.getJSONArray("place");
                    JSONArray year = jsonObject.getJSONArray("year");

                    Search_Model model = new Search_Model();

                    for ( int i=0 ; i< branch.length() ; i++ )
                    {
                        model=new Search_Model();
                        model.setName(name.getString(i));
                        model.setPlace(place.getString(i));
                        model.setBranch(branch.getString(i));
                        String str[] = image.getString(i).split("'",3);
                        model.setImg(str[1]);
                        model.setYear(year.getString(i));
                        myList.add(model);
                        myAdapter.notifyDataSetChanged();
                    }

                    myAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hhhhh",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                if(url==URL1)
                {
                    map.put("name", sname);
                    map.put("place", splace);
                    map.put("branch", sbranch);
                }
                else
                    map.put("studying",sdomain);
                return map;
            }
        };

        queue.add(stringRequest);
    }

}