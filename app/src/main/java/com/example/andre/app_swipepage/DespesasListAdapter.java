package com.example.andre.app_swipepage;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class DespesasListAdapter extends ArrayAdapter<Despesa>{

    private Context mContext;
    private int mResource;
    private Page3Fragment FragInstace;

    public DespesasListAdapter(@NonNull Context _context, @LayoutRes int _resource, @NonNull ArrayList<Despesa> objects, Page3Fragment _fragInst) {
        super(_context, _resource, objects);
        mContext = _context;
        mResource = _resource;
        FragInstace = _fragInst;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String nome = getItem(position).getDesignacao();
        String preco = getItem(position).getPreco();
        final String _data = getItem(position).getData();

        Despesa nDespesa = new Despesa(nome, preco, _data);

        LayoutInflater infla = LayoutInflater.from(mContext);
        convertView = infla.inflate(mResource, parent, false);

        TextView tvNome = (TextView) convertView.findViewById(R.id.textViewDesignacao);
        TextView tvPreco = (TextView) convertView.findViewById(R.id.textViewPreco);
        TextView tvData = (TextView) convertView.findViewById(R.id.textViewData);

        tvNome.setText(nome);
        tvPreco.setText(preco + "€");
        tvData.setText(_data);

        ImageView ApagarSelec = (ImageView) convertView.findViewById(R.id.imageViewApagar);

        ApagarSelec.setTag(position);

        ApagarSelec.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                final int _position=(Integer)v.getTag();

                AlertDialog.Builder mDialog = new AlertDialog.Builder(getContext());
                View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_erase, null);
                Button btnsim = (Button) mView.findViewById(R.id.buttonSim);
                Button btnnao = (Button) mView.findViewById(R.id.buttonNao);

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

                        ArrayList<Despesa> ApagarDaqui = new ArrayList<Despesa>();
                        ApagarDaqui = OperacoesEmFicheiros.CarregarLista();

                        ApagarDaqui.remove(_position);

                        File _folder = new File(Environment.getExternalStorageDirectory(), "Despin");

                        File fTxt = new File(_folder, "Despesas.txt");

                        PrintWriter writer = null;
                        try {
                            writer = new PrintWriter(fTxt);

                            Despesa[] aux2 = ApagarDaqui.toArray(new Despesa[ApagarDaqui.size()]);

                            for(int i=0; i<ApagarDaqui.size(); i++){
                                writer.println(aux2[i].designacao + "#" + aux2[i].preco + "#" + aux2[i].dataD);
                            }

                            writer.close();

                            FragInstace.ReloadFrag();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        _dialog.dismiss();

                    }
                });

                _dialog.show();

            }
        });

        return convertView;
    }
}
