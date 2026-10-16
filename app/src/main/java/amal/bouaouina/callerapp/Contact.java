package amal.bouaouina.callerapp;

public class Contact {

    int id;
    String nom;
    String pseudo;
    String numero;

    @Override
    public String toString() {
        return nom + " " + pseudo + "\n" + numero;
    }
    public Contact(int id, String nom, String pseudo, String numero) {
        this.id = id;
        this.nom = nom;
        this.pseudo = pseudo;
        this.numero = numero;
    }

    public Contact( String nom, String pseudo, String numero) {
        this.nom = nom;
        this.pseudo = pseudo;
        this.numero = numero;
    }


}