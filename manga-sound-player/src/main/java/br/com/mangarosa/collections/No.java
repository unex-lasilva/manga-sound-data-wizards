package br.com.mangarosa.collections;

public class No {
    private Object valor;
    private No prox;

    public No(){
    }
    public No(Object valor){
        this.valor= valor;
        this.prox = null;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}

