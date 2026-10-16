package amal.bouaouina.callerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContact extends AppCompatActivity {

    EditText ednom, edpseudo, ednumero;
    Button btnvalider, btnannuler;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);   // réutilise le layout

        ednom = findViewById(R.id.ednom_ajout);
        edpseudo = findViewById(R.id.edpseudo_ajout);
        ednumero = findViewById(R.id.ednumero_ajout);
        btnvalider = findViewById(R.id.btnvalider_ajout);
        btnannuler = findViewById(R.id.btnannuler_ajout);

        // Récupérer le contact passé en extra
        int id = getIntent().getIntExtra("id", -1);
        String nom = getIntent().getStringExtra("nom");
        String pseudo = getIntent().getStringExtra("pseudo");
        String numero = getIntent().getStringExtra("numero");

        contact = new Contact(id, nom, pseudo, numero);

        // Pré-remplir
        ednom.setText(nom);
        edpseudo.setText(pseudo);
        ednumero.setText(numero);

        btnannuler.setOnClickListener(v -> finish());

        btnvalider.setOnClickListener(v -> {
            String n = ednom.getText().toString().trim();
            String p = edpseudo.getText().toString().trim();
            String num = ednumero.getText().toString().trim();

            if (n.isEmpty() || num.isEmpty()) {
                Toast.makeText(this, "Nom et numéro obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            contact.nom = n;
            contact.pseudo = p;
            contact.numero = num;

            DatabaseHelper db = new DatabaseHelper(this);
            db.modifierContact(contact);

            Toast.makeText(this, "Contact modifié", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}