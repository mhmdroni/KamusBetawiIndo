package com.example.kamusbetawiindo;

import android.os.Bundle;

import com.example.kamusbetawiindo.Adapter.KamusIndoAdapter;
import com.example.kamusbetawiindo.DB.DBHelper;
import com.example.kamusbetawiindo.Model.Kamus;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ListKamusActivity extends AppCompatActivity {
    RecyclerView rv_Kamus;
    List<Kamus> listKamus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_kamus_activity);

        rv_Kamus = (RecyclerView)findViewById(R.id.rvKamus);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rv_Kamus.setLayoutManager(llm);
        DBHelper dbHelper = new DBHelper(this);
        listKamus = dbHelper.getAllKamus();

        KamusIndoAdapter adapter = new KamusIndoAdapter(listKamus,this);
        rv_Kamus.setAdapter(adapter);

    }
}
