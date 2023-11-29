package com.ahmedapps.mystatusdownloader.ui.sticker.repeater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedapps.mystatusdownloader.ui.sticker.CallBack_PIP;
import com.ahmedapps.mystatusdownloader.ui.sticker.model.StickrMainOption;

import java.util.ArrayList;

import com.ahmedapps.mystatusdownloader.databinding.LayoutStickerMainOptionBinding;

public class RepeaterStickrMainOpt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final String NATIVE_AD = "NativeAd";
    private final ArrayList<StickrMainOption> appMdlStikrMainOptArrayList;
    private final CallBack_PIP callBackPip;
    Activity mActivity;

    public RepeaterStickrMainOpt(Activity activity, ArrayList<StickrMainOption> appMdlStikrMainOptArrayList, CallBack_PIP callBackPip) {
        super();
        this.mActivity = activity;
        this.appMdlStikrMainOptArrayList = appMdlStikrMainOptArrayList;
        this.callBackPip = callBackPip;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

            @NonNull LayoutStickerMainOptionBinding itemBinding = LayoutStickerMainOptionBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
            return new ViewHolder(itemBinding);

    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder._tvTitle.setText(appMdlStikrMainOptArrayList.get(i).getParentText());
            holder._ivPhotoThumb.setImageDrawable(appMdlStikrMainOptArrayList.get(i).getParentIcon());

        }
    }


    @Override
    public int getItemCount() {
        return appMdlStikrMainOptArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView _ivPhotoThumb;
        public TextView _tvTitle;


        public ViewHolder(final LayoutStickerMainOptionBinding itemView) {
            super(itemView.getRoot());
            _ivPhotoThumb = itemView.ivPhotoThumb;
            _tvTitle = itemView.tvTitle;

            _tvTitle.setVisibility(View.VISIBLE);
            itemView.getRoot().setOnClickListener(v -> callBackPip.onItemClick(itemView.getRoot(), getLayoutPosition()));
        }
    }

}

