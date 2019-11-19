package com.example.pract_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

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

    @Override
    public void onLoginButton(LoginFragment.User user) {
        LoginTask task = new LoginTask();
        task.execute(user);




    }

    public class LoginTask extends AsyncTask<LoginFragment.User,Void,Integer>{

        private LoginFragment.User mUser=null;
        @Override
        protected Integer doInBackground(LoginFragment.User... users) {
            if(users!=null && users.length>0) {
                mUser = users[0];

                //http://labtelema.ujaen.es/ssmm/autentica.php?user=user1&pass=12345
                //String url_cad="http://"+mUser.domain+":"+mUser.port+"/ssmm/autentica.php?user="+mUser.user+"&pass="+mUser.pass;
                String url_cad="http://labtelema.ujaen.es/ssmm/autentica.php?user=user1&pass=12345";
                try {
                    URL url = new URL(url_cad);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.getDoInput();
                    connection.connect();

                    int response = connection.getResponseCode();

                    if(response==200) {


                        BufferedReader br = new BufferedReader(new InputStreamReader( connection.getInputStream()));
                        String linea="";
                        String data ="";
                        while ((linea = br.readLine())!=null && !isCancelled()){
                            data+=linea;
                            Log.d("Recibido",linea);

                        }
                        if(data.length()>0){
                            return new Integer(1);
                        }
                    }




                    return new Integer(-3);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return new Integer(-1);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new Integer(-2);
                }


            }else
                return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer==1) {
                //Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                //intent.putExtra("data", mUser.getData());
                //startActivity(intent);
                Toast.makeText(getApplicationContext(),"Todo ha ido bien",Toast.LENGTH_LONG).show();
            }else
            {
                //Error
                Toast.makeText(getApplicationContext(),"algo ha ido mal "+integer,Toast.LENGTH_LONG).show();
            }
        }


    }
}
