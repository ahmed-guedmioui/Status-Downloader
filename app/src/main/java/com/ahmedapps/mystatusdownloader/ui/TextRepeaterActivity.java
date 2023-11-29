package com.ahmedapps.mystatusdownloader.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

import com.ahmedapps.mystatusdownloader.R;
import com.ahmedapps.mystatusdownloader.utils.ConstMethod;
import com.ahmedapps.mystatusdownloader.utils.notifier.EventNotifier;
import com.ahmedapps.mystatusdownloader.utils.notifier.EventState;
import com.ahmedapps.mystatusdownloader.utils.notifier.IEventListener;
import com.ahmedapps.mystatusdownloader.utils.notifier.NotifierFactory;
import com.ahmedapps.mystatusdownloader.databinding.ActivityTextRepeaterBinding;

;

public class TextRepeaterActivity extends AppCompatActivity implements IEventListener {
    ActivityTextRepeaterBinding binding;

    String text = "";
    String limitNo;
    int count = 0;

    @Override
    public int eventNotify(int eventType, final Object eventObject) {
        int eventState = EventState.EVENT_IGNORED;
        if (eventType == EventState.EVENT_AD_LOADED_NATIVE) {
            eventState = EventState.EVENT_PROCESSED;
        }

        return eventState;
    }

    private void registerAdsListener() {
        EventNotifier notifier = NotifierFactory.getInstance().getNotifier(NotifierFactory.EVENT_NOTIFIER_AD_STATUS);
        notifier.registerListener(this, 1000);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextRepeaterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        registerAdsListener();
        limitNo = binding.etTextRepeateNumber.getText().toString();
        binding.btnRepeat.setOnClickListener(v -> {
            hideSoftKeyboard(TextRepeaterActivity.this);
            if (!TextUtils.isEmpty(binding.etTextMsg.getText().toString()) && !TextUtils.isEmpty(binding.etTextRepeateNumber.getText().toString())) {

                binding.etTextRepeates.getText().clear();
                String input = binding.etTextMsg.getText().toString();
                int number = Integer.parseInt(binding.etTextRepeateNumber.getText().toString());
                ArrayList<String> data = new ArrayList<>();
                if (binding.switchNewLine.isChecked()) {
                    for (int i = 0; i < number; i++) {
                        data.add(input);
                        data.add("\n");
                    }
                } else {
                    for (int i = 0; i < number; i++) {
                        data.add(input);
                    }
                }
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < data.size(); i++) {
                    builder.append(data.get(i));
                }
                binding.etTextRepeates.append(builder);
            } else {
                Toast.makeText(TextRepeaterActivity.this, getString(R.string.txt_please_enter_your_message_and_counter), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnCopy.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.etTextRepeates.getText().toString())) {
                ConstMethod.CopyToClipBoard(TextRepeaterActivity.this, binding.etTextRepeates.getText().toString());

            } else {
                Toast.makeText(TextRepeaterActivity.this, getString(R.string.txt_please_enter_your_message), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnClear.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.etTextRepeates.getText().toString())) {
                binding.etTextRepeates.getText().clear();
            } else {
                Toast.makeText(TextRepeaterActivity.this, getString(R.string.txt_please_enter_your_message), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnShare.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(binding.etTextRepeates.getText().toString())) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, binding.etTextRepeates.getText().toString());
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(TextRepeaterActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TextRepeaterActivity.this, getString(R.string.txt_please_enter_your_message), Toast.LENGTH_SHORT).show();
            }
        });
    }
}