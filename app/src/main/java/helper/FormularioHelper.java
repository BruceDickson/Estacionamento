package helper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bruce.estacionamento.FormularioActivity;
import com.example.bruce.estacionamento.R;

import bin.Carro;

public class FormularioHelper {
    private Carro carro;
    private EditText dono;
    private EditText modelo;
    private EditText placa;
    private EditText telefone;
    private CheckBox estacionado;
    private ImageView foto;

    public FormularioHelper(FormularioActivity activity){
        this.carro = new Carro();

        this.dono = activity.findViewById(R.id.edtDono);
        this.modelo = activity.findViewById(R.id.edtModelo);
        this.placa = activity.findViewById(R.id.edtPlaca);
        this.telefone = activity.findViewById(R.id.edtTelefone);
        this.estacionado = activity.findViewById(R.id.cbEstacionar);
        this.foto = activity.findViewById(R.id.imgFoto);
    }

    public Carro pegaCarroDoFormulario(){
        carro.setDono(dono.getText().toString());
        carro.setModelo(modelo.getText().toString());
        carro.setPlaca(placa.getText().toString());
        carro.setTelefone(telefone.getText().toString());

        if(estacionado.isChecked()) carro.setEstacionado(1);
        else carro.setEstacionado(0);

        return carro;
    }

    public void colocaNoFormulario(Carro carro){
        dono.setText(carro.getDono());
        modelo.setText(carro.getModelo());
        placa.setText(carro.getPlaca());
        telefone.setText(carro.getTelefone());

        if(carro.getEstacionado() == 1) estacionado.setChecked(true);
        else estacionado.setChecked(false);

        if(carro.getCaminhoFoto() != null){
            Bitmap imagemFoto = BitmapFactory.decodeFile(carro.getCaminhoFoto());
            imagemFoto = Bitmap.createScaledBitmap(imagemFoto, 150,150,true);

            foto.setImageBitmap(imagemFoto);
        }


        this.carro = carro;

    }

    public void carregaImagem(String caminhoFoto){
        this.carro.setCaminhoFoto(caminhoFoto);

        Bitmap imagemFoto = BitmapFactory.decodeFile(caminhoFoto);
        imagemFoto = Bitmap.createScaledBitmap(imagemFoto, 150,150,true);

        foto.setImageBitmap(imagemFoto);

    }
}
