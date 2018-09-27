package com.example.bruce.estacionamento;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import DAO.CarroDAO;
import bin.Carro;
import helper.FormularioHelper;

public class FormularioActivity extends AppCompatActivity {
    private Carro carro;
    private FormularioHelper fHelper;
    private Button btnDeletar;
    private ImageView foto;
    private int TIRAR_FOTO = 123;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        foto = findViewById(R.id.imgFoto);
        fHelper = new FormularioHelper(FormularioActivity.this);
        Button button = (Button) findViewById(R.id.btnCadastrar);
        carro = (Carro) getIntent().getSerializableExtra("carroSelecionado");
        btnDeletar = findViewById(R.id.btnDeletar);

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
            btnDeletar.setVisibility(View.VISIBLE);

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

            btnDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarroDAO dao = new CarroDAO(FormularioActivity.this);
                    carro = fHelper.pegaCarroDoFormulario();
                    dao.delete(carro);
                    dao.close();
                    finish();
                }
            });
        }

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                Uri localFoto = Uri.fromFile(new File(caminhoFoto));

                Intent irCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                startActivityForResult(irCamera, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TIRAR_FOTO){
            if(resultCode == Activity.RESULT_OK){
                fHelper.carregaImagem(caminhoFoto);
            }
            else{
                this.caminhoFoto = null;
            }
        }
    }

}
