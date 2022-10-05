package com.tzkee.monitoringsystem.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class PathUtils {

    public static void updateGallery(Context context, String path) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
    }
    public static File getRecordPath() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        return new File(storageDir.getAbsolutePath() + "/android-monitoring-app");
    }
}
