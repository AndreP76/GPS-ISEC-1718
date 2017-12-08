package com.gps.g13.expensestracker;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.Toast;

import com.gps.g13.expensestracker.gestaodedados.CategoriaDespesas;
import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.ListaNomesCategoriasDespesas;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidAmmountException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidTransactionException;

import org.w3c.dom.Text;

public class InfoDetalhada extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        tvCategoria = (TextView) findViewById(R.id.tv_nomeCategoria_infoDetalhada);
        tvSubTitulo = (TextView) findViewById(R.id.tv_orcamento_InfoDetalhada);
        tvRodape = (TextView) findViewById(R.id.tv_DinheiroRestante_infoDetalhada);

        Bundle extras = getIntent().getExtras();

        gestDados = (GestorDados) extras.getSerializable("GESTAO");
        isRendimento = extras.getBoolean("TIPO");
        if (!isRendimento) {
            categoria = extras.getString("CATEGORIA");

            try {
                tvCategoria.setText(gestDados.getCategoriaDespesas(categoria).getNome());
                tvSubTitulo.setText(getResources().getString(R.string.orcamento) + ((CategoriaDespesas)gestDados.getCategoriaDespesas(categoria)).getOrcamento() + getResources().getString(R.string.unidade_monetaria));
                tvRodape.setText(String.format("%s%s", getResources().getString(R.string.orcamento_restante), gestDados.getCategoriaDespesas(categoria).getResumoDeTransacoes()));

            } catch (InvalidCategoryException e) {
                e.printStackTrace();
            }
        } else {
            tvCategoria.setText(gestDados.getCategoriaRendimento().getNome());
            tvRodape.setText(getResources().getString(R.string.dinheiro_total) + gestDados.getCategoriaRendimento().getResumoDeTransacoes() + getResources().getString(R.string.unidade_monetaria));
            tvRodape.setText(String.format("%s%s", getResources().getString(R.string.orcamento_disponivel), gestDados.getCategoriaRendimento().getResumoDeTransacoes()));
        }

        lv = (ListView) findViewById(R.id.lista_de_transacoes);
        lv.setAdapter(new lvAdapter());
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent(this, InfoDetalhada.class);
        intent.putExtra("GESTAO",gestDados);
        if (id == R.id.nav_rendimentos) {
            intent.putExtra("TIPO",true);


        } else if (id == R.id.nav_alimentacao) {
            intent.putExtra("TIPO",false);
            intent.putExtra("CATEGORIA","Alimentação");

        } else if (id == R.id.nav_alojamento) {
            intent.putExtra("TIPO",false);
            intent.putExtra("CATEGORIA","Alojamento");

        } else if (id == R.id.nav_transportes) {
            intent.putExtra("TIPO",false);
            intent.putExtra("CATEGORIA","Transportes");

        } else if (id == R.id.nav_universidade) {
            intent.putExtra("TIPO",false);
            intent.putExtra("CATEGORIA","Universidade");

        } else if (id == R.id.nav_lazer) {
            intent.putExtra("TIPO",false);
            intent.putExtra("CATEGORIA","Lazer");

        } else if (id == R.id.nav_outros) {
            intent.putExtra("TIPO",false);
            intent.putExtra("CATEGORIA","Outros");
        }

        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.informacao_detalhada_menu,menu);

        //MenuItem item = (MenuItem) findViewById(R.id.EditaOrcamento);
        if (isRendimento)
        {
            invalidateOptionsMenu();
            MenuItem item = menu.findItem(R.id.EditaOrcamento);
            item.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO : TODOS REVER/RESCREVER ESTA PARTE
        //ver o que lancar para criar transacao;
        //atencao que temos criar e editar
        if (item.getItemId() == R.id.criaTransacao) {
            Intent intent = new Intent(this, InserirActivity.class);
            intent.putExtra("GD", gestDados);
            intent.putExtra("CATEGORIA", categoria);
            startActivity(intent);
        } else if (item.getItemId() == R.id.EditaOrcamento) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            // set title
            alertDialogBuilder.setTitle(getResources().getString(R.string.orcamento));

            final EditText input = new EditText(InfoDetalhada.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            Double orcamento = -1.0;
            try {
                orcamento = ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamento();
            } catch (InvalidCategoryException e) {
                e.printStackTrace();
            }
            input.setText(String.format("%s", orcamento));    // mostrar o valor do orcamento atual

            alertDialogBuilder.setView(input); // uncomment this line

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                gestDados.editarOrcamento(categoria, Double.parseDouble(input.getText().toString()));
                                tvSubTitulo.setText(getResources().getString(R.string.orcamento) + ((CategoriaDespesas)gestDados.getCategoriaDespesas(categoria)).getOrcamento() + getResources().getString(R.string.unidade_monetaria));
                            } catch (InvalidCategoryException e) {
                                e.printStackTrace();
                            } catch (InvalidAmmountException | NumberFormatException e) {
                                Toast.makeText(context, "Isso não é um numero valído", Toast.LENGTH_SHORT).show();
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


    class lvAdapter extends BaseAdapter {
        private int posicao;

        @Override
        public int getCount() {
            if (!isRendimento) {
                try {
                    gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().size();
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return 0;
                }
            } else {
                gestDados.getCategoriaRendimento().getListaDeTransacoes().size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (!isRendimento) {
                try {
                    gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i);
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
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
            posicao = i;
            View linha = getLayoutInflater().inflate(R.layout.linha_da_lista_de_transacoes, null);
            TextView tv_nomeTransacao = (TextView) findViewById(R.id.tv_nomeCategoria_infoDetalhada);
            TextView tvValorTransacao = (TextView) findViewById(R.id.tv_ValorTransacao_InfoGeral);
            TextView tvDataTransacao = (TextView) findViewById(R.id.tv_DataTransacao_InfoGeral);
            Button btnRemove = (Button) findViewById(R.id.btn_delete_linha);
            Button btnEdite = (Button) findViewById(R.id.btn_edit_linha);

            if (!isRendimento) {
                try {
                    tv_nomeTransacao.setText(gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i).getNome());
                    tvValorTransacao.setText("" + gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i).getMontante());
                    tvDataTransacao.setText("" + gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(i).getData());
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                tv_nomeTransacao.setText(gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i).getNome());
                tvValorTransacao.setText("" + gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i).getMontante());
                tvDataTransacao.setText("" + gestDados.getCategoriaRendimento().getListaDeTransacoes().get(i).getData());
            }

            btnEdite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InserirActivity.class);

                    intent.putExtra("GD", gestDados);
                    if (!isRendimento) {
                        try {
                            //TODO : NUNO E BRUNO VERIFIQUEM ESTES INTENTS

                            intent.putExtra("TR", gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(posicao));
                        } catch (InvalidCategoryException e) {
                            e.printStackTrace();
                        }
                    } else {
                        intent.putExtra("TR", gestDados.getCategoriaRendimento().getListaDeTransacoes().get(posicao));
                    }
                    startActivity(intent);
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
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        if (!isRendimento) {
                                            gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().remove(posicao);
                                            gestDados.removeTransacao(gestDados.getCategoriaDespesas(categoria).getNome(), gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().get(posicao).getNome());
                                        } else {
                                            gestDados.getCategoriaRendimento().getListaDeTransacoes().remove(posicao);
                                            gestDados.removeTransacao(gestDados.getCategoriaRendimento().getNome(), gestDados.getCategoriaRendimento().getListaDeTransacoes().get(posicao).getNome());
                                        }
                                    } catch (InvalidCategoryException e) {
                                        e.printStackTrace();
                                    } catch (InvalidTransactionException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
