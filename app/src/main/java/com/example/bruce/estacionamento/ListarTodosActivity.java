package com.example.bruce.estacionamento;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import DAO.CarroDAO;
import bin.Carro;

public class ListarTodosActivity extends AppCompatActivity {
    private ListView listaCarros;
    private ArrayAdapter<Carro> adapter;
    private Carro carroSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_todos);

        listaCarros = (ListView) findViewById(R.id.lista_todos);
        registerForContextMenu(listaCarros);

        listaCarros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                carroSelecionado = (Carro) parent.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem estacionar = menu.add("Estacionar Carro");
        MenuItem detalhes = menu.add("Detalhes");

        estacionar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(ListarTodosActivity.this)
                        .setTitle("Estacionar Carro")
                        .setMessage("Deseja mesmo estacionar")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CarroDAO dao = new CarroDAO(ListarTodosActivity.this);
                                dao.estacionarCarro(carroSelecionado);
                                dao.close();
                                Toast.makeText(getApplicationContext(), "Carro Estacionado", Toast.LENGTH_SHORT).show();
                                carregaTodosCarros();
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void carregaTodosCarros(){

        final CarroDAO dao = new CarroDAO(ListarTodosActivity.this);
        List<Carro> carros = dao.getList();
        dao.close();

        ListaCarrosAdapter adpt = new ListaCarrosAdapter(carros, ListarTodosActivity.this);
        listaCarros.setAdapter(adpt);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.carregaTodosCarros();
    }

}
