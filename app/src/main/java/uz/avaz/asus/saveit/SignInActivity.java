package uz.avaz.asus.saveit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class SignInActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void login(View view) {

    }
}