package com.gps.g13.expensestracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InserirActivity extends AppCompatActivity {

    private EditText transacaoNome;
    private EditText transacaoValor;
    private EditText transacaoDia;
    private TextView transacaoMes;
    private TextView transacaoAno;
    private Button btnOK;

    private TextView tipo;
    private TextView categoria;


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

        //transacaoValor.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(99,2)});

        transacaoDia = (EditText) findViewById(R.id.et_InserirTransacao_data_dia);
        transacaoMes = (TextView) findViewById(R.id.tv_InserirTransacao_data_mes);
        transacaoAno= (TextView) findViewById(R.id.tv_InserirTransacao_data_ano);;
        btnOK=(Button)findViewById(R.id.btn_InserirTransacao_ok);


        tipo =(TextView) findViewById(R.id.tv_inserir_tipo);
        categoria=(TextView) findViewById(R.id.tv_inserir_Categoria);

        Bundle extras=getIntent().getExtras();

        if(extras.get("TIPO").equals("Rendimento")){
            tipo.setText(R.string.NovoRendimento);
            categoria.setVisibility(View.GONE);
        }else{
            tipo.setText(R.string.NovaDespesa);
            categoria.setText((String)extras.get("CATEGORIA"));
        }


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


            //Ve se o dia esta correto
            if(dia>0&& dia<=cal.get(Calendar.DAY_OF_MONTH)){


                //ve se o valor € é correto
                char[] x=transacaoValor.getText().toString().toCharArray();
                int count=0;
                boolean ok =false;

                for(int i =0; i<x.length;i++){
                    if(ok){
                        count++;
                    }

                    if(x[i]=='.'){
                        ok=true;
                    }
                }
                if(count>2){
                    Toast.makeText(this, "Valor invalido" , Toast.LENGTH_LONG).show();
                    return;
                }




                //sai
               // finish();



            }else{
                Toast.makeText(this, "Data invalida" , Toast.LENGTH_LONG).show();

            }

        }catch (Exception e){
            Toast.makeText(this, "Dados invalidos" , Toast.LENGTH_LONG).show();
            Log.i("test",e.getMessage());
        }

    }



    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
            mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }

    }
}
