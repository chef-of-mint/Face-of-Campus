package com.example.patronus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.patronus.databinding.ActivityResultBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Result extends AppCompatActivity {
    ActivityResultBinding binding;

    private String URL = "https://faceofcampus.herokuapp.com/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int a = -1 ;
        a = getIntent().getIntExtra("int",-1);

        if ( a == 0 )
        {
            Bitmap bitmap = getIntent().getParcelableExtra("BitmapImage");
            if ( bitmap != null )
            {
                binding.imageView2.setImageBitmap(bitmap);
                api_call(convert(bitmap));
                Log.d("hhhhh",convert(bitmap));
            }
        }else if ( a==1 )
        {
            Uri uri = Uri.parse(getIntent().getStringExtra("BitmapImage"));
            if (uri != null) {
                binding.imageView2.setImageURI(uri);

                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                api_call(convert(selectedImage));
            }
        }
        binding.mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void api_call(String string)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Result.this , "Success",Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String name = jsonObject.getString("name");
                    String place = jsonObject.getString("place");
                    String branch = jsonObject.getString("branch");
                    String image = jsonObject.getString("image");
                    String year = jsonObject.getString("year");

                    binding.textView1.setText(name);
                    binding.textView2.setText(place);
                    binding.textView3.setText(branch);
                    String txt ="";
                    if(year.equals("1"))txt+="'st";
                    else if(year.equals("2"))txt+="'nd";
                    else if(year.equals("3"))txt+="'rd";
                    else txt+="'th";


                    binding.textView4.setText(year+txt);
//                    binding.imageView2.setImageBitmap(covert_y(image));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hhhhh",error.toString());
                Toast.makeText(Result.this, "No Record found", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("image",string);
                return map;
            }
        };

        queue.add(stringRequest);
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public Bitmap covert_y ( String str)
    {
        byte[] decodedString = Base64.decode(str, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}