package amal.bouaouina.callerapp;
import androidx.core.splashscreen.SplashScreen;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnvalider, btnquitter;
    EditText edEmail, edPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnquitter = findViewById(R.id.btnQte_auth);
        btnvalider = findViewById(R.id.btnValider_ath);
        edEmail = findViewById(R.id.edEmail_auth);
        edPwd = findViewById(R.id.edPwd_auth);

        btnquitter.setOnClickListener(v -> finish());

        btnvalider.setOnClickListener(v -> {
            String email = edEmail.getText().toString();
            String pwd = edPwd.getText().toString();
            if (email.equals("amal") && pwd.equals("111")) {
                startActivity(new Intent(MainActivity.this, Affiche.class));
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Vérifier les infos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}