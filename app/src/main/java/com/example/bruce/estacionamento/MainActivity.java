package com.example.bruce.estacionamento;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import DAO.CarroDAO;
import bin.Carro;

public class MainActivity extends AppCompatActivity {
    private ListView listaCarros;
    private ArrayAdapter<Carro> adapter;
    private Carro carroSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarroDAO dao = new CarroDAO(MainActivity.this);

        listaCarros = (ListView) findViewById(R.id.lista_carros);
        registerForContextMenu(listaCarros);

        listaCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent edicao = new Intent(MainActivity.this, FormularioActivity.class);
                edicao.putExtra("carroSelecionado", (Carro) listaCarros.getItemAtPosition(position));
                startActivity(edicao);
            }
        });

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
        MenuItem retirar = menu.add("Retirar Carro");
        MenuItem detalhes = menu.add("Detalhes");

        retirar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Retirar carro do estacionamento")
                        .setMessage("Deseja mesmo retirar")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CarroDAO dao = new CarroDAO(MainActivity.this);
                                dao.retirarCarroDoEstacionamento(carroSelecionado);
                                dao.close();
                                carregaListaEstacionados();
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_novo:
                Intent intent = new Intent(MainActivity.this,FormularioActivity.class);
                startActivity(intent);
                return false;
            case R.id.menu_listar:
                Intent listar = new Intent(MainActivity.this, ListarTodosActivity.class);
                startActivity(listar);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void carregaListaEstacionados(){

        final CarroDAO dao = new CarroDAO(MainActivity.this);
        List<Carro> carros = dao.getListEstacionados();
        dao.close();

        ListaCarrosAdapter adpt = new ListaCarrosAdapter(carros, MainActivity.this);
        listaCarros.setAdapter(adpt);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.carregaListaEstacionados();
    }
}
