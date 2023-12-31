package com.ahmedapps.mystatusdownloader.ui.sticker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ahmedapps.mystatusdownloader.ui.sticker.model.StickrMainOption;

import java.util.ArrayList;

import com.ahmedapps.mystatusdownloader.R;

import com.ahmedapps.mystatusdownloader.databinding.ActivityStickerBinding;

import com.ahmedapps.mystatusdownloader.ui.sticker.repeater.RepeaterStickrMainOpt;

public class StickerActivity extends AppCompatActivity {

    private ActivityStickerBinding binding;

    public final static String STICKER_1 = "Baby";
    public final static String STICKER_2 = "Birthday";
    public final static String STICKER_3 = "Emoj";
    public final static String STICKER_4 = "Food";
    public final static String STICKER_5 = "Halloween";
    public final static String STICKER_6 = "Love";
    public final static String STICKER_7 = "Music";
    public final static String STICKER_8 = "Sale";
    public final static String STICKER_9 = "Social";
    public final static String STICKER_10 = "Transport";
    public final static String STICKER_11 = "Travel";

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStickerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        

        binding.rvStickerOptions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        stickerMainAdapter();
    }

    private void stickerMainAdapter() {

        ArrayList<StickrMainOption> stickerParentList = createStickerParentList();
        RepeaterStickrMainOpt stickerCategoryListAdapter = new RepeaterStickrMainOpt(StickerActivity.this, stickerParentList, (v, pos) -> {

                Intent intent = new Intent(StickerActivity.this, StickerListActivity.class);
                intent.putExtra("curName", stickerParentList.get(pos).getParentText());
                startActivity(intent);

        });

        binding.rvStickerOptions.setAdapter(stickerCategoryListAdapter);

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private ArrayList<StickrMainOption> createStickerParentList() {
        String[] stickerListTitle;
        Integer[] stickerListIcon;

        stickerListTitle = new String[]{STICKER_1, STICKER_2, STICKER_3, STICKER_4, STICKER_5, STICKER_6, STICKER_7, STICKER_8,
                STICKER_9, STICKER_10, STICKER_11};
        stickerListIcon = new Integer[]{R.drawable.e_sti_1, R.drawable.e_sti_2, R.drawable.e_sti_3, R.drawable.e_sti_4, R.drawable.e_sti_5, R.drawable.e_sti_6, R.drawable.e_sti_7, R.drawable.e_sti_8, R.drawable.e_sti_9, R.drawable.e_sti_10,
                R.drawable.e_sti_11};

        ArrayList<StickrMainOption> stickerParentList = new ArrayList<>();

        for (int k = 0; k < stickerListIcon.length; k++) {
            StickrMainOption stickerParentMode3 = new StickrMainOption();
            stickerParentMode3.setParentIcon(getResources().getDrawable(stickerListIcon[k]));
            stickerParentMode3.setParentText(stickerListTitle[k]);
            stickerParentList.add(stickerParentMode3);
        }
        return stickerParentList;
    }
}