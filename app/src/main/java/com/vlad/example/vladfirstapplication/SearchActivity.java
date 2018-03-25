package com.vlad.example.vladfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void search(View view) {
        Intent intent = new Intent(this, ResultActivity.class);

        EditText editText = (EditText) findViewById(R.id.searchText);
        String searchString = editText.getText().toString();

        intent.putExtra("GIGI", searchString);

        startActivity(intent);
    }

}
