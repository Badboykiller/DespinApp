package com.example.andre.app_swipepage;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.*;

import java.util.GregorianCalendar;

import static junit.framework.Assert.assertEquals;

public class PageFragment extends Fragment {

    TextView mtextViewDias;
    TextView mDataInicio;

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.page_fragment_layout, container, false);

        mDataInicio = (TextView)view.findViewById(R.id.textViewDataInicio);
        mtextViewDias = (TextView)view.findViewById(R.id.textViewContagemDias);

        VerificarDataInicio();
        ContarDias();

        ImageView img_novaData = (ImageView) view.findViewById(R.id.imageViewChangeData);

        img_novaData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mDialog = new AlertDialog.Builder(getContext());
                View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_mudar_data, null);
                Button btnsim = (Button) mView.findViewById(R.id.buttonSim);
                Button btnnao = (Button) mView.findViewById(R.id.buttonNao);
                final EditText etnovadata = (EditText) mView.findViewById(R.id.editTextNovadata);

                PorAData(etnovadata);

                final String CurrentData = String.valueOf(etnovadata.getText());

                mDialog.setView(mView);
                final AlertDialog _dialog = mDialog.create();

                btnnao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _dialog.dismiss();
                    }
                });

                btnsim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlterarData(etnovadata, CurrentData);
                        _dialog.dismiss();
                    }

                });

                _dialog.show();
            }
        });

        return view;
    }

    public void ContarDias(){

        String DataInicio = null;
        DataInicio = OperacoesEmFicheiros.BuscarDataInicio();

        int dia2 = 0;
        int mes2 = 0;
        int ano2 = 0;

        if(DataInicio != null){

            String[] parts = DataInicio.split(" ");

            dia2 = Integer.parseInt(parts[0]);
            mes2 = OperacoesEmFicheiros.GetMES(parts[2]);
            ano2 = Integer.parseInt(parts[4]);

        }

        Calendar now = Calendar.getInstance();
        int ano = now.get(Calendar.YEAR);
        int mes = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int dia = now.get(Calendar.DAY_OF_MONTH);

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal1.set(ano2, mes2, dia2);
        cal2.set(ano, mes, dia);
        mtextViewDias.setText((daysBetween(cal1.getTime(),cal2.getTime()) + 1)  + " dias");

    }

    public int daysBetween(Date d1, Date d2) {
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public void PorAData(EditText etSelec){

        String DataInicio = null;
        DataInicio = OperacoesEmFicheiros.BuscarDataInicio();

        if(DataInicio != null){

            String[] parts = DataInicio.split(" ");

            int mes = OperacoesEmFicheiros.GetMES(parts[2]);

            etSelec.setText(parts[0] + "/" + String.valueOf(mes) + "/" + parts[4]);

        }else{

            etSelec.setText("Erro...");

        }
    }

    public void VerificarDataInicio(){

        File _folder = new File(Environment.getExternalStorageDirectory(), "Despin");

        if (!_folder.exists()) {
            _folder.mkdirs();
        }

        File fTxt = new File(_folder, "DataInicio.txt");

        if(!fTxt.exists()){
            OperacoesEmFicheiros.GuardarDataInicio("10 de setembro de 2017");
        }

        mDataInicio.setText(OperacoesEmFicheiros.BuscarDataInicio());

    }

    public void AlterarData(EditText etnovadata, String currentData){

        String novaData = String.valueOf(etnovadata.getText());

        if(!novaData.equals(currentData)){
            String[] parts = novaData.split("/");

            if(parts.length == 3){

                int _dia = Integer.parseInt(parts[0]);
                String _mes = OperacoesEmFicheiros.GetMesS(Integer.parseInt(parts[1]));
                int _ano = Integer.parseInt(parts[2]);

                if(((int) Math.log10(_ano) + 1) > 3){

                    String novaDataS = String.valueOf(_dia) + " de " + _mes + " de " + String.valueOf(_ano);

                    OperacoesEmFicheiros.GuardarDataInicio(novaDataS);
                    VerificarDataInicio();
                    ContarDias();
                }

            }
        }
    }

}
