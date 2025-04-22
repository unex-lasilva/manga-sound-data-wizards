package br.com.mangarosa;

import java.util.Scanner;

public class MangaSoundApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MangaController controller = new MangaController();

        boolean executando = true;

        while (executando) {
            System.out.println("\n=== 🎵 MangaSound - Menu Principal 🎵 ===");
            System.out.println("1. Adicionar Música ao Repositório");
            System.out.println("2. Criar Lista de Reprodução");
            System.out.println("3. Editar Lista de Reprodução");
            System.out.println("4. Executar Lista de Reprodução");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Título da música: ");
                    String tituloMusica = scanner.nextLine();
                    System.out.print("Nome do artista: ");
                    String nomeArtista = scanner.nextLine();
                    System.out.print("Caminho do arquivo (.wav): ");
                    String path = scanner.nextLine();

                    controller.adicionarMusica(tituloMusica, path, nomeArtista);
                    break;

                case "2":
                    System.out.print("Título da nova lista de reprodução: ");
                    String tituloLista = scanner.nextLine();
                    System.out.print("Deseja criar uma cópia de outra lista? (s/n): ");
                    String copiar = scanner.nextLine();

                    if (copiar.equalsIgnoreCase("s")) {
                        System.out.print("Título da lista a ser copiada: ");
                        String origem = scanner.nextLine();
                        ListaReproducao listaOrigem = buscarListaPorTitulo(controller, origem);
                        if (listaOrigem != null) {
                            ListaReproducao nova = new ListaReproducao(tituloLista);
                            nova.criarListaAPartirDe(listaOrigem);
                            controller.getListasReproducao().append(nova);
                            System.out.println("Lista criada como cópia.");
                        } else {
                            System.out.println("Lista original não encontrada.");
                        }
                    } else {
                        controller.criarListaReproducao(tituloLista);
                    }
                    break;

                case "3":
                    editarListaMenu(scanner, controller);
                    break;

                case "4":
                    executarListaMenu(scanner, controller);
                    break;

                case "5":
                    System.out.println("Encerrando MangaSound. Até logo!");
                    executando = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void editarListaMenu(Scanner scanner, MangaController controller) {
        System.out.print("Título da lista a ser editada: ");
        String tituloLista = scanner.nextLine();

        boolean editando = true;
        while (editando) {
            System.out.println("\n--- Editar Lista ---");
            System.out.println("1. Adicionar música");
            System.out.println("2. Adicionar música em posição específica");
            System.out.println("3. Remover música por título");
            System.out.println("4. Remover música por posição");
            System.out.println("5. Verificar se música está na lista");
            System.out.println("6. Ver posição da música na lista");
            System.out.println("7. Renomear a lista");
            System.out.println("8. Limpar lista");
            System.out.println("9. Excluir lista de reprodução");
            System.out.println("10. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Título da música: ");
                    String tituloMusica = scanner.nextLine();
                    controller.adicionarMusicaListaReproducao(tituloMusica, tituloLista);
                    break;

                case "2":
                    System.out.print("Título da música: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Posição: ");
                    int posicaoAdd;
                    try {
                        posicaoAdd = Integer.parseInt(scanner.nextLine());
                        controller.adicionarMusicaListaReproducaoEmPosicao(titulo, tituloLista, posicaoAdd);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido. Digite um número inteiro.");
                    }
                    break;

                case "3":
                    System.out.print("Título da música a remover: ");
                    String tituloRemover = scanner.nextLine();
                    controller.removerMusicaListaReproducao(tituloRemover, tituloLista);
                    break;

                case "4":
                    System.out.print("Posição a remover: ");
                    try {
                        int posicaoRemover = Integer.parseInt(scanner.nextLine());
                        controller.removerMusicaListaReproducaoEmPosicao(tituloLista, posicaoRemover);
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido. Digite um número inteiro.");
                    }
                    break;

                case "5":
                    System.out.print("Título da música a verificar: ");
                    String musicaBusca = scanner.nextLine();
                    Musica musicaVerificada = buscarMusicaPorTitulo(controller, musicaBusca);
                    ListaReproducao listaVerif = buscarListaPorTitulo(controller, tituloLista);
                    if (musicaVerificada != null && listaVerif != null) {
                        boolean contem = listaVerif.contemMusica(musicaVerificada);
                        System.out.println(contem ? "A música está na lista." : "A música NÃO está na lista.");
                    }
                    break;

                case "6":
                    System.out.print("Título da música para ver posição: ");
                    String musicaPos = scanner.nextLine();
                    Musica musicaP = buscarMusicaPorTitulo(controller, musicaPos);
                    ListaReproducao listaP = buscarListaPorTitulo(controller, tituloLista);
                    if (musicaP != null && listaP != null) {
                        int pos = listaP.posicaoDa(musicaP);
                        if (pos != -1)
                            System.out.println("A música está na posição: " + pos);
                        else
                            System.out.println("Música não está na lista.");
                    }
                    break;

                case "7":
                    System.out.print("Novo título para a lista: ");
                    String novoTitulo = scanner.nextLine();
                    ListaReproducao listaRenomear = buscarListaPorTitulo(controller, tituloLista);
                    if (listaRenomear != null) {
                        listaRenomear.setTitulo(novoTitulo);
                        tituloLista = novoTitulo;
                        System.out.println("Lista renomeada com sucesso.");
                    }
                    break;

                case "8":
                    ListaReproducao listaLimpar = buscarListaPorTitulo(controller, tituloLista);
                    if (listaLimpar != null && listaLimpar.limparLista()) {
                        System.out.println("Lista limpa com sucesso.");
                    } else {
                        System.out.println("Erro ao limpar a lista.");
                    }
                    break;

                case "9":
                    controller.excluirListaReproducao(tituloLista);
                    System.out.println("Lista excluída.");
                    editando = false;
                    break;

                case "10":
                    editando = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static Musica buscarMusicaPorTitulo(MangaController controller, String titulo) {
        for (int i = 0; i < controller.getRepositorioMusica().size(); i++) {
            Musica m = (Musica) controller.getRepositorioMusica().get(i);
            if (m.getTitulo().equalsIgnoreCase(titulo)) {
                return m;
            }
        }
        return null;
    }

    private static void executarListaMenu(Scanner scanner, MangaController controller) {
        System.out.print("Digite o título da lista de reprodução: ");
        String titulo = scanner.nextLine();

        controller.reproduzirListaDeReproducao(titulo);

        ListaReproducao listaSelecionada = controller.getReprodutorLista().getListaReproducao();
        if (listaSelecionada == null || listaSelecionada.isVazia()) {
            System.out.println("Lista inválida ou vazia. Retornando ao menu principal.");
            return;
        }

        System.out.println("\n▶ Iniciando reprodução da lista: *" + listaSelecionada.getTitulo() + "*");

        boolean reproduzindo = true;
        while (reproduzindo) {
            System.out.println("\n--- Controle da Reprodução ---");
            System.out.println("1. ⏸ Pausar música");
            System.out.println("2. ▶ Executar música");
            System.out.println("3. ⏭ Próxima música");
            System.out.println("4. ⏮ Voltar música");
            System.out.println("5. Reiniciar música atual");
            System.out.println("6. Reiniciar lista");
            System.out.println("7. ⏹ Parar e voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            String acao = scanner.nextLine();

            switch (acao) {
                case "1":
                    controller.pausarMusica();
                    break;
                case "2":
                    controller.executarMusica();
                    break;
                case "3":
                    controller.passarMusica();
                    break;
                case "4":
                    controller.voltarMusica();
                    break;
                case "5":
                    controller.reiniciarMusica();
                    break;
                case "6":
                    controller.reiniciarLista();
                    break;
                case "7":
                    controller.pararLista();
                    reproduzindo = false;
                    System.out.println(" Retornando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static ListaReproducao buscarListaPorTitulo(MangaController controller, String titulo) {
        for (int i = 0; i < controller.getListasReproducao().size(); i++) {
            ListaReproducao l = (ListaReproducao) controller.getListasReproducao().get(i);
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        return null;
    }
}