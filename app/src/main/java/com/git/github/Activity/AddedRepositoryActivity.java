package com.git.github.Activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.git.github.R;

public class AddedRepositoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_repository);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Repositories");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddedRepositoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}