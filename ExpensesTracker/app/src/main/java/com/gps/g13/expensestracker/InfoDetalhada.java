package com.gps.g13.expensestracker;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.ListaNomesCategoriasDespesas;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidAmmountException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidTransactionException;

import org.w3c.dom.Text;

public class InfoDetalhada extends AppCompatActivity {

    private GestorDados gestDados;
    private String categoria;
    private boolean isRendimento;
    private TextView tvCategoria;
    private TextView tvSubTitulo;
    private TextView tvRodape;

    //para dialogBox
    final Context context = this;

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detalhada);
        tvCategoria=(TextView)findViewById(R.id.tv_nomeCategoria_infoDetalhada);
        tvSubTitulo=(TextView)findViewById(R.id.tv_orcamento_InfoDetalhada);
        tvRodape=(TextView)findViewById(R.id.tv_DinheiroRestante_infoDetalhada);

        Bundle extras =getIntent().getExtras();

        gestDados= (GestorDados) extras.getSerializable("GESTAO");
        isRendimento=extras.getBoolean("TIPO");
        if (!isRendimento){
            categoria=extras.getString("CATEGORIA");

            try {
                tvCategoria.setText(gestDados.getCategoriaDespesas(categoria).getNome());
                tvSubTitulo.setText("Orcamento: " + "falta metodo para pedir orcamento" + "€");
                tvRodape.setText("Orcamento Restante " + gestDados.getCategoriaDespesas(categoria).getResumoDeTransacoes());

            } catch (InvalidCategoryException e) {
                e.printStackTrace();
            }
        }else{

            MenuItem item = (MenuItem) findViewById(R.id.EditaOrcamento);
            item.setVisible(false);
            tvCategoria.setText("Rendimentos");
            tvRodape.setText("Dinheiro Total: " + "falta metodo para pedir dinheiro total" +"€");
            tvRodape.setText("Orcamento disponível: " + gestDados.getCategoriaRendimento().getResumoDeTransacoes());
        }

        lv =(ListView)findViewById(R.id.lista_de_transacoes);
        lv.setAdapter(new lvAdapter());
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.informacao_detalhada_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO : TODOS REVER/RESCREVER ESTA PARTE
        //ver o que lancar para criar transacao;
        //atencao que temos criar e editar
        if (item.getItemId()==R.id.criaTransacao) {
            Intent intent = new Intent(this,InserirActivity.class);         //// nao sei o que por para
            intent.putExtra("GD",gestDados);                                ///// lançar a atividade
            //intent.putExtra("TR"); << para criar nova transação n é usado //   para criar uma nova transacao
            startActivity(intent);

        }else if(item.getItemId()==R.id.EditaOrcamento) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            // set title
            alertDialogBuilder.setTitle("Orçamento");

            final EditText input = new EditText(InfoDetalhada.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialogBuilder.setView(input); // uncomment this line

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            try {
                                gestDados.editarOrcamento(categoria,Double.parseDouble(input.getText().toString()));
                            } catch (InvalidCategoryException e) {
                                e.printStackTrace();
                            } catch (InvalidAmmountException e) {
                                e.printStackTrace();
                            }
                        }
                    })
            ;

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    class lvAdapter extends BaseAdapter{
        private int posicao;

        @Override
        public int getCount() {
            if(!isRendimento) {
                try {
                    gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().size();
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
            else{
                gestDados.getCategoriaRendimento().getListaDeTransacoes().size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if(!isRendimento) {
                try {
                    gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i);
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            else{
                gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View linha =getLayoutInflater().inflate(R.layout.linha_da_lista_de_transacoes,null);
            posicao=i;
            TextView tv_nomeTransacao= (TextView) findViewById(R.id.tv_nomeCategoria_infoDetalhada);
            TextView tvValorTransacao= (TextView) findViewById(R.id.tv_ValorTransacao_InfoGeral);
            TextView tvDataTransacao= (TextView) findViewById(R.id.tv_DataTransacao_InfoGeral);
            Button btnRemove = (Button) findViewById(R.id.btn_delete_linha);
            Button btnEdite = (Button) findViewById(R.id.btn_edit_linha);

            if(!isRendimento) {
                try {
                    tv_nomeTransacao.setText(gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i).getNome());
                    tvValorTransacao.setText(""+gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i).getMontante());
                    tvDataTransacao.setText("" + gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i).getData());
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            else{
                tv_nomeTransacao.setText(gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i).getNome());
                tvValorTransacao.setText(""+gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i).getMontante());
                tvDataTransacao.setText("" + gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i).getData());
            }

            btnEdite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isRendimento) {
                        /*try {
                        //TODO : NUNO E BRUNO VERIFIQUEM ESTES INTENTS
                            Intent intent = new Intent(this,InserirActivity.class);
                            intent.putExtra("GD",gestDados);
                            intent.putExtra("TR",gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(posicao));
                            startActivity(intent);
                        } catch (InvalidCategoryException e) {
                            e.printStackTrace();
                        }*/
                    }
                    else{
                        /*Intent intent = new Intent(this,InserirActivity.class);
                        intent.putExtra("GD",gestDados);
                        intent.putExtra("TR",gestDados.getCategoriaRendimento().getListaDeTransacoes().get(posicao));                            startActivity(intent);
                        startActivity(intent);*/
                    }
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle("Tem a certeza que pretende apagar?");


                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    if(!isRendimento) {
                                        try {
                                            gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().remove(posicao);
                                            gestDados.removeTransacao(gestDados.getCategoriaDespesas(categoria).getNome(),gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(posicao).getNome());
                                        } catch (InvalidCategoryException e) {
                                            e.printStackTrace();
                                        } catch (InvalidTransactionException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else{
                                        gestDados.getCategoriaRendimento().getListaDeTransacoes().remove(posicao);
                                        try {
                                            gestDados.removeTransacao(gestDados.getCategoriaRendimento().getNome(),gestDados.getCategoriaRendimento().getListaDeTransacoes().get(posicao).getNome());
                                        } catch (InvalidCategoryException e) {
                                            e.printStackTrace();
                                        } catch (InvalidTransactionException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            })
                            .setPositiveButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            });
            return linha;
        }
    }
}
