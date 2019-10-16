package com.example.pract_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LoginFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Acabo de arrancar");

        fragment = new LoginFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //Fragment f = fm.findFragmentById(R.id.contenedor);
        Fragment f = fm.findFragmentByTag("login");
        if (f == null) {
            ft.add(R.id.contenedor, fragment,"login");
        } else {
            Toast.makeText(this, "YA HAY UNO", Toast.LENGTH_LONG).show();
        }
        ft.commit();

    }
}
