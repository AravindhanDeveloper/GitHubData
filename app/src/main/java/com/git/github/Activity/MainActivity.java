package com.git.github.Activity;

import android.app.Dialog;
import android.content.Intent;

import java.lang.reflect.Type;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.git.github.Adapter.DetailsListAdapter;
import com.git.github.Model.BaseListResponse;
import com.git.github.Model.DetailsModel;
import com.git.github.R;
import com.git.github.Repository.DetailsListRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DetailsListRepository detailsListRepository;
    RecyclerView recyclerview;
    private ArrayList<DetailsModel> detailModalArrayList;
    public Dialog dialog;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Git Repositories");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        recyclerview = findViewById(R.id.recyclerview);
        createBtn = findViewById(R.id.create_btn);
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        detailsListRepository = new DetailsListRepository(MainActivity.this);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddedRepositoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getDetails();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("detail", null);
        Type type = new TypeToken<ArrayList<DetailsModel>>() {
        }.getType();
        detailModalArrayList = gson.fromJson(json, type);
        DetailsListAdapter adapter = new DetailsListAdapter(MainActivity.this, detailModalArrayList);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (detailModalArrayList == null) {
            detailModalArrayList = new ArrayList<>();
            createBtn.setVisibility(View.VISIBLE);

        }
    }

    private void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(detailModalArrayList);

        editor.putString("detail", json);

        editor.apply();
    }

    private void getDetails() {
        showLoader();
        detailsListRepository.setGetCommonAPIDetails(new DetailsListRepository.GetCommonAPIDataSuccessCallBack() {
            @Override
            public void getCommonAPIDataSuccess(List apiResponse) {
                if (apiResponse != null) {
                    hideLoader();
                    List<DetailsModel> result = (List<DetailsModel>) apiResponse;

                    detailModalArrayList = (ArrayList<DetailsModel>) result;
                    DetailsListAdapter adapter = new DetailsListAdapter(MainActivity.this, result);
                    recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    saveData();
                } else {
                    hideLoader();
                    loadData();

                    Toast.makeText(MainActivity.this, "Network failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void getCommonAPIDataFailure(String message) {
                Toast.makeText(MainActivity.this, "Network failed", Toast.LENGTH_SHORT).show();
                hideLoader();
                loadData();


            }
        });

        detailsListRepository.getDeatils();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, AddedRepositoryActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoader() {
        if (dialog == null) {
            dialog = new Dialog(this);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_progress_view);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideLoader() {
        if (dialog.isShowing() && dialog == null) {
            dialog = new Dialog(this);
        }
        dialog.dismiss();
    }
}