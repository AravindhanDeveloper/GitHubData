package com.git.github.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.git.github.Common.Constants;
import com.git.github.Model.BaseListResponse;
import com.git.github.Model.RespModel;
import com.git.github.R;
import com.git.github.Repository.CreateRepository;
import com.git.github.Repository.DetailsListRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class AddedRepositoryActivity extends AppCompatActivity {
    CreateRepository createRepository;
    TextInputEditText repositoryDesTxt, repositoryNameTxt;
    TextInputLayout repositoryDesLayer, repositoryNameLayer;
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_repository);
        initView();
        repositoryNameTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (repositoryNameTxt.length() != 0) {
                    repositoryNameLayer.setError(null);
                }


            }
        });
        repositoryDesTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (repositoryDesTxt.length() != 0) {
                    repositoryDesLayer.setError(null);
                }


            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    private void validation() {
        if (repositoryNameTxt.getText().toString().length() == 0) {
            repositoryNameLayer.setError("please fill this field");
        }
        if (repositoryDesTxt.getText().toString().length() == 0) {
            repositoryDesLayer.setError("please fill this field");
        }
        if (repositoryNameTxt.getText().toString().length() != 0 && repositoryDesTxt.getText().toString().length() != 0) {
            PostAPI();
        }
    }

    private void initView() {
        repositoryDesTxt = findViewById(R.id.repository_des_txt);
        repositoryDesLayer = findViewById(R.id.repository_des_layer);
        repositoryNameLayer = findViewById(R.id.repository_name_layer);
        repositoryNameTxt = findViewById(R.id.repository_name_txt);
        createBtn = findViewById(R.id.create_btn);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Repositories");
        createRepository = new CreateRepository(AddedRepositoryActivity.this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddedRepositoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void PostAPI() {
        final ProgressDialog progressDialog = new ProgressDialog(AddedRepositoryActivity.this);
        progressDialog.setMessage("Creating Repository\nPlease do not press back or close the app");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.show();
        createRepository.setGetCommonAPIDetails(new CreateRepository.GetCommonAPIDataSuccessCallBack() {

            @Override
            public void getCommonAPIDataSuccess(BaseListResponse apiArrayResponse) {
                if (apiArrayResponse != null) {
                    if (apiArrayResponse.getCode() == Constants.API_STATUS_SUCCESS) {
                        progressDialog.dismiss();


                    }
                } else {

                }

            }

            @Override
            public void getCommonAPIDataFailure(String message) {
                progressDialog.dismiss();
            }
        });

        RespModel n = new RespModel();
        n.setDescription(repositoryDesTxt.getText().toString());
        n.setName(repositoryNameTxt.getText().toString());
        n.setHomepage("https://github.com");
        createRepository.getResp("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", n);
        Log.e("postdetails", new Gson().toJson(n));

    }
}