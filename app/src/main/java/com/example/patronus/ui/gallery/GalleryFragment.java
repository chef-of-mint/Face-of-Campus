package com.example.patronus.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.patronus.List_from_search.ListActivity;
import com.example.patronus.R;
import com.example.patronus.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.searchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String str =binding.editText.getText().toString();
                Intent intent= new Intent(getContext(), ListActivity.class);
                intent.putExtra("val",binding.editText.getText().toString());
                intent.putExtra("intval",1);
                startActivity(intent);
            }
        });
        binding.searchBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String str =binding.editText.getText().toString();
                Intent intent= new Intent(getContext(), ListActivity.class);
                intent.putExtra("val",binding.editText.getText().toString());
                intent.putExtra("intval",2);
                startActivity(intent);
            }
        });binding.searchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String str =binding.editText.getText().toString();
                Intent intent= new Intent(getContext(), ListActivity.class);
                intent.putExtra("val",binding.editText.getText().toString());
                intent.putExtra("intval",3);
                startActivity(intent);
            }
        });binding.searchDomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String str =binding.editText.getText().toString();
                Intent intent= new Intent(getContext(), ListActivity.class);
                intent.putExtra("val",binding.editText.getText().toString());
                intent.putExtra("intval",4);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}