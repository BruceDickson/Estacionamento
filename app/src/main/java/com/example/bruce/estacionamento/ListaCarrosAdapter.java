package com.example.bruce.estacionamento;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import bin.Carro;

public class ListaCarrosAdapter extends BaseAdapter {
    private final List<Carro> carros;
    private final Activity activity;

    public ListaCarrosAdapter(List<Carro> carros, Activity activity) {
        this.carros = carros;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return carros.size();
    }

    @Override
    public Object getItem(int position) {
        return carros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return carros.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = activity.getLayoutInflater().inflate(R.layout.item_carro, null);
        Carro carro = carros.get(position);
        TextView txtDono = v.findViewById(R.id.txtDono1);
        TextView txtModelo = v.findViewById(R.id.txtModelo1);
        ImageView imgFoto = v.findViewById(R.id.imgFotoLista);
        TextView txtData = v.findViewById(R.id.txtData);
        Bitmap bm = null;
        //TextView txtplaca = v.findViewById(R.id.txtPlaca1);
        //TextView txtTelefone = v.findViewById(R.id.txtTelefone1);

        if(carro.getEstacionado() == 1) v.setBackgroundColor(activity.getResources().getColor(R.color.carro_estacionado));
        else v.setBackgroundColor(activity.getResources().getColor(R.color.carro_desestacionado));

        txtDono.setText(carro.getDono());
        txtModelo.setText(carro.getModelo() + " - " + carro.getPlaca());

        txtData.setText(carro.getData());


        /*SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
        String str = fmt.format(carro.getData());
        txtData.setText(str);*/

        if(carro.getCaminhoFoto() != null) {
            bm = BitmapFactory.decodeFile(carro.getCaminhoFoto());
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bm, 100, 100, true);
            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
            imgFoto.setImageBitmap(rotatedBitmap);
        }

        return v;
    }
}
