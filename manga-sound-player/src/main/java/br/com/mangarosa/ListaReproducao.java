package br.com.mangarosa;

import br.com.mangarosa.collections.ListaEncadeada;

public class ListaReproducao {
    private String titulo;
    private ListaEncadeada lista;

    public ListaReproducao(String titulo) {
        this.titulo = titulo;
        this.lista = new ListaEncadeada();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isVazia() {
        return lista.isEmpty();
    }
}
