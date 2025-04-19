package br.com.mangarosa;

import br.com.mangarosa.collections.ListaEncadeada;

public class MangaController {
    private ListaEncadeada repositorioMusica;
    private ListaEncadeada listasReproducao;
    private ListaEncadeada artistas;

    public MangaController() {
/**
 * Construtor adicionado pra garantir que os atributos listasReproducao e repositorioMusica
 * (do diagrama) sejam inicializados corretamente.
 * Embora o construtor não esteja explicitamente no diagrama de classes,
 * é necessário para evitar NullPointerException e
 * permitir o funcionamento da aplicação como descrito no enunciado.
 */
        this.repositorioMusica = new ListaEncadeada();
        this.listasReproducao = new ListaEncadeada();
    }

    public void adicionarMusica(String titulo, String path, String nomeArtista){
        Musica nova = new Musica(titulo, nomeArtista, path);
        repositorioMusica.append(nova);
    }

    public void criarListaReproducao(String titulo) {
        ListaReproducao novaLista = new ListaReproducao(titulo);
        listasReproducao.append(novaLista);
    }

    public void excluirListaReproducao(String titulo) {
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao lista = (ListaReproducao) listasReproducao.get(i);
            if (lista.getTitulo().equals(titulo)) {
                listasReproducao.remove(i);
                break;
            }
        }
    }


}
