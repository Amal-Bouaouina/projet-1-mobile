package amal.bouaouina.callerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Accueil extends AppCompatActivity {
public static ArrayList<Contact> data = new ArrayList<>();
    Button btnAjouter;
    Button btnAfficher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btnAjouter = findViewById(R.id.btnAjouter);
        btnAfficher = findViewById(R.id.btnAfficher);

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, Ajout.class);
                startActivity(intent);
            }
        });


        btnAfficher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, Affiche.class);
                startActivity(intent);}
            });

    }
}