package amal.bouaouina.callerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Affiche extends AppCompatActivity {

    RecyclerView rvaffiche;
    SearchView searchView;
    DatabaseHelper db;
    FloatingActionButton fabAjouter;
    ImageButton btnDeconnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche);

        rvaffiche = findViewById(R.id.rv_affiche);
        searchView = findViewById(R.id.searchView);
        fabAjouter = findViewById(R.id.fabAjouter);
        btnDeconnexion = findViewById(R.id.btnDeconnexion);
        db = new DatabaseHelper(this);

        // ✅ Bouton + → ouvre Ajout
        fabAjouter.setOnClickListener(v -> {
            startActivity(new Intent(Affiche.this, Ajout.class));
        });

        // ✅ Bouton Déconnexion → retour à MainActivity
        btnDeconnexion.setOnClickListener(v -> {
            startActivity(new Intent(Affiche.this, MainActivity.class));
            finish();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                afficherContacts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                afficherContacts(newText);
                return true;
            }
        });
    }

    private void afficherContacts(String query) {
        List<Contact> contacts;
        if (query == null || query.trim().isEmpty()) {
            contacts = db.getAllContacts();
        } else {
            contacts = db.rechercherContacts(query.trim());
        }

        MyRecyclerContactAdapter adapter = new MyRecyclerContactAdapter(
                Affiche.this,
                new ArrayList<>(contacts)
        );

        rvaffiche.setAdapter(adapter);
        rvaffiche.setLayoutManager(
                new GridLayoutManager(Affiche.this, 2, GridLayoutManager.VERTICAL, false)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        afficherContacts(searchView.getQuery().toString());
    }
}