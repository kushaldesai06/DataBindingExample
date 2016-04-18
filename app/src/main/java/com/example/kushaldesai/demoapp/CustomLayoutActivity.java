package com.example.kushaldesai.demoapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.example.kushaldesai.demoapp.CustomLayout.CustomLayoutEx;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Kushal.Desai on 4/15/2016.
 */
public class CustomLayoutActivity extends AppCompatActivity {

    @OnClick(R.id.customview1)
    public void onCustomeView1clicked(View view){
        Toast.makeText(this, view.getId() == R.id.customview1 ? "Background" : "Foreground", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.customview2)
    public void onCustomeView2clicked(View view){
        Toast.makeText(this, view.getId() == R.id.customview1 ? "Background" : "Foreground", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_main);
        ButterKnife.inject(this);
        // testing
    }

}
