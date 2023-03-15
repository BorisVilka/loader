package org.cryptonews.main.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.cryptonews.main.databinding.ActivityLoaderBinding;

public class LoaderActivity extends AppCompatActivity {

    ActivityLoaderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoaderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button3.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        });
    }
}