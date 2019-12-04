package com.example.eventos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventos.R;
import com.example.eventos.activity.CadastroConvidadoActivity;
import com.example.eventos.activity.CadastroEventoActivity;
import com.example.eventos.activity.CadastroUsuarioActivity;
import com.example.eventos.activity.ListaConvidadosActivity;
import com.example.eventos.model.Evento;

import java.util.List;


public class AdapterListEventos extends ArrayAdapter<Evento> implements ListAdapter {

    private Context context;
    private int layoutResource;
    private List<Evento> eventos;

    public AdapterListEventos(@NonNull Context context, int resource, @NonNull List<Evento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.eventos = objects;
    }

    public class ViewHolder {
        TextView nome, data, btnCarregarConvidadosEvento;
        ImageView btnMenuEvento;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Evento getItem(int pos) {
        return eventos.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Evento evento = getItem(position);
        final ViewHolder viewHolder;
        //Case the view not is inflated, we need inflate this.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder.nome = convertView.findViewById(R.id.txtEventoNome);
            viewHolder.data = convertView.findViewById(R.id.txtEventoData);
            viewHolder.btnMenuEvento = convertView.findViewById(R.id.btnMenuEvento);
            viewHolder.btnCarregarConvidadosEvento = convertView.findViewById(R.id.btnCarregarConvidadosEvento);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nome.setText(evento.getNomeEvento());
        viewHolder.data.setText(evento.getData());
        viewHolder.btnCarregarConvidadosEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListaConvidadosActivity.class);
                intent.putExtra("evento", evento);
                getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });
        try {
            viewHolder.btnMenuEvento.setImageResource(R.drawable.ic_more_vert_black_24dp);
            viewHolder.btnMenuEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.btnMenuEvento:

                            PopupMenu popup = new PopupMenu(getContext(), view);
                            popup.getMenuInflater().inflate(R.menu.evento_menu_crud,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.menu_evento_editar:

                                            Intent i = new Intent(getContext(), CadastroEventoActivity.class);
                                            i.putExtra("evento", evento);
                                            context.startActivity(i);
                                            notifyDataSetChanged();
                                            break;
                                        case R.id.menu_evento_deletar:
                                            evento.deletar();
                                            notifyDataSetChanged();
                                            break;
                                        default:
                                            break;
                                    }
                                    return true;
                                }
                            });

                            break;

                        default:
                            break;
                    }
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }
        return convertView;
    }

}

