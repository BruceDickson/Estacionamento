package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import bin.Carro;

public class CarroDAO extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String TABELA = "Carro";
    private static final String DATABASE = "Estacionamento";

    public CarroDAO(Context context){
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s = "CREATE TABLE " + TABELA + " (id INTEGER PRIMARY KEY," +
                    " modelo TEXT, placa TEXT UNIQUE NOT NULL, dono TEXT," +
                    " telefone TEXT, estacionamento INTEGER, caminhoFoto TEXT);";
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Carro carro){
        ContentValues values = new ContentValues();

        values.put("modelo", carro.getModelo());
        values.put("placa", carro.getPlaca());
        values.put("dono", carro.getDono());
        values.put("telefone", carro.getTelefone());
        values.put("estacionamento", carro.getEstacionado());
        values.put("caminhoFoto", carro.getCaminhoFoto());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public void delete(Carro carro){
        String[] args = {carro.getId() + ""};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    public List<Carro> consultar(String placa){
        List<Carro> carros = new ArrayList<Carro>();

        if(placa != "") {
            Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + " WHERE placa=?", new String[]{placa});

            while (c.moveToNext()) {
                Carro carro = new Carro();
                carro.setId(c.getLong(c.getColumnIndex("id")));
                carro.setModelo(c.getString(c.getColumnIndex("modelo")));
                carro.setPlaca(c.getString(c.getColumnIndex("placa")));
                carro.setDono(c.getString(c.getColumnIndex("dono")));
                carro.setTelefone(c.getString(c.getColumnIndex("telefone")));
                carro.setEstacionado(c.getInt(c.getColumnIndex("estacionamento")));

                carros.add(carro);
            }
            c.close();
        }
        return carros;
    }

    public void alterar(Carro carro){
        ContentValues values = new ContentValues();

        values.put("modelo", carro.getModelo());
        values.put("placa", carro.getPlaca());
        values.put("dono", carro.getDono());
        values.put("telefone", carro.getTelefone());
        values.put("estacionamento", carro.getEstacionado());
        values.put("caminhoFoto", carro.getCaminhoFoto());

        getWritableDatabase().update(TABELA, values, "id=?", new String[]{carro.getId()+""}); // ERA PRA TER TOSTRING
    }

    public List<Carro> getList(){
        List<Carro> carros = new ArrayList<Carro>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + ";", null);

        while(c.moveToNext()){
            Carro carro = new Carro();
            carro.setId(c.getLong(c.getColumnIndex("id")));
            carro.setModelo(c.getString(c.getColumnIndex("modelo")));
            carro.setPlaca(c.getString(c.getColumnIndex("placa")));
            carro.setDono(c.getString(c.getColumnIndex("dono")));
            carro.setTelefone(c.getString(c.getColumnIndex("telefone")));
            carro.setEstacionado(c.getInt(c.getColumnIndex("estacionamento")));
            carro.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            carros.add(carro);
        }
        c.close();
        return carros;
    }

    public List<Carro> getListEstacionados(){
        List<Carro> carros = new ArrayList<Carro>();

        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA + " WHERE estacionamento = 1;", null);

        while(c.moveToNext()){
            Carro carro = new Carro();
            carro.setId(c.getLong(c.getColumnIndex("id")));
            carro.setModelo(c.getString(c.getColumnIndex("modelo")));
            carro.setPlaca(c.getString(c.getColumnIndex("placa")));
            carro.setDono(c.getString(c.getColumnIndex("dono")));
            carro.setTelefone(c.getString(c.getColumnIndex("telefone")));
            carro.setEstacionado(c.getInt(c.getColumnIndex("estacionamento")));
            carro.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            carros.add(carro);
        }
        c.close();
        return carros;
    }

    public void retirarCarroDoEstacionamento(Carro carro){
        ContentValues values = new ContentValues();
        values.put("estacionamento", 0);

        getWritableDatabase().update(TABELA, values, "id=?", new String[]{carro.getId()+""}); // ERA PRA TER TOSTRING
    }

    public void estacionarCarro(Carro carro){
        ContentValues values = new ContentValues();
        values.put("estacionamento", 1);

        getWritableDatabase().update(TABELA, values, "id=?", new String[]{carro.getId()+""}); // ERA PRA TER TOSTRING
    }

}
