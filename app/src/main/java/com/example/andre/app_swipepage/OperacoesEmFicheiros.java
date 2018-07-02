package com.example.andre.app_swipepage;

import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class OperacoesEmFicheiros {

    public static boolean GuardarNovo(String conteudo){

        File _folder = new File(Environment.getExternalStorageDirectory(), "Despin");

        if (!_folder.exists()) {
            _folder.mkdirs();
        }

        File fTxt = new File(_folder, "Despesas.txt");

        BufferedWriter bW;

        try {
            bW = new BufferedWriter(new FileWriter(fTxt, true));
            bW.write(conteudo);
            bW.newLine();
            bW.flush();
            bW.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static ArrayList<Despesa> CarregarLista(){

        ArrayList<Despesa> listaCarregada = new ArrayList<Despesa>();

        File _folder = new File(Environment.getExternalStorageDirectory(), "Despin");

        File _file = new File(_folder, "Despesas.txt");

        if (_file.exists()) {

            try {

                BufferedReader b = new BufferedReader(new FileReader(_file));

                String readLine = "";

                while ((readLine = b.readLine()) != null) {
                    String[] parts = readLine.split("#");
                    Despesa busca = new Despesa(parts[0], parts[1], parts[2]);
                    listaCarregada.add(busca);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            return null;
        }

        return OrdenarArrayPorData(listaCarregada);
    }

    public static ArrayList<Despesa> OrdenarArrayPorData(ArrayList<Despesa> ListaAEnviar){

        boolean flag = true;

        Despesa[] AuxArray = ListaAEnviar.toArray(new Despesa[ListaAEnviar.size()]);

        while(flag){

            flag = false;

            for(int i=0; i< ListaAEnviar.size() - 1; i++){

                String[] parts1 = AuxArray[i].dataD.split(" ");
                String[] parts2 = AuxArray[i+1].dataD.split(" ");

                int dia1 = Integer.parseInt(parts1[0]), ano1 = Integer.parseInt(parts1[4]), dia2 = Integer.parseInt(parts2[0]), ano2 = Integer.parseInt(parts2[4]);
                int mes1 = GetMES(parts1[2]), mes2 = GetMES(parts2[2]);

                if(MudarPosicao(dia1, mes1, ano1, dia2, mes2, ano2)){
                    Despesa aux = AuxArray[i];
                    AuxArray[i] = AuxArray[i + 1];
                    AuxArray[i + 1] = aux;

                    flag = true;
                }
            }

        }

        ListaAEnviar = new ArrayList<>(Arrays.asList(AuxArray));

        return ListaAEnviar;
    }

    public static int GetMES(String _mes){

        switch(_mes){
            case "janeiro" : return 1;
            case "fevereiro" : return 2;
            case "março" : return 3;
            case "abril" : return 4;
            case "maio" : return 5;
            case "junho" : return 6;
            case "julho" : return 7;
            case "agosto" : return 8;
            case "setembro" : return 9;
            case "outubro" : return 10;
            case "novembro" : return 11;
            case "dezembro" : return 12;
            default: return 0;
        }
    }

    public static String GetMesS(int _mes){

        switch(_mes){
            case 1: return "janeiro";
            case 2: return "fevereiro";
            case 3: return "março";
            case 4: return "abril";
            case 5: return "maio";
            case 6: return "junho";
            case 7: return "julho";
            case 8: return "agosto";
            case 9: return "setembro";
            case 10: return "outubro";
            case 11: return "novembro";
            case 12: return "dezembro";
            default: return "erro";
        }

    }

    private static boolean MudarPosicao(int dia1, int mes1, int ano1, int dia2, int mes2, int ano2){

        boolean flag = false;

        if(Integer.compare(ano1, ano2) == 1){
            flag = true;
        }

        if(Integer.compare(ano1, ano2) == 0 && Integer.compare(mes1, mes2) == 1){
            flag = true;
        }

        if(Integer.compare(ano1, ano2) == 0 && Integer.compare(mes1, mes2) == 0 && Integer.compare(dia1, dia2) == 1) {
            flag = true;
        }

        return flag;

    }

    public static void GuardarDataInicio(String _Data){

        File _folder = new File(Environment.getExternalStorageDirectory(), "Despin");

        if (!_folder.exists()) {
            _folder.mkdirs();
        }

        File fTxt = new File(_folder, "DataInicio.txt");

        BufferedWriter bW;

        try {
            bW = new BufferedWriter(new FileWriter(fTxt));
            bW.write(_Data);
            bW.newLine();
            bW.flush();
            bW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String BuscarDataInicio(){

        ArrayList<Despesa> listaCarregada = new ArrayList<Despesa>();

        File _folder = new File(Environment.getExternalStorageDirectory(), "Despin");

        File _file = new File(_folder, "DataInicio.txt");

        if (_file.exists()) {

            try {
                BufferedReader b = new BufferedReader(new FileReader(_file));

                String readLine = "";

                while ((readLine = b.readLine()) != null) {
                    return readLine;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            return null;
        }

        return null;
    }

}
