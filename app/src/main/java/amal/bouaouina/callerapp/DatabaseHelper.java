package amal.bouaouina.callerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "contacts.db";
    private static final int DB_VERSION = 3;

    // Table contact
    public static final String TABLE_CONTACT = "contact";
    public static final String COL_ID = "id";
    public static final String COL_NOM = "nom";
    public static final String COL_PRENOM = "prenom";
    public static final String COL_NUMERO = "numero";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_CONTACT + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOM + " TEXT NOT NULL, " +
                    COL_PRENOM + " TEXT, " +
                    COL_NUMERO + " TEXT NOT NULL);";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        // Sécurité : on supprime aussi l'ancienne table "contacts" si elle traîne
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    /** Ajouter un contact */
    public long ajouterContact(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOM, c.nom);
        values.put(COL_PRENOM, c.pseudo);
        values.put(COL_NUMERO, c.numero);
        long id = db.insert(TABLE_CONTACT, null, values);
        db.close();
        return id;
    }

    /** Récupérer tous les contacts */
    public List<Contact> getAllContacts() {
        List<Contact> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_CONTACT + " ORDER BY " + COL_NOM + " ASC",
                null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String nom = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOM));
                String prenom = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRENOM));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow(COL_NUMERO));
                liste.add(new Contact(id, nom, prenom, numero));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return liste;
    }

    /** Rechercher contacts (nom ou prénom) */
    public List<Contact> rechercherContacts(String query) {
        List<Contact> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_NOM + " LIKE ? OR " + COL_PRENOM + " LIKE ?";
        String[] args = new String[]{"%" + query + "%", "%" + query + "%"};
        Cursor cursor = db.query(TABLE_CONTACT, null, selection, args, null, null, COL_NOM + " ASC");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String nom = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOM));
                String prenom = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRENOM));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow(COL_NUMERO));
                liste.add(new Contact(id, nom, prenom, numero));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return liste;
    }

    /** Supprimer un contact */
    public int supprimerContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(TABLE_CONTACT, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return res;
    }

    /** Modifier un contact */
    public int modifierContact(Contact c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOM, c.nom);
        values.put(COL_PRENOM, c.pseudo);
        values.put(COL_NUMERO, c.numero);
        int res = db.update(TABLE_CONTACT, values, COL_ID + " = ?", new String[]{String.valueOf(c.id)});
        db.close();
        return res;
    }
}