package com.example.andre.app_swipepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Page3Fragment extends ListFragment {

    ListView listaDespesas;
    DespesasListAdapter AdaptadorDesp;
    TextView totalmoney;

    public Page3Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View _view = inflater.inflate(R.layout.page_fragment_layout3, container, false);

        listaDespesas = (ListView) _view.findViewById(android.R.id.list);

        totalmoney = (TextView) _view.findViewById(R.id.textViewtotaldespesas);

        ArrayList<Despesa> ListaDesp = new ArrayList<Despesa>();
        ListaDesp = OperacoesEmFicheiros.CarregarLista();

        if(ListaDesp == null){

            File _folder = new File(Environment.getExternalStorageDirectory(), "Despin");

            if (!_folder.exists()) {
                _folder.mkdirs();
            }

            File fTxt = new File(_folder, "Despesas.txt");

            try {
                fTxt.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ListaDesp = OperacoesEmFicheiros.CarregarLista();

        float toto = 0;

        Despesa[] aux = ListaDesp.toArray(new Despesa[ListaDesp.size()]);

        for (int i = 0; i < ListaDesp.size(); i++) {
            toto = toto + Float.parseFloat(aux[i].preco);
        }

        AdaptadorDesp = new DespesasListAdapter(getActivity(), R.layout.adapter_view_layout, ListaDesp, this);
        listaDespesas.setAdapter(AdaptadorDesp);

        totalmoney.setText(String.format("%.2f", toto) + "€");

        return _view;

    }

    public void RefreshesView(){

        ArrayList<Despesa> ListaDesp2 = new ArrayList<Despesa>();
        ListaDesp2 = OperacoesEmFicheiros.CarregarLista();

        AdaptadorDesp.clear();
        AdaptadorDesp.addAll(ListaDesp2);
        AdaptadorDesp.notifyDataSetChanged();

        Despesa[] aux2 = ListaDesp2.toArray(new Despesa[ListaDesp2.size()]);
        float toto2 = 0;

        for (int i = 0; i < ListaDesp2.size(); i++) {
            toto2 = toto2 + Float.parseFloat(aux2[i].preco);
        }

        totalmoney.setText(String.format("%.2f", toto2) + "€");
    }

    public void ReloadFrag() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

}
