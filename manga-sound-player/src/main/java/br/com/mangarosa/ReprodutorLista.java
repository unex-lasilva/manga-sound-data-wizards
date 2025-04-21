package br.com.mangarosa;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ReprodutorLista {
    private String status;
    private Clip clip;
    private ListaReproducao listaReproducao;
    private int posicaoAtual = 0;

    public ReprodutorLista() {
    }

    public ReprodutorLista(ListaReproducao listaReproducao) {
        this.listaReproducao = listaReproducao;
    }

    public void setListaReproducao(ListaReproducao lista) {
        this.listaReproducao = lista;
    }

    public ListaReproducao getListaReproducao() {
        return listaReproducao;
    }

    public void play() {
        if (listaReproducao == null || listaReproducao.isVazia()) {
            System.out.println("Nenhuma lista de reprodução selecionada ou lista vazia.");
            return;
        }

        Musica musicaAtual = listaReproducao.obterMusica(posicaoAtual);

        try {
            File arquivo = new File(musicaAtual.getPath());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            status = "tocando";

            System.out.println("Tocando: " + musicaAtual.getTitulo() + " 🎶");

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }

    public void pause() {
        if (clip != null && status.equals("tocando")) {
            clip.stop();
            status = "pausado";
            System.out.println("Música pausada.");
        }
    }

    public void restartMusica() {
        if (clip != null && (status.equals("tocando") || status.equals("pausado"))) {
            clip.setMicrosecondPosition(0);
            clip.start();
            status = "tocando";
            System.out.println("Música reiniciada.");
        } else {
            System.out.println("Nenhuma música está sendo executada para reiniciar.");
        }
    }


    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            status = "parado";
            System.out.println("Música parada.");
        }
    }

    public void passarMusica() {
        stop();
        if (posicaoAtual < listaReproducao.tamanho() - 1) {
            posicaoAtual++;
        } else {
            System.out.println("Você já está na última música.");
        }
        play();
    }

    public void voltarMusica() {
        if (clip != null && clip.getMicrosecondPosition() > 10_000_000) { // 10 segundos
            clip.setMicrosecondPosition(0);
            clip.start();
            System.out.println("Reiniciando música...");
        } else {
            stop();
            if (posicaoAtual > 0) {
                posicaoAtual--;
                play();
            } else {
                System.out.println("Você já está na primeira música.");
                play();
            }
        }
    }

    public void restartLista() {
        stop();
        posicaoAtual = 0;
        play();
    }
}