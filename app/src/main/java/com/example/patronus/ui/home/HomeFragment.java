package com.example.patronus.ui.home;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patronus.HomeActivity;
import com.example.patronus.R;
import com.example.patronus.Result;
import com.example.patronus.databinding.FragmentHomeBinding;
import com.example.patronus.rec_home.Adapter;
import com.example.patronus.rec_home.Model;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    LinearLayoutManager layoutManager;
    List<Model> imgList;
    Adapter adapter;

    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);


        imgList = new ArrayList<>();
        imgList.add(new Model(R.drawable.ic_launcher_background, "LEARN and GROW together"));
        imgList.add(new Model(R.drawable.ic_launcher_foreground, "Find your peer group"));
        imgList.add(new Model(R.drawable.ic_launcher_background, "Real time facial recognition"));
        adapter = new Adapter(imgList);
        adapter.notifyDataSetChanged();

        binding.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkAndRequestPermissions((Activity) getContext())) {
                    chooseImage(getContext());
                }
                //send image to api
//                Intent intent = new Intent(getContext(), HomeActivity.class);
//                startActivity(intent);

            }

        });


        return root;
    }
    // function to let's the user to choose image from camera or gallery

    private void chooseImage(Context context) {

        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array

        // create a dialog for showing the optionsMenu

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // set the items in builder

        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (optionsMenu[i].equals("Take Photo")) {

                    // Open the camera and get the photo

                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                    //Toast.makeText(getContext(), "h1", Toast.LENGTH_SHORT).show();
                } else if (optionsMenu[i].equals("Choose from Gallery")) {

                    // choose from  external storage

                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);
                    //Toast.makeText(getContext(), "h2", Toast.LENGTH_SHORT).show();

                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }


    // function to check permission

    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    // Handled permission Result


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();

                } else if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    chooseImage(getContext());
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("result code", resultCode + "");
        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case 0:
                    Log.d("request code", resultCode + " " + data);
                 //   Toast.makeText(getContext(), "suc1" + requestCode + " " + data, Toast.LENGTH_SHORT).show();
                    if (resultCode == RESULT_OK && data != null) {
                    //    Toast.makeText(getContext(), "suc2", Toast.LENGTH_SHORT).show();
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        Intent intent = new Intent(getContext(), Result.class);
                        intent.putExtra("int",0);
                        intent.putExtra("BitmapImage", selectedImage);
                        startActivity(intent);
                    //    Toast.makeText(getContext(), "suc3", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        Intent intent2 = new Intent(getActivity(), Result.class);
                        intent2.putExtra("int",1);
                        intent2.putExtra("BitmapImage",selectedImage.toString());
                        startActivity(intent2);

                    }
                    break;
            }

        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}