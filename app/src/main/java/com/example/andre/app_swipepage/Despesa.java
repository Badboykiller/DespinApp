package com.example.andre.app_swipepage;

public class Despesa {

    String designacao;
    String preco;
    String dataD;

    public Despesa(String _designacao, String _preco, String _data) {
        this.designacao = _designacao;
        this.preco = _preco;
        this.dataD = _data;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getData() {
        return dataD;
    }

    public void setData(String _dataD) {
        this.dataD = _dataD;
    }

}
