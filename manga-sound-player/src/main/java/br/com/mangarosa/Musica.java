package br.com.mangarosa;

public class Musica {
    private String titulo;
    private int duracao;
    private String path;
    private String artista;


    public Musica() {
    }

    public Musica(String titulo, String artista, String path) {
        this.titulo = titulo;
        this.artista = artista;
        this.path = path;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getPath() {
        return path;
    }

    public String getArtista() {
        return artista;
    }
}