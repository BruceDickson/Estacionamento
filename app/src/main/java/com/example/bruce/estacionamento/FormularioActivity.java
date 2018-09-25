package com.example.bruce.estacionamento;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

import DAO.CarroDAO;
import bin.Carro;
import helper.FormularioHelper;

public class FormularioActivity extends AppCompatActivity {
    private Carro carro;
    private FormularioHelper fHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        fHelper = new FormularioHelper(FormularioActivity.this);
        Button button = (Button) findViewById(R.id.btnCadastrar);
        carro = (Carro) getIntent().getSerializableExtra("carroSelecionado");

        if(carro == null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carro = fHelper.pegaCarroDoFormulario();
                    CarroDAO dao = new CarroDAO(FormularioActivity.this);
                    dao.insere(carro);
                    dao.close();
                    finish();
                }
            });
        }else{
            button.setText("Alterar");
            fHelper.colocaNoFormulario(carro);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarroDAO dao = new CarroDAO(FormularioActivity.this);
                    carro = fHelper.pegaCarroDoFormulario();
                    dao.alterar(carro);
                    dao.close();
                    finish();
                }
            });
        }
    }
}
