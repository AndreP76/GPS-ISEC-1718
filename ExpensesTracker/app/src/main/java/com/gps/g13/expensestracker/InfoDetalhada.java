package com.gps.g13.expensestracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import com.gps.g13.expensestracker.gestaodedados.Categoria;
import com.gps.g13.expensestracker.gestaodedados.CategoriaDespesas;
import com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento;
import com.gps.g13.expensestracker.gestaodedados.Dados;
import com.gps.g13.expensestracker.gestaodedados.GestorDados;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidAmmountException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidCategoryException;
import com.gps.g13.expensestracker.gestaodedados.exceptions.InvalidTransactionException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InfoDetalhada extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //para dialogBox
    final Context context = this;
    private GestorDados gestDados;
    private String categoria;
    private boolean isRendimento;
    private TextView tvCategoria;
    private TextView tvSubTitulo;
    private TextView tvRodape;
    private ListView lv;
    private lvAdapter lva;

    @Override
    protected void onPause() {
        super.onPause();
        ExpensesTracker.getGestorDadosGlobal(this).guardaDados();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExpensesTracker.getGestorDadosGlobal(this).guardaDados();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detalhada);
        tvCategoria = (TextView) findViewById(R.id.tv_nomeCategoria_infoDetalhada);
        tvSubTitulo = (TextView) findViewById(R.id.tv_orcamento_InfoDetalhada);
        tvRodape = (TextView) findViewById(R.id.tv_DinheiroRestante_infoDetalhada);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();

        gestDados = ExpensesTracker.getGestorDadosGlobal(this);//(GestorDados) extras.getSerializable("GESTAO");
        isRendimento = extras.getBoolean("TIPO");
        if (!isRendimento) {
            categoria = extras.getString("CATEGORIA");

            try {
                tvCategoria.setText(gestDados.getCategoriaDespesas(categoria).getNome());
                tvSubTitulo.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                        getResources().getString(R.string.orcamento),
                        ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamento(),
                        getResources().getString(R.string.unidade_monetaria)));
                tvRodape.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                        getResources().getString(R.string.orcamento_restante),
                        ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamentoRestante(),
                        getResources().getString(R.string.unidade_monetaria)));

            } catch (InvalidCategoryException e) {
                e.printStackTrace();
            }
        } else {
            categoria = Dados.RENDIMENTOS_KEY;
            tvCategoria.setText(gestDados.getCategoriaRendimento().getNome());
            tvSubTitulo.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                    getResources().getString(R.string.dinheiro_total),
                    gestDados.getCategoriaRendimento().getResumoDeTransacoes(),
                    getResources().getString(R.string.unidade_monetaria)));

            double totalOrcamentos = 0.0;
            List<Categoria> categorias = gestDados.getCategorias();
            for (Categoria c : categorias) {
                if(!c.getNome().equalsIgnoreCase(CategoriaRendimento.NOME_RENDIMENTO)) {
                    totalOrcamentos += ((CategoriaDespesas)c).getOrcamento();
                }
            }
            tvRodape.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                    getResources().getString(R.string.orcamento_disponivel),
                    (gestDados.getCategoriaRendimento().getResumoDeTransacoes()-totalOrcamentos),
                    getResources().getString(R.string.unidade_monetaria)));
        }


        lv = (ListView) findViewById(R.id.lista_de_transacoes);
        lva = new lvAdapter();
        lv.setAdapter(lva);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lva.notifyDataSetChanged();
        if (!isRendimento) {
            try {
                tvRodape.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                        getResources().getString(R.string.orcamento_restante),
                        ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamentoRestante(),
                        getResources().getString(R.string.unidade_monetaria)));
            } catch (InvalidCategoryException e) {
                e.printStackTrace();
            }
        }
        else
        {
            tvSubTitulo.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                    getResources().getString(R.string.dinheiro_total),
                    gestDados.getCategoriaRendimento().getResumoDeTransacoes(),
                    getResources().getString(R.string.unidade_monetaria)));
            double totalOrcamentos = 0.0;
            List<Categoria> categorias = gestDados.getCategorias();
            for (Categoria c : categorias) {
                if(!c.getNome().equalsIgnoreCase(CategoriaRendimento.NOME_RENDIMENTO)) {
                    totalOrcamentos += ((CategoriaDespesas)c).getOrcamento();
                }
            }
            tvRodape.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                    getResources().getString(R.string.orcamento_disponivel),
                    (gestDados.getCategoriaRendimento().getResumoDeTransacoes()-totalOrcamentos),
                    getResources().getString(R.string.unidade_monetaria)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = new MenuInflater(this);
        mi.inflate(R.menu.informacao_detalhada_menu, menu);

        //MenuItem item = (MenuItem) findViewById(R.id.EditaOrcamento);
        if (isRendimento) {
            invalidateOptionsMenu();
            MenuItem item = menu.findItem(R.id.EditaOrcamento);
            item.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

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
            double orcamento = -1.0;
            try {
                orcamento = ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamento();
            } catch (InvalidCategoryException e) {
                e.printStackTrace();
            }
            input.setText(Double.toString(orcamento));    // mostrar o valor do orcamento atual

            alertDialogBuilder.setView(input); // uncomment this line

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                gestDados.editarOrcamento(categoria, Double.parseDouble(input.getText().toString()));
                                tvSubTitulo.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                                        getResources().getString(R.string.orcamento),
                                        ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamento(),
                                        getResources().getString(R.string.unidade_monetaria)));
                                tvRodape.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                                        getResources().getString(R.string.orcamento_restante),
                                        ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamentoRestante(),
                                        getResources().getString(R.string.unidade_monetaria)));

                            } catch (InvalidCategoryException e) {
                                e.printStackTrace();
                            } catch (InvalidAmmountException | NumberFormatException e) {
                                Toast.makeText(context, "Isso não é um número válído", Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent(this, InfoDetalhada.class);
        intent.putExtra("GESTAO", gestDados);
        if (id == R.id.nav_rendimentos) {
            intent.putExtra("TIPO", true);


        } else if (id == R.id.nav_alimentacao) {
            intent.putExtra("TIPO", false);
            intent.putExtra("CATEGORIA", "Alimentação");

        } else if (id == R.id.nav_alojamento) {
            intent.putExtra("TIPO", false);
            intent.putExtra("CATEGORIA", "Alojamento");

        } else if (id == R.id.nav_transportes) {
            intent.putExtra("TIPO", false);
            intent.putExtra("CATEGORIA", "Transportes");

        } else if (id == R.id.nav_universidade) {
            intent.putExtra("TIPO", false);
            intent.putExtra("CATEGORIA", "Universidade");

        } else if (id == R.id.nav_lazer) {
            intent.putExtra("TIPO", false);
            intent.putExtra("CATEGORIA", "Lazer");

        } else if (id == R.id.nav_outros) {
            intent.putExtra("TIPO", false);
            intent.putExtra("CATEGORIA", "Outros");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class lvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (!isRendimento) {
                try {
                    return gestDados.getCategoriaDespesas(categoria).getListaDeTransacoes().size();
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return 0;
                }
            } else {
                return gestDados.getCategoriaRendimento().getListaDeTransacoes().size();
            }
        }

        @Override
        public Object getItem(int i) {
            if (!isRendimento) {
                try {
                    return gestDados.getCategoriaDespesas(categoria).getTransacao(i);
                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return gestDados.getCategoriaRendimento().getTransacao(i);
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View linha = getLayoutInflater().inflate(R.layout.linha_da_lista_de_transacoes, null);
            TextView tv_nomeTransacao = ((TextView) linha.findViewById(R.id.tv_nomeTransacao_InfoGeral));
            TextView tvValorTransacao = (TextView) linha.findViewById(R.id.tv_ValorTransacao_InfoGeral);
            TextView tvDataTransacao = (TextView) linha.findViewById(R.id.tv_DataTransacao_InfoGeral);
            Button btnRemove = (Button) linha.findViewById(R.id.btn_delete_linha);
            Button btnEdite = (Button) linha.findViewById(R.id.btn_edit_linha);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dAux; //so para simplicar o codigo, poderia ser evitada esta variavel
            String str;

            if (!isRendimento) {
                try {
                    tv_nomeTransacao.setText(gestDados.getCategoriaDespesas(categoria).getTransacao(i).getNome());
                    str = String.format(Locale.getDefault(), "%.2f%s",
                            gestDados.getCategoriaDespesas(categoria).getTransacao(i).getMontante(),
                            getResources().getString(R.string.unidade_monetaria));
                    tvValorTransacao.setText(str);
                    dAux = gestDados.getCategoriaDespesas(categoria).getTransacao(i).getData();
                    tvDataTransacao.setText("" + df.format(dAux));

                } catch (InvalidCategoryException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                try {
                    tv_nomeTransacao.setText(gestDados.getCategoriaRendimento().getTransacao(i).getNome());
                    str = String.format(Locale.getDefault(), "%.2f%s",
                            gestDados.getCategoriaRendimento().getTransacao(i).getMontante(),
                            getResources().getString(R.string.unidade_monetaria));
                    tvValorTransacao.setText(str);
                    dAux = gestDados.getCategoriaRendimento().getTransacao(i).getData();
                    tvDataTransacao.setText("" + df.format(dAux));
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }

            btnEdite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InserirActivity.class);

                    //intent.putExtra("GD", gestDados);
                    if (!isRendimento) {
                        try {
                            intent.putExtra("TR", gestDados.getCategoriaDespesas(categoria).getTransacao(i));
                        } catch (InvalidCategoryException e) {
                            e.printStackTrace();
                        }
                    } else {
                        intent.putExtra("TR", gestDados.getCategoriaRendimento().getTransacao(i));
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
                                            gestDados.removeTransacao(gestDados.getCategoriaDespesas(categoria).getNome(), gestDados.getCategoriaDespesas(categoria).getTransacao(i).getNome());
                                            try {
                                                tvRodape.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                                                        getResources().getString(R.string.orcamento_restante),
                                                        ((CategoriaDespesas) gestDados.getCategoriaDespesas(categoria)).getOrcamentoRestante(),
                                                        getResources().getString(R.string.unidade_monetaria)));
                                            } catch (InvalidCategoryException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            gestDados.removeTransacao(gestDados.getCategoriaRendimento().getNome(), gestDados.getCategoriaRendimento().getTransacao(i).getNome());
                                            tvSubTitulo.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                                                    getResources().getString(R.string.dinheiro_total),
                                                    gestDados.getCategoriaRendimento().getResumoDeTransacoes(),
                                                    getResources().getString(R.string.unidade_monetaria)));
                                            double totalOrcamentos = 0.0;
                                            List<Categoria> categorias = gestDados.getCategorias();
                                            for (Categoria c : categorias) {
                                                if(!c.getNome().equalsIgnoreCase(CategoriaRendimento.NOME_RENDIMENTO)) {
                                                    totalOrcamentos += ((CategoriaDespesas)c).getOrcamento();
                                                }
                                            }
                                            tvRodape.setText(String.format(Locale.getDefault(), "%s %.2f%s",
                                                    getResources().getString(R.string.orcamento_disponivel),
                                                    (gestDados.getCategoriaRendimento().getResumoDeTransacoes()-totalOrcamentos),
                                                    getResources().getString(R.string.unidade_monetaria)));
                                        }
                                        notifyDataSetChanged();
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
