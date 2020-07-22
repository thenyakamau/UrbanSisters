package ke.co.urbansisters.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ke.co.urbansisters.R;
import ke.co.urbansisters.ui.auth.AuthActivity;

public class SplashActivity extends AppCompatActivity {

    private  int SPLASH_RUNTIME = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread splash= new Thread(){
            public void run(){
                try{
                    sleep(SPLASH_RUNTIME);
                    startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
            }

        };
        splash.start();
    }
}