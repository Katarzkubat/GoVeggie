package com.example.katarzkubat.goveggie.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.katarzkubat.goveggie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katarzkubat.goveggie.R.color.white;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.settings_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(R.string.settings);
        mToolbar.setTitleTextColor(getResources().getColor(white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24px);
    }
}
