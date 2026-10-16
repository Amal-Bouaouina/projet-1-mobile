package amal.bouaouina.callerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MyContactAdapter extends BaseAdapter {
    Context con;
    ArrayList<Contact> data;

    MyContactAdapter(Context con, ArrayList<Contact> data){
       this.con=con;
       this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //creer un viewHolder
        LayoutInflater inf=LayoutInflater.from(con);
        View v=inf.inflate(R.layout.view_contact,null);

        //Récupertation des holers
        TextView tvnom=v.findViewById(R.id.textNom);
        TextView tvpseudo=v.findViewById(R.id.prenom);
        TextView tvnumero=v.findViewById(R.id.textNumero);
        ImageView imgcall=v.findViewById(R.id.btnAppeler);
        ImageView imgDelete=v.findViewById(R.id.btnSupprimer);
        ImageView imgEdit=v.findViewById(R.id.btnModifier);

        //Affectation des Holders
        Contact c= data.get(position);
        tvnom.setText(c.nom);
        tvpseudo.setText(c.pseudo);
        tvnumero.setText(c.numero);
        tvnumero.setText(c.numero);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Faire : supprimer depuis la base sqlite
                AlertDialog.Builder alert=new AlertDialog.Builder(con);
                alert.setTitle("Suppression");
                alert.setMessage("confimer la suppression");
                alert.setPositiveButton("confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("non",null);
                alert.setNeutralButton("annuler",null);
                alert.show();

            }
        });
        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent();
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel"+c.numero));
                con.startActivity(i);
            }
        });
        return v;
    }
}
