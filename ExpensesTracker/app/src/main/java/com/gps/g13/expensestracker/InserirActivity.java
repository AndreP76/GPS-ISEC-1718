package com.gps.g13.expensestracker;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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

import com.gps.g13.expensestracker.gestaodedados.Categoria;
import com.gps.g13.expensestracker.gestaodedados.Dados;
import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.Transacao;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidAmmountException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidDateException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidNameException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidTransactionException;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private String categoriaName;
    private Transacao transacaoEditar;
    private GestorDados gd;

    Date date;
    Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);
        //CODIGO GERADO AUTOMATICAMENTE PELO ANDROID STUDIO
        //INICIO
        try{  //Remover a actionbar
            ActionBar supportBar = getSupportActionBar();
            if(supportBar != null) {
                supportBar.hide();
            }
        }catch (Exception e){
            Log.i("inserirNova",e.getMessage());
        }

        //FIM

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



        transacaoEditar = (Transacao) extras.getSerializable("TR");
        gd = ExpensesTracker.getGestorDadosGlobal();/*(GestorDados)extras.getSerializable("GD");*/
        if(transacaoEditar != null) {//modo de edicao
            Calendar time = Calendar.getInstance(); //variavel aux so' para converter
            time.setTimeInMillis(transacaoEditar.getData().getTime());

            transacaoDia.setText(""+time.get(Calendar.DAY_OF_MONTH));
            transacaoAno.setText(""+time.get(Calendar.YEAR));
            transacaoMes.setText(""+time.get(Calendar.MONTH));
            transacaoNome.setText(transacaoEditar.getNome());
            transacaoValor.setText(transacaoEditar.getMontante() + "");

            categoria.setText(transacaoEditar.getCategoria().getNome());
            categoriaName = transacaoEditar.getCategoria().getNome();
            tipo.setText(R.string.editTransacao);
        }else {//modo de adição
            categoriaName = (String) extras.get("CATEGORIA");
            if (categoriaName != null && categoriaName.equals(Dados.RENDIMENTOS_KEY)) {
                tipo.setText(R.string.NovoRendimento);
                categoria.setVisibility(View.GONE);
            } else if (categoriaName != null) {
                tipo.setText(R.string.NovaDespesa);
                categoria.setText(categoriaName);
            } else {
                Log.e("[INSERIR] :: ","Received a null category");
                finish();
            }

            //meter a data atual nas textViews correspondentes
            date= new Date();
            cal = Calendar.getInstance();
            cal.setTime(date);
            int mes=cal.get(Calendar.MONTH);
            mes++;//mes vem de 0 a 11, logo o mes++

            transacaoMes.setText(mes+"");
            transacaoAno.setText(cal.get(Calendar.YEAR)+"");
        }
    }

    public void onClickOK(View v){
        Date data;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //o construtor assim é Deprecated.
            //data = new Date(Integer.parseInt(transacaoAno.getText().toString()), Integer.parseInt(transacaoMes.getText().toString()), Integer.parseInt(transacaoDia.getText().toString()));

            String dateString = Integer.parseInt(transacaoAno.getText().toString()) + "-" + Integer.parseInt(transacaoMes.getText().toString()) + "-" + Integer.parseInt(transacaoDia.getText().toString());

            //data = new Date(Integer.parseInt(transacaoAno.getText().toString()), Integer.parseInt(transacaoMes.getText().toString()), Integer.parseInt(transacaoDia.getText().toString()));
            try {
                Date dAuxiliar = f.parse(dateString);
                long milliseconds = dAuxiliar.getTime();
                data = new Date();
                data.setTime(milliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
                data = new Date();
            }

        }catch (RuntimeException rEx){
            Log.w("[INSERIR] :: ","Transaction date parsing failed. Assuming today.");
            data = new Date();
        }



        String categoria = categoriaName;
        String Nome = transacaoNome.getText().toString();
        double Montante = Double.parseDouble(transacaoValor.getText().toString());
        if(transacaoEditar != null){//modo edição
            try {
                gd.editarTransacao(categoria,transacaoEditar.getNome(),Nome,Montante,data);
            } catch (InvalidCategoryException e) {
                Log.e("[INSERIR] :: ","Edited transaction category is invalid!");
                Toast.makeText(this,"Ocorreu um erro ao editar a transação selecionada. Por favor tente novamente",Toast.LENGTH_SHORT).show();
                finish();
            } catch (InvalidTransactionException e) {
                Log.e("[INSERIR] :: ","Edited transaction is invalid!");
                Toast.makeText(this,"Ocorreu um erro ao editar a transação selecionada. Por favor tente novamente",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            try {
                gd.adicionaTransacao(categoria,Nome,Montante,data);
            } catch (InvalidNameException e) {
                Toast.makeText(this,"Este nome não é aceitavel para a transação. Mude o nome e tente novamente",Toast.LENGTH_SHORT).show();
            } catch (InvalidAmmountException e) {
                Toast.makeText(this,"Este montante não é valido. Mude o valor e tente novamente",Toast.LENGTH_SHORT).show();
            } catch (InvalidDateException e) {
                Toast.makeText(this,"Esta data não é aceitavel para a transação. Mude a data e tente novamente",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (InvalidCategoryException e) {
                Toast.makeText(this,"Erro desconhecido na adição de uma transação. Tente novamente.",Toast.LENGTH_SHORT).show();
                Log.e("[INSERIR] :: ","Programmers fix this plz. Invalid category on adding new transaction");
            }
        }
        finish();
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
