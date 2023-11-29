package com.ahmedapps.mystatusdownloader.utils;

import android.app.Application;
import android.content.Context;


import java.io.File;

public class AppClass extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        trimCache(getApplicationContext());
    }

    public void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
        }
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

}
