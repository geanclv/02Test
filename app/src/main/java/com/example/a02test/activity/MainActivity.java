package com.example.a02test.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a02test.R;
import com.example.a02test.adapter.FrutaAdapter;
import com.example.a02test.model.Fruta;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private ListView listView;
    private GridView gridView;

    //menus
    private MenuItem itemGridView;
    private MenuItem itemListView;

    private FrutaAdapter frutaAdapterList;
    private FrutaAdapter frutaAdapterGrid;

    private List<Fruta> lstFruta;

    private int contador;
    private final int SWITCH_TO_LIST_VIEW = 0;
    private final int SWITCH_TO_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConmponents();
        initActionBarIcon();
        iniListFrutas();

        //Método para el clic de cada item
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        //Iniciando los adaptadores
        this.frutaAdapterList = new FrutaAdapter(this, R.layout.list_item, lstFruta);
        this.frutaAdapterGrid = new FrutaAdapter(this, R.layout.grid_item, lstFruta);

        //Seteando los adaptadores
        this.listView.setAdapter(frutaAdapterList);
        this.gridView.setAdapter(frutaAdapterGrid);

        //Registrando los menus contextuales
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);
    }

    //Acciones al clicear en los items
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.clicEnFruta(lstFruta.get(position));
    }

    private void clicEnFruta(Fruta fruta) {
        //Diferenciamos entre frutas conocidas y desconocidas
        if (fruta.getOrigen().equals("Unknown"))
            Toast.makeText(this, "Perdón, no hay información de " + fruta.getNombre(),
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "La mejor fruta de " + fruta.getOrigen() + " es: "
                    + fruta.getNombre(), Toast.LENGTH_LONG).show();
    }

    //Menu de la barra de acciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflamos el optionsMenu con nuestro menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        //Luego de inflar obtenemos las respectivas referencias de los items
        itemListView = menu.findItem(R.id.itemListView);
        itemGridView = menu.findItem(R.id.itemGridView);
        return true;
    }

    //Creando acciones para el menu de la barra de acciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAgregar:
                this.agregarFruta(new Fruta("Agregada nro " + (++contador), "Unknown",
                        R.drawable.ic_fruta_32));
                return true;
            case R.id.itemListView:
                this.cambiarVista(this.SWITCH_TO_LIST_VIEW);
                return true;
            case R.id.itemGridView:
                this.cambiarVista(this.SWITCH_TO_GRID_VIEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Método para cambiar entre las vistas: List/Grid View
    private void cambiarVista (int opcion) {
        if(opcion == SWITCH_TO_LIST_VIEW){
            //Si queremos cambiar a list view y está invisible
            if(this.listView.getVisibility() == View.INVISIBLE){
                //Escondemos el grid view y mostramos el botón de grid view
                this.gridView.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);
                //Mostramos el list view y escondemos el botón de grid view
                this.listView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }
        } else if(opcion == SWITCH_TO_GRID_VIEW) {
            //Si queremos cambiar a grid view y está invisible
            if(this.gridView.getVisibility() == View.INVISIBLE){
                //Escondemos el list view y mostramos el botón de list view
                this.listView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true);
                //Mostramos el grid view y escondemos el botón de grid view
                this.gridView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        }
    }

    private void iniListFrutas() {
        lstFruta = new ArrayList<Fruta>() {{
            for (int i = 0; i < 4; i++) {
                add(new Fruta("Nombre " + i,"Origen " + i, R.drawable.ic_blueberry_32));
            }
        }};
    }

    private void agregarFruta(Fruta fruta) {
        this.lstFruta.add(fruta);
        //Notificando el cambio a los Adapters
        this.frutaAdapterList.notifyDataSetChanged();
        this.frutaAdapterGrid.notifyDataSetChanged();
    }

    private void eliminarFruta(int position) {
        this.lstFruta.remove(position);
        //Notificando el cambio a los Adapters
        this.frutaAdapterList.notifyDataSetChanged();
        this.frutaAdapterGrid.notifyDataSetChanged();
    }

    //Creando e inflando el menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Inflamos el context menu con nuestro layout
        MenuInflater inflater = getMenuInflater();
        //Antes de inflar le añadimos el header dependiendo del objeto seleccionado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.lstFruta.get(info.position).getNombre());
        //Inflamos
        inflater.inflate(R.menu.context_menu, menu);
    }

    //Acciones al pinchar el menu contextual
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //Obtener info del item seleccionado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.itemEliminar:
                this.eliminarFruta(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void initActionBarIcon() {
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initConmponents() {
        listView = findViewById(R.id.listView);
        gridView = findViewById(R.id.gridView);
    }
}
