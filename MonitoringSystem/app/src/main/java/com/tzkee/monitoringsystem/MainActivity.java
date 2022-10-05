package com.tzkee.monitoringsystem;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.tzkee.monitoringsystem.Activities.BasicRtmpActivity;
import com.tzkee.monitoringsystem.utils.ActivityLink;
import com.tzkee.monitoringsystem.utils.ImageAdapter;

import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static String TAG = "MainActivity";
    static
    {
        if(OpenCVLoader.initDebug()) {
            Log.d(TAG,"OpenCV installed successfully");
        }
        else {
            Log.d(TAG, "OpenCV is not installed");
        }
    }

    private GridView list;
    private List<ActivityLink> activities;

    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        createList();
        setListAdapter(activities);
    }

    private void createList() {
        activities = new ArrayList<>();
        activities.add(new ActivityLink(new Intent(this, BasicRtmpActivity.class),
                getString(R.string.basic_rtmp_activity)));
    }

    private void setListAdapter(List<ActivityLink> activities) {
        list.setAdapter(new ImageAdapter(activities));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (hasPermissions(this, PERMISSIONS)) {
            ActivityLink link = activities.get(i);
            if (Build.VERSION.SDK_INT >= JELLY_BEAN) {
                startActivity(link.getIntent());
                overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            } else {
                Toast.makeText(this, "You need min Android JELLY_BEAN",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            showPermissionsErrorAndRequest();
        }
    }


    private void showPermissionsErrorAndRequest() {
        Toast.makeText(this, "You need permissions before", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}