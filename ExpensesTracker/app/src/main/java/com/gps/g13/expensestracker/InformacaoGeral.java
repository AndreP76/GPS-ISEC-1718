package com.gps.g13.expensestracker;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.gps.g13.expensestracker.gestaodedados.Categoria;
import com.gps.g13.expensestracker.gestaodedados.CategoriaRendimento;
import com.gps.g13.expensestracker.gestaodedados.GestorDados;

import java.util.List;

public class InformacaoGeral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvOrcamentoTotal;
    private TextView tvDinheiroGasto;
    private TextView tvBalanco;
    private GestorDados gestorDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_geral);

        gestorDados = new GestorDados();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvOrcamentoTotal = (TextView) findViewById(R.id.tv_orcamentoTotal);
        tvDinheiroGasto = (TextView) findViewById(R.id.tv_dinheiroGasto);
        tvBalanco = (TextView) findViewById(R.id.balanco);
    }


    @Override
    protected void onResume() {
        super.onResume();

        double orcamento = gestorDados.getCategoriaRendimento().getResumoDeTransacoes();
        double dinheiroGasto = 0.0;
        double balanco;

        List<Categoria> categorias = gestorDados.getCategorias();
        for (Categoria c : categorias) {
            if(!c.getNome().equalsIgnoreCase(CategoriaRendimento.NOME_RENDIMENTO)) {
                dinheiroGasto += c.getResumoDeTransacoes();
            }
        }
        dinheiroGasto = -dinheiroGasto;

        balanco = orcamento - dinheiroGasto;

        tvOrcamentoTotal.setText(getResources().getString(R.string.orcamentototal) + orcamento + getResources().getString(R.string.unidade_monetaria));
        tvDinheiroGasto.setText(getResources().getString(R.string.dinheiroGasto) + dinheiroGasto + getResources().getString(R.string.unidade_monetaria));
        tvBalanco.setText(getResources().getString(R.string.balanco) + balanco + getResources().getString(R.string.unidade_monetaria));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.informacao_geral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent(this, InformacaoDetalhada.class);
        intent.putExtra("GESTAO",gestorDados);
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


}
