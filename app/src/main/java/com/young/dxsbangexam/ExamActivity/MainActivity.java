package com.young.dxsbangexam.ExamActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.young.dxsbangexam.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void 跳转(View view) {
        Intent intent = new Intent(MainActivity.this, ExamChooseActivity.class);
        startActivity(intent);

    }
}
