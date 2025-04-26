package br.com.mangarosa;

import br.com.mangarosa.collections.ListaEncadeada;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MangaController {
    private ListaEncadeada repositorioMusica;
    private ListaEncadeada listasReproducao;
    private ListaEncadeada artistas;
    private ReprodutorLista reprodutorLista = new ReprodutorLista();

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
        this.artistas = new ListaEncadeada();
    }

    public void adicionarMusica(String titulo, String path, String nomeArtista){
        try {
            File original = new File(path);
            if (!original.exists()) {
                System.out.println("Arquivo original não encontrado: " + path);
                return;
            }

            File pastaRepositorio = new File("repositorio");
            if (!pastaRepositorio.exists()) {
                pastaRepositorio.mkdir();
            }

            File destino = new File(pastaRepositorio, original.getName());
            Files.copy(original.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Musica nova = new Musica(titulo, nomeArtista, destino.getPath());
            repositorioMusica.append(nova);

            System.out.println("Música adicionada com sucesso ao repositório!");

        } catch (IOException e) {
            System.out.println("Erro ao copiar a música: " + e.getMessage());
        }
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

    public void adicionarMusicaListaReproducao(String tituloMusica, String tituloLista) {
        Musica musica = null;
        for (int i = 0; i < repositorioMusica.size(); i++) {
            Musica m = (Musica) repositorioMusica.get(i);
            if (m.getTitulo().equalsIgnoreCase(tituloMusica)) {
                musica = m;
                break;
            }
        }
        if (musica == null) {
            System.out.println("Música não encontrada no repositório.");
            return;
        }

        ListaReproducao lista = null;
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao l = (ListaReproducao) listasReproducao.get(i);
            if (l.getTitulo().equalsIgnoreCase(tituloLista)) {
                lista = l;
                break;
            }
        }
        if (lista == null) {
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }

        if (lista.contemMusica(musica)) {
            System.out.println("A música já está na lista de reprodução.");
        } else {
            lista.addMusica(musica);
            System.out.println("Música adicionada à lista de reprodução.");
        }
    }

    public void adicionarMusicaListaReproducaoEmPosicao(String tituloMusica, String tituloLista, int posicao) {
        Musica musica = null;
        for (int i = 0; i < repositorioMusica.size(); i++) {
            Musica m = (Musica) repositorioMusica.get(i);
            if (m.getTitulo().equalsIgnoreCase(tituloMusica)) {
                musica = m;
                break;
            }
        }

        if (musica == null) {
            System.out.println("Música não encontrada no repositório.");
            return;
        }

        ListaReproducao lista = null;
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao l = (ListaReproducao) listasReproducao.get(i);
            if (l.getTitulo().equalsIgnoreCase(tituloLista)) {
                lista = l;
                break;
            }
        }

        if (lista == null) {
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }

        if (posicao < 0 || posicao > lista.tamanho()) {
            System.out.println("Posição inválida.");
            return;
        }

        lista.inserirMusicaEm(posicao, musica);
        System.out.println("Música adicionada na posição " + posicao + " da lista de reprodução.");
    }

    public void removerMusicaListaReproducao(String tituloMusica, String tituloLista) {
        ListaReproducao lista = null;
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao l = (ListaReproducao) listasReproducao.get(i);
            if (l.getTitulo().equalsIgnoreCase(tituloLista)) {
                lista = l;
                break;
            }
        }

        if (lista == null) {
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }

        for (int i = 0; i < lista.tamanho(); i++) {
            Musica m = lista.obterMusica(i);
            if (m.getTitulo().equalsIgnoreCase(tituloMusica)) {
                boolean removida = lista.removerMusica(i);
                if (removida) {
                    System.out.println("Música removida da lista de reprodução.");
                } else {
                    System.out.println("Erro ao remover a música.");
                }
                return;
            }
        }

        System.out.println("Música não encontrada na lista de reprodução.");
    }

    public void removerMusicaListaReproducaoEmPosicao(String tituloLista, int posicao) {
        ListaReproducao lista = null;
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao l = (ListaReproducao) listasReproducao.get(i);
            if (l.getTitulo().equalsIgnoreCase(tituloLista)) {
                lista = l;
                break;
            }
        }

        if (lista == null) {
            System.out.println("Lista de reprodução não encontrada.");
            return;
        }

        if (posicao < 0 || posicao >= lista.tamanho()) {
            System.out.println("Posição inválida.");
            return;
        }

        boolean removida = lista.removerMusica(posicao);
        if (removida) {
            System.out.println("Música removida da posição " + posicao + " da lista de reprodução.");
        } else {
            System.out.println("Erro ao remover a música.");
        }
    }

    public void reproduzirListaDeReproducao(String tituloLista) {
        for (int i = 0; i < listasReproducao.size(); i++) {
            ListaReproducao lista = (ListaReproducao) listasReproducao.get(i);
            if (lista.getTitulo().equalsIgnoreCase(tituloLista)) {
                reprodutorLista = new ReprodutorLista(lista);
                reprodutorLista.restartLista();
                return;
            }
        }
        System.out.println("Lista de reprodução não encontrada.");
    }

    public ReprodutorLista getReprodutorLista() {
        return reprodutorLista;
    }

    public void pausarMusica() {
        reprodutorLista.pause();
    }

    public void executarMusica() {
        reprodutorLista.play();
    }

    public void voltarMusica() {
        reprodutorLista.voltarMusica();
    }

    public void passarMusica() {
        reprodutorLista.passarMusica();
    }

    public void reiniciarMusica() {
        reprodutorLista.restartMusica();
    }

    public void reiniciarLista() {
        reprodutorLista.restartLista();
    }

    public void pararLista() {
        reprodutorLista.stop();
    }

    public ListaEncadeada getRepositorioMusica() {
        return repositorioMusica;
    }

    public ListaEncadeada getListasReproducao() {
        return listasReproducao;
    }

}