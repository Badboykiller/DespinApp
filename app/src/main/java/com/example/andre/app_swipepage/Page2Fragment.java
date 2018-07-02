package com.example.andre.app_swipepage;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Page2Fragment extends Fragment {



    public Page2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_fragment_layout2, container, false);

        Button buttonDeRegistar = (Button) view.findViewById(R.id.buttonRegistar);
        final EditText ETnome = (EditText) view.findViewById(R.id.editTextNome);
        final EditText ETpreco = (EditText) view.findViewById(R.id.editTextPreco);
        final EditText ETdata = (EditText) view.findViewById(R.id.editTextData);

        //Impedir maiusculas
        ETdata.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase();
                    }
                }
        });

        buttonDeRegistar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if( ETnome.getText().toString().isEmpty()){
                    Toast toast= Toast.makeText(getActivity(),
                            "Designação necessária!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
                else if (ETpreco.getText().toString().isEmpty()){
                    Toast toast= Toast.makeText(getActivity(),
                            "Preço necessário!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }else if(ETdata.getText().toString().isEmpty()){
                    Toast toast= Toast.makeText(getActivity(),
                            "Data necessária!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }else{

                    if(OperacoesEmFicheiros.GuardarNovo(ETnome.getText() + "#" + ETpreco.getText() + "#" + ETdata.getText())){

                        Toast toast= Toast.makeText(getActivity(),
                                "Despesa registada!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                        ETnome.setText("");
                        ETpreco.setText("");
                        ETdata.setText("");

                    }else{

                        Toast toast= Toast.makeText(getActivity(),
                                "Erro ao registar!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                    }

                }

                for (Fragment fragment : getFragmentManager().getFragments())
                {
                    for (Fragment fragment1 : fragment.getFragmentManager().getFragments())
                    {
                        if (fragment1 instanceof Page3Fragment)
                        {
                            Page3Fragment mFragmentA = ((Page3Fragment) fragment1);
                            mFragmentA.RefreshesView();
                        }
                    }
                }


            }
        });


        return view;
    }



}
