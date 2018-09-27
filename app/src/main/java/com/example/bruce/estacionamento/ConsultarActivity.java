package com.example.bruce.estacionamento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import DAO.CarroDAO;
import bin.Carro;

public class ConsultarActivity extends AppCompatActivity {
    private ListView listaCarros;
    private ImageButton consultar;
    private String placa;
    private EditText campoConsulta;
    //oi
    private String g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        listaCarros = findViewById(R.id.lista_consultar);
        consultar = findViewById(R.id.ibConsultar);
        campoConsulta = findViewById(R.id.edtConsulta);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placa = campoConsulta.getText().toString();
                consultaCarros();
            }
        });
    }

    public void consultaCarros(){

        final CarroDAO dao = new CarroDAO(ConsultarActivity.this);
        List<Carro> carros = dao.consultar(placa);
        dao.close();

        ListaCarrosAdapter adpt = new ListaCarrosAdapter(carros, ConsultarActivity.this);
        listaCarros.setAdapter(adpt);
    }

    @Override
    public void onResume(){
        super.onResume();
    }




}
