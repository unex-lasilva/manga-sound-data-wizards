package br.com.mangarosa;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ReprodutorLista {
    private String status;
    private Clip clip;
    private ListaReproducao listaReproducao;
    private int posicaoAtual = 0;

    private String formatarDuracao(int duracao) {
        int minutos = duracao / 60;
        int segundos = duracao % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

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

            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            float frameRate = format.getFrameRate();
            int duracao = (int) (frames / frameRate);
            musicaAtual.setDuracao(duracao);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            status = "tocando";

            System.out.println(" Tocando agora:");
            System.out.println("Título: " + musicaAtual.getTitulo());
            System.out.println("Artista: " + musicaAtual.getArtista());
            System.out.println("Duração: " + formatarDuracao(musicaAtual.getDuracao()));
            System.out.println("🎶");

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }

    public void pause() {
        if (clip != null && "tocando".equals(status)) {
            clip.stop();
            status = "pausado";
            System.out.println("Música pausada.");
        } else {
            System.out.println("Nenhuma música está tocando para pausar.");
        }
    }

    public void restartMusica() {
        if (clip != null && ("tocando".equals(status) || "pausado".equals(status))) {
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
            if (clip.isOpen()) {  // Verifica se o clip está aberto
                clip.stop();
                clip.close();  // Fecha o clip somente se ele estiver aberto
            }
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
            System.out.println("A música foi reiniciada (passou de 10s).");
        } else {
            stop();
            if (posicaoAtual > 0) {
                posicaoAtual--;
                play();
            } else {
                posicaoAtual = 0;
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