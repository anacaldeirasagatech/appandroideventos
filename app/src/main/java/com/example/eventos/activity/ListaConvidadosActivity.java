package com.example.eventos.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventos.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaConvidadosActivity extends AppCompatActivity {
    ListView listItemView;
    FloatingActionButton fab;

    String[] listItemsValue = new String[] {
            "Android",
            "PHP",
            "Web Development",
            "Blogger",
            "SEO",
            "Photoshop",
            "Android Studio",
            "Eclipse",
            "SDK Manager",
            "AVD Manager"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_convidados);
        setTitle("Lista de Convidados");

        listItemView = (ListView)findViewById(R.id.listView);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, listItemsValue);

        listItemView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ListaConvidadosActivity.this, "Fab Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }
}
