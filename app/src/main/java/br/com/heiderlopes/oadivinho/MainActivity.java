package br.com.heiderlopes.oadivinho;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final String ESTADO_ULTIMO_CHUTE_JOGADOR = "ultimoChuteJogador";
    static final String ESTADO_CHUTE_MAQUINA = "chuteMaquina";

    private TextView tvUltimoChute;
    private EditText etNumeroChute;

    private int chuteMaquina;
    private int chuteJogador;

    private int[] sonsErro = {R.raw.chavez_ai_que_burro_da_zero, R.raw.errou_faustao, R.raw.errou_rude, R.raw.que_pena_voce_errou};
    private int[] sonsAcertou = {R.raw.you_win};

    private Random geradorNumero = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUltimoChute = (TextView) findViewById(R.id.tvUltimoChute);
        etNumeroChute = (EditText) findViewById(R.id.etNumeroChute);


        if (savedInstanceState != null) {
            chuteMaquina = savedInstanceState.getInt(ESTADO_CHUTE_MAQUINA);
            chuteJogador = savedInstanceState.getInt(ESTADO_ULTIMO_CHUTE_JOGADOR);

            tvUltimoChute.setText(String.valueOf(chuteJogador));
        } else {
            chuteMaquina = geradorNumero.nextInt(11);
        }

    }

    public void chutar(View v) {
        String auxNumeroChute = etNumeroChute.getText().toString();
        if(auxNumeroChute.isEmpty()) {
            Toast.makeText(this, "Informe seu chute", Toast.LENGTH_SHORT).show();
        } else {
            chuteJogador = Integer.parseInt(auxNumeroChute);
            tvUltimoChute.setText(etNumeroChute.getText().toString());
            if(chuteJogador == chuteMaquina) {
                showAlert("Você ganhou");
                chuteMaquina = geradorNumero.nextInt(11);
                executarSomAcertou();
            } else if( chuteJogador < chuteMaquina) {
                showAlert("O valor é maior que o informado");
                executarSomErrou();
            } else {
                showAlert("O valor é menor que o informado");
                executarSomErrou();
            }
        }
    }

    private void showAlert(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("O Adivinho");
        builder.setMessage(mensagem)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        etNumeroChute.setText("");
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void executarSomAcertou() {
        int indice = geradorNumero.nextInt(sonsAcertou.length);
        executarSom(sonsAcertou[indice]);
    }

    private void executarSomErrou() {
        int indice = geradorNumero.nextInt(sonsErro.length);
        executarSom(sonsErro[indice]);
    }

    private void executarSom(int resourceID) {
        MediaPlayer player = new MediaPlayer().create(this, resourceID);
        player.start();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(ESTADO_ULTIMO_CHUTE_JOGADOR, chuteJogador);
        savedInstanceState.putInt(ESTADO_CHUTE_MAQUINA, chuteMaquina);
        super.onSaveInstanceState(savedInstanceState);
    }
}
