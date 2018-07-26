package com.example.katarzkubat.goveggie.UI;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

import com.example.katarzkubat.goveggie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;

public class LocationActivity extends AppCompatActivity {

    @BindView(R.id.location_toolbar) Toolbar mToolbar;
    @BindView(R.id.location_permission_checkbox) CheckBox mCheckBox;
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(R.string.location);
        mToolbar.setTitleTextColor(getResources().getColor(white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             ActivityCompat.requestPermissions(LocationActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(LocationActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mCheckBox.setChecked(false);
        } else {
            mCheckBox.setChecked(true);
            mCheckBox.setEnabled(false);
        }
    }

}
