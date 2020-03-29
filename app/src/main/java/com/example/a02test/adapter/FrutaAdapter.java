package com.example.a02test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a02test.R;
import com.example.a02test.model.Fruta;

import java.util.List;

public class FrutaAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Fruta> lstFruta;

    public FrutaAdapter(Context context, int layout, List<Fruta> lstFruta) {
        this.context = context;
        this.layout = layout;
        this.lstFruta = lstFruta;
    }

    @Override
    public int getCount() {
        return this.lstFruta.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lstFruta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View Holder Pattern -> nos permite mejorar la performace debido a que almacena
         * en cache las referencias*/
        ViewHolder holder;
        if(convertView == null) {
            //Inflando la vista con el layout creado (list_item)
//            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
//            convertView = layoutInflater.inflate(this.layout, null);
            //Otra forma de inflar (Lo anterior en una línea)
            convertView = LayoutInflater.from(this.context).inflate(this.layout, null);

            holder = new ViewHolder();
            //Agregando la referencia del textView al Holder
            holder.txtNombre = convertView.findViewById(R.id.txtNombre);
            holder.txtOrigen = convertView.findViewById(R.id.txtOrigen);
            holder.icono = convertView.findViewById(R.id.imageView);
            //llenando el holder
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Completando el textView del layout
        holder.txtNombre.setText(this.lstFruta.get(position).getNombre());
        holder.txtOrigen.setText(this.lstFruta.get(position).getOrigen());
        holder.icono.setImageResource(this.lstFruta.get(position).getIcono());

        //Devolvemos la Vista inflada y modificada con nuestros datos
        return convertView;
    }

    static class ViewHolder {
        private TextView txtNombre;
        private TextView txtOrigen;
        private ImageView icono;
    }
}
