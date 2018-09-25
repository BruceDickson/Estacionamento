package helper;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;

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

    public FormularioHelper(FormularioActivity activity){
        this.carro = new Carro();

        this.dono = activity.findViewById(R.id.edtDono);
        this.modelo = activity.findViewById(R.id.edtModelo);
        this.placa = activity.findViewById(R.id.edtPlaca);
        this.telefone = activity.findViewById(R.id.edtTelefone);
        this.estacionado = activity.findViewById(R.id.cbEstacionar);
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

        this.carro = carro;

    }
}
