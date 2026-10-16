package amal.bouaouina.callerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyRecyclerContactAdapter extends RecyclerView.Adapter<MyRecyclerContactAdapter.MyViewHolder> {
    Context con;
    ArrayList<Contact> data;

    public MyRecyclerContactAdapter(Context con, ArrayList<Contact> data) {
        this.con = con;
        this.data = data;
    }

    public MyRecyclerContactAdapter(Affiche con, List<HashMap<String, String>> data, int viewContact, String[] from, int[] to) {
    }

    @NonNull
    @Override
    public MyRecyclerContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creer un viewHolder
        LayoutInflater inf=LayoutInflater.from(con);
        View v=inf.inflate(R.layout.view_contact,null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerContactAdapter.MyViewHolder holder, int position) {
//Affectation des Holders
        Contact c= data.get(position);
        holder.tvnom.setText(c.nom);
        holder.tvpseudo.setText(c.pseudo);
        holder.tvnumero.setText(c.numero);
        holder.tvnumero.setText(c.numero);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvnom ,tvpseudo,tvnumero;
        ImageView imgcall,imgDelete,imgEdit;
        public MyViewHolder(@NonNull View v) {
            super(v);
            //Récupertation des holers
            tvnom = v.findViewById(R.id.textNom);
            tvpseudo = v.findViewById(R.id.prenom);
            tvnumero = v.findViewById(R.id.textNumero);
            imgcall = v.findViewById(R.id.btnAppeler);
            imgDelete = v.findViewById(R.id.btnSupprimer);
            imgEdit = v.findViewById(R.id.btnModifier);

            imgcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedIndex=getAdapterPosition();
                    Contact selectedContact = data.get(selectedIndex);
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel" + selectedContact.numero));
                    con.startActivity(i);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedIndex = getAdapterPosition();
                    if (selectedIndex == RecyclerView.NO_POSITION) return;

                    Contact selectedContact = data.get(selectedIndex);

                    AlertDialog.Builder alert = new AlertDialog.Builder(con);
                    alert.setTitle("Suppression");
                    alert.setMessage("Confirmer la suppression de " + selectedContact.nom + " ?");

                    alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ✅ 1. Supprimer de la base SQLite
                            DatabaseHelper db = new DatabaseHelper(con);
                            db.supprimerContact(selectedContact.id);

                            // ✅ 2. Supprimer de la liste en mémoire
                            int pos = getAdapterPosition();
                            if (pos != RecyclerView.NO_POSITION) {
                                data.remove(pos);
                                notifyItemRemoved(pos);
                            }
                        }
                    });

                    alert.setNegativeButton("Annuler", null);
                    alert.show();
                }
            });
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos == RecyclerView.NO_POSITION) return;

                    Contact c = data.get(pos);

                    Intent i = new Intent(con, EditContact.class);
                    i.putExtra("id", c.id);
                    i.putExtra("nom", c.nom);
                    i.putExtra("pseudo", c.pseudo);
                    i.putExtra("numero", c.numero);
                    con.startActivity(i);
                }
            });
        }
    }
}
