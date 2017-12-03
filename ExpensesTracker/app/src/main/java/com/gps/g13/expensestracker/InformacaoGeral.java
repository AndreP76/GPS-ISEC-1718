package com.gps.g13.expensestracker;

import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class InformacaoGeral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView orcamentoTotal;
    private TextView dinheiroGasto;
    private TextView balanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_geral);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        orcamentoTotal = (TextView) findViewById(R.id.tv_orcamentoTotal);
        dinheiroGasto = (TextView) findViewById(R.id.tv_dinheiroGasto);
        balanco = (TextView) findViewById(R.id.balanco);
    }


    @Override
    protected void onResume() {
        super.onResume();

        orcamentoTotal.setText(R.string.orcamentoTotal + 30 +R.string.unidadeMonetaria);
        dinheiroGasto.setText(R.string.dinheiroGasto + 20 + R.string.unidadeMonetaria);
        balanco.setText(R.string.balanco + 10 + R.string.unidadeMonetaria);
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

        if (id == R.id.nav_rendimentos) {
            Toast.makeText(this, "TODO: Rendimentos", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_alimentacao) {
            Toast.makeText(this, "TODO: Alimentação", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_alojamento) {
            Toast.makeText(this, "TODO: Alojamento", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_transportes) {
            Toast.makeText(this, "TODO: Transportes", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_universidade) {
            Toast.makeText(this, "TODO: Universidade", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_lazer) {
            Toast.makeText(this, "TODO: Lazer", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_outros) {
            Toast.makeText(this, "TODO: Outros", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
