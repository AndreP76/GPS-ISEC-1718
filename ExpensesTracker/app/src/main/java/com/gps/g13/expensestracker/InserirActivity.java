package com.gps.g13.expensestracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Date;
import java.util.SimpleTimeZone;

public class InserirActivity extends AppCompatActivity {

    private EditText transacaoNome;
    private EditText transacaoValor;
    private EditText transacaoDia;
    private TextView transacaoMes;
    private TextView transacaoAno;
    private Button btnOK;
    Date date;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);
        //Remove actionBar
        try{
            getSupportActionBar().hide();

        }catch (Exception e){
            Log.i("inserirNova",e.getMessage());
        }

        transacaoNome =(EditText) findViewById(R.id.et_InserirTransacao_nome);
        transacaoValor =(EditText) findViewById(R.id.et_InserirTransacao_valor);
        transacaoDia = (EditText) findViewById(R.id.et_InserirTransacao_data_dia);
        transacaoMes = (TextView) findViewById(R.id.tv_InserirTransacao_data_mes);
        transacaoAno= (TextView) findViewById(R.id.tv_InserirTransacao_data_ano);;
        btnOK=(Button)findViewById(R.id.btn_InserirTransacao_ok);



        //get data actual e por nas textViews correspondentes

        date= new Date();
        cal = Calendar.getInstance();
        cal.setTime(date);
        int mes=cal.get(Calendar.MONTH);
        mes++;//mes vem de 0 a 11, logo o mes++

        transacaoMes.setText(mes+"");
        transacaoAno.setText(cal.get(Calendar.YEAR)+"");
    }







    public void onClickOK(View v){
        int dia;

        try {
            dia= Integer.parseInt(transacaoDia.getText().toString());
            if(dia<1|| dia<=cal.get(Calendar.DAY_OF_MONTH)){

                //guarda dados


                //sai
                finish();

            }else{
                Toast.makeText(this, "Data invalida" , Toast.LENGTH_LONG).show();

            }

        }catch (Exception e){
            Toast.makeText(this, "Data invalida" , Toast.LENGTH_LONG).show();

        }

    }
}
