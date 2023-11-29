package com.ahmedapps.mystatusdownloader.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.ahmedapps.mystatusdownloader.utils.Common;

import java.util.List;

import com.ahmedapps.mystatusdownloader.models.Status;
import com.ahmedapps.mystatusdownloader.R;

public class ImageAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private final List<Status> imagesList;
    private Context context;
    private final RelativeLayout container;
    
    private int counter = 1;

    public ImageAdapter(List<Status> imagesList, RelativeLayout container) {
        this.imagesList = imagesList;
        this.container = container;
        
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {

        final Status status = imagesList.get(position);
        if (status.isApi30()) {
//            holder.save.setVisibility(View.GONE);
            Glide.with(context).load(status.getDocumentFile().getUri()).into(holder.imageView);
        } else {
//            holder.save.setVisibility(View.VISIBLE);
            Glide.with(context).load(status.getFile()).into(holder.imageView);
        }

        holder.save.setOnClickListener(v -> {
            if (counter == 2) {
                counter = 1;

                    Common.copyFile(status, context, container);

            } else {
                counter++;
                Common.copyFile(status, context, container);
            }
        });

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/jpg");

            if (status.isApi30()) {
                shareIntent.putExtra(Intent.EXTRA_STREAM, status.getDocumentFile().getUri());
            } else {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + status.getFile().getAbsolutePath()));
            }

            context.startActivity(Intent.createChooser(shareIntent, "Share image"));

        });

        holder.imageView.setOnClickListener(v -> {

            if (counter == 2) {
                counter = 1;

                    final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View view = inflater.inflate(R.layout.view_image_full_screen, null);
                    alertD.setView(view);

                    ImageView imageView = view.findViewById(R.id.img);
                    if (status.isApi30()) {
                        Glide.with(context).load(status.getDocumentFile().getUri()).into(imageView);
                    } else {
                        Glide.with(context).load(status.getFile()).into(imageView);
                    }

                    AlertDialog alert = alertD.create();
                    alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                    alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alert.show();

            } else {
                counter++;
                final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.view_image_full_screen, null);
                alertD.setView(view);

                ImageView imageView = view.findViewById(R.id.img);
                if (status.isApi30()) {
                    Glide.with(context).load(status.getDocumentFile().getUri()).into(imageView);
                } else {
                    Glide.with(context).load(status.getFile()).into(imageView);
                }

                AlertDialog alert = alertD.create();
                alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alert.show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
