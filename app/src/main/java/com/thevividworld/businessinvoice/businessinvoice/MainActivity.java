package com.thevividworld.businessinvoice.businessinvoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dragi Postolovski on 22-Aug-17.
 */

public class MainActivity extends AppCompatActivity {

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login);

    }

    public void Login(View view) {
        Intent dashboard = new Intent(MainActivity.this, Dashboard.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(dashboard);
    }
}