package com.example.eventos.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.eventos.R;
import com.example.eventos.model.Convidado;
import com.example.eventos.model.Convite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


public class AdapterListConvidados extends ArrayAdapter<Convidado> implements ListAdapter {

    private Context context;
    private int layoutResource;
    private List<Convidado> convidados;

    public AdapterListConvidados(@NonNull Context context, int resource, @NonNull List<Convidado> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.convidados = objects;
    }

    public class ViewHolder {
        TextView nome;
        ProgressBar loading;
    }

    @Override
    public int getCount() {
        return convidados.size();
    }

    @Override
    public Convidado getItem(int pos) {
        return convidados.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Convidado convidado = getItem(position);
        final ViewHolder viewHolder;
        //Case the view not is inflated, we need inflate this.
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder.nome = convertView.findViewById(R.id.txtNomeConvidado);
            viewHolder.loading = convertView.findViewById(R.id.progressConvidado);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DocumentReference convitesRef;
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        convitesRef = database.collection("convidado").document(convidado.getIdConvidado());
        convitesRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Convidado convidado = task.getResult().toObject(Convidado.class);
                            convidados.get(position).setNome(convidado.getNome());
                            convidados.get(position).setEmail(convidado.getEmail());
                            convidados.get(position).setTelefone(convidado.getTelefone());
                            viewHolder.nome.setText(convidado.getNome());
                            viewHolder.loading.setVisibility(View.GONE);
                            viewHolder.nome.setVisibility(View.VISIBLE);
                            notifyDataSetChanged();
                        }
                    }
                });
        return convertView;
    }

}

