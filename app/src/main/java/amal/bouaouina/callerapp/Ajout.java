package amal.bouaouina.callerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ajout extends AppCompatActivity {

    EditText ednom, edpseudo, ednumero;
    Button btnvalider, btnannuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        ednom = findViewById(R.id.ednom_ajout);
        edpseudo = findViewById(R.id.edpseudo_ajout);
        ednumero = findViewById(R.id.ednumero_ajout);

        btnannuler = findViewById(R.id.btnannuler_ajout);
        btnvalider = findViewById(R.id.btnvalider_ajout);

        // Bouton Annuler
        btnannuler.setOnClickListener(v -> finish());   // finish() revient à Affiche

        btnvalider.setOnClickListener(v -> {
            String nom = ednom.getText().toString().trim();
            String pseudo = edpseudo.getText().toString().trim();
            String numero = ednumero.getText().toString().trim();

            if (nom.isEmpty() || numero.isEmpty()) {
                Toast.makeText(Ajout.this, "Nom et numéro obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper db = new DatabaseHelper(Ajout.this);
            Contact c = new Contact(nom, pseudo, numero);
            db.ajouterContact(c);

            Toast.makeText(Ajout.this, "Contact ajouté", Toast.LENGTH_SHORT).show();
            finish();   // ✅ Retour à Affiche (qui se rafraîchit via onResume)
        });

        // Bouton Valider
        //btnvalider.setOnClickListener(new View.OnClickListener() {
        //  @Override
        // public void onClick(View view) {
        //  finish();
        //}
        //});
        //btnvalider.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {

        //String nom = ednom.getText().toString().trim();
        //String pseudo = edpseudo.getText().toString().trim();
        //String numero = ednumero.getText().toString().trim();

        //if (nom.isEmpty() || numero.isEmpty()) {
        //Toast.makeText(Ajout.this, "Nom et numéro obligatoires", Toast.LENGTH_SHORT).show();
        // return;
        //}

                // Sauvegarde dans SQLite
        //DatabaseHelper db = new DatabaseHelper(Ajout.this);
        //Contact c = new Contact(nom, pseudo, numero);
        //db.ajouterContact(c);

                // Mise à jour de la liste en mémoire (facultatif)
        //Accueil.data.add(c);

        //ednom.setText("");
        //edpseudo.setText("");
        //ednumero.setText("");
        //Toast.makeText(Ajout.this, "Contact ajouté", Toast.LENGTH_SHORT).show();
        //}
        //});
    }

}