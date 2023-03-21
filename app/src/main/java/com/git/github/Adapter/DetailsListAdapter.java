package com.git.github.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.git.github.Activity.MainActivity;
import com.git.github.Common.Constants;
import com.git.github.Model.DetailsModel;
import com.git.github.R;
import com.google.android.material.textview.MaterialTextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class DetailsListAdapter extends RecyclerView.Adapter<DetailsListAdapter.DetailsViewHolder> {
    Context mContext;

    List<DetailsModel> result = new ArrayList<>();


    public DetailsListAdapter(MainActivity mainActivity, List<DetailsModel> result) {
        this.mContext = mainActivity;
        this.result = result;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_card, parent, false);
        return new DetailsViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        final DetailsModel detailsDataList = result.get(position);

        String name = detailsDataList.getName();
        String des = detailsDataList.getDescription();
        holder.repositoryTxt.setText(name);
        if (des != null) {
            holder.repositorydesTxt.setText(des);
        } else {
            holder.repositorydesTxt.setText("not provided");

        }
        holder.gitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://github.com/AravindhanDeveloper/" + name));
                mContext.startActivity(viewIntent);
            }
        });
        holder.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "GIT HUB");
                    String shareMessage= "\nRepository Name: "+name+"\n\n";
                    shareMessage = shareMessage + "https://github.com/AravindhanDeveloper/" + name;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    mContext.startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView repositoryTxt, repositorydesTxt;
        RelativeLayout gitLayout;
        ImageView shareIcon;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            repositoryTxt = itemView.findViewById(R.id.repository_txt);
            repositorydesTxt = itemView.findViewById(R.id.repositorydes_txt);
            gitLayout = itemView.findViewById(R.id.git_layout);
            shareIcon = itemView.findViewById(R.id.share_icon);
        }
    }
}
