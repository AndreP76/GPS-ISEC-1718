package com.gps.g13.expensestracker;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.ListaNomesCategoriasDespesas;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;

import org.w3c.dom.Text;

public class InfoDetalhada extends AppCompatActivity {

    private GestorDados gestDados;
    private String categoria;
    private boolean isRendimento;
    private TextView tvCategoria;
    private TextView tvSubTitulo;
    private TextView tvRodape;


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
                tvSubTitulo.setText("Orcamento: " + gestDados.getCategoriaDespesas(categoria).getResumoDeTransacoes()+"€");
                tvCategoria.setText(gestDados.getCategoriaDespesas(categoria).getNome());
            } catch (InvalidCategoryException e) {
                e.printStackTrace();
            }
        }else{
            tvCategoria.setText("Rendimentos");
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

        return super.onOptionsItemSelected(item);

    }

    class lvAdapter extends BaseAdapter{
        private int possicao;

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
            possicao=i;
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
                        try {

                            Intent intent = new Intent(this,inserirActivity.class);
                            intent.putExtras("GD",gestDados);
                            intent.putExtra("TR",gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(possicao));


                        } catch (InvalidCategoryException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Intent intent = new Intent(this,inserirActivity.class);
                        intent.putExtras("GD",gestDados);
                        intent.putExtra("TR",gestDados.getCategoriaRendimento().getListaDeTransacoes().get(possicao));

                    }


                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isRendimento) {
                        try {
                        gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().remove(possicao);
                            gestDados.removeTransacao(gestDados.getCategoriaDespesas(categoria).getNome(),gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i).getNome());


                        } catch (InvalidCategoryException e) {
                            e.printStackTrace();
                        }
                    }

                    else{
                        gestDados.getCategoriaRendimento().getListaDeTransacoes().remove(possicao);
                        gestDados.removeTransacao(gestDados.getCategoriaRendimento().getNome(),gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i).getNome());
                    }

                }
            });


            return linha;
        }
    }
}
