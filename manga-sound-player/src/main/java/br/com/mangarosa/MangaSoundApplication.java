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
                        if (controller.getListasReproducao().isEmpty()) {
                            System.out.println("Não há listas disponíveis para copiar.");
                            break;
                        }

                        System.out.println("\nListas disponíveis:");
                        for (int i = 0; i < controller.getListasReproducao().size(); i++) {
                            ListaReproducao lista = (ListaReproducao) controller.getListasReproducao().get(i);
                            System.out.println((i + 1) + ". " + lista.getTitulo());
                        }

                        System.out.print("Digite o número da lista a ser copiada: ");
                        try {
                            int indiceLista = Integer.parseInt(scanner.nextLine()) - 1;

                            if (indiceLista >= 0 && indiceLista < controller.getListasReproducao().size()) {
                                ListaReproducao listaOrigem = (ListaReproducao) controller.getListasReproducao().get(indiceLista);
                                ListaReproducao nova = new ListaReproducao(tituloLista);
                                nova.criarListaAPartirDe(listaOrigem);
                                controller.getListasReproducao().append(nova);
                                System.out.println("Lista criada como cópia.");
                            } else {
                                System.out.println("Número inválido.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Valor inválido. Digite um número inteiro.");
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
        if (controller.getListasReproducao().isEmpty()) {
            System.out.println("Nenhuma lista de reprodução cadastrada.");
            return;
        }

        System.out.println("\n=== Listas de Reprodução ===");
        for (int i = 0; i < controller.getListasReproducao().size(); i++) {
            ListaReproducao lista = (ListaReproducao) controller.getListasReproducao().get(i);
            System.out.println(i + " - " + lista.getTitulo());
        }

        System.out.print("\nDigite o número da lista que deseja editar: ");
        int indiceLista;
        try {
            indiceLista = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Voltando ao menu principal.");
            return;
        }

        if (indiceLista < 0 || indiceLista >= controller.getListasReproducao().size()) {
            System.out.println("Número de lista inválido. Voltando ao menu principal.");
            return;
        }

        ListaReproducao listaSelecionada = (ListaReproducao) controller.getListasReproducao().get(indiceLista);
        String tituloLista = listaSelecionada.getTitulo();

        boolean editando = true;
        while (editando) {
            System.out.println("\n--- Editar Lista: " + tituloLista + " ---");
            System.out.println("1. Adicionar música");
            System.out.println("2. Adicionar música em posição específica");
            System.out.println("3. Remover música");
            System.out.println("4. Remover música pela posição");
            System.out.println("5. Renomear lista");
            System.out.println("6. Limpar lista de reprodução");
            System.out.println("7. Excluir lista de reprodução");
            System.out.println("8. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    if (controller.getRepositorioMusica().isEmpty()) {
                        System.out.println("Nenhuma música disponível para adicionar.");
                        break;
                    }
                    System.out.println("\n=== Músicas Disponíveis ===");
                    for (int i = 0; i < controller.getRepositorioMusica().size(); i++) {
                        Musica musica = (Musica) controller.getRepositorioMusica().get(i);
                        System.out.println(i + " - " + musica.getTitulo() + " - " + musica.getArtista());
                    }
                    System.out.print("\nDigite o número da música que deseja adicionar: ");
                    try {
                        int indiceMusica = Integer.parseInt(scanner.nextLine());
                        if (indiceMusica >= 0 && indiceMusica < controller.getRepositorioMusica().size()) {
                            Musica musicaAdicionar = (Musica) controller.getRepositorioMusica().get(indiceMusica);
                            controller.adicionarMusicaListaReproducao(musicaAdicionar.getTitulo(), tituloLista);
                            System.out.println("Música adicionada com sucesso!");
                        } else {
                            System.out.println("Número de música inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;

                case "2":
                    if (controller.getRepositorioMusica().isEmpty()) {
                        System.out.println("Nenhuma música disponível para adicionar.");
                        break;
                    }
                    System.out.println("\n=== Músicas Disponíveis ===");
                    for (int i = 0; i < controller.getRepositorioMusica().size(); i++) {
                        Musica musica = (Musica) controller.getRepositorioMusica().get(i);
                        System.out.println((i + 1) + " - " + musica.getTitulo() + " - " + musica.getArtista());
                    }
                    System.out.print("\nDigite o número da música que deseja adicionar: ");
                    try {
                        int indiceMusica = Integer.parseInt(scanner.nextLine()) - 1;
                        System.out.print("Digite a posição para inserir (1-" + (listaSelecionada.tamanho() + 1) + "): ");
                        int posicao = Integer.parseInt(scanner.nextLine()) - 1;

                        if (indiceMusica >= 0 && indiceMusica < controller.getRepositorioMusica().size()) {
                            Musica musicaAdicionar = (Musica) controller.getRepositorioMusica().get(indiceMusica);


                            int posicaoExistente = listaSelecionada.posicaoDa(musicaAdicionar);
                            if (posicaoExistente != -1) {
                                System.out.println("Esta música já está na lista na posição " + (posicaoExistente + 1) +
                                        ". Deseja movê-la? (s/n)");
                                String mover = scanner.nextLine();
                                if (!mover.equalsIgnoreCase("s")) {
                                    break;
                                }

                                listaSelecionada.removerMusica(posicaoExistente);
                                if (posicao > posicaoExistente) {
                                    posicao--;
                                }
                            }

                            controller.adicionarMusicaListaReproducaoEmPosicao(musicaAdicionar.getTitulo(), tituloLista, posicao);
                            System.out.println("Música adicionada na posição " + (posicao + 1) + " com sucesso!");
                        } else {
                            System.out.println("Número de música inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;

                case "3":
                    if (listaSelecionada.getMusicas().isEmpty()) {
                        System.out.println("A lista está vazia.");
                        break;
                    }
                    System.out.println("\n=== Músicas na Lista ===");
                    for (int i = 0; i < listaSelecionada.getMusicas().size(); i++) {
                        Musica musica = (Musica) listaSelecionada.getMusicas().get(i);
                        System.out.println(i + " - " + musica.getTitulo() + " - " + musica.getArtista());
                    }
                    System.out.print("\nDigite o número da música que deseja remover: ");
                    try {
                        int indiceRemover = Integer.parseInt(scanner.nextLine());
                        if (indiceRemover >= 0 && indiceRemover < listaSelecionada.getMusicas().size()) {
                            Musica musicaRemover = (Musica) listaSelecionada.getMusicas().get(indiceRemover);
                            controller.removerMusicaListaReproducao(musicaRemover.getTitulo(), tituloLista);
                            System.out.println("Música removida com sucesso!");
                        } else {
                            System.out.println("Número de música inválido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;

                case "4":
                    System.out.print("\nDigite a posição da música que deseja remover: ");
                    try {
                        int posicaoRemover = Integer.parseInt(scanner.nextLine());
                        controller.removerMusicaListaReproducaoEmPosicao(tituloLista, posicaoRemover);
                        System.out.println("Música removida da posição " + posicaoRemover + " com sucesso!");
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;

                case "5":
                    System.out.print("Digite o novo nome para a lista: ");
                    String novoTitulo = scanner.nextLine();

                    if (novoTitulo == null || novoTitulo.trim().isEmpty()) {
                        System.out.println("O nome não pode ser vazio ou conter apenas espaços!");
                    } else {

                        boolean nomeExistente = false;
                        for (int i = 0; i < controller.getListasReproducao().size(); i++) {
                            ListaReproducao l = (ListaReproducao) controller.getListasReproducao().get(i);
                            if (l.getTitulo().equalsIgnoreCase(novoTitulo) &&
                                    !l.getTitulo().equalsIgnoreCase(tituloLista)) {
                                nomeExistente = true;
                                break;
                            }
                        }

                        if (nomeExistente) {
                            System.out.println("Já existe uma lista com esse nome!");
                        } else {
                            listaSelecionada.setTitulo(novoTitulo.trim());
                            tituloLista = novoTitulo.trim();
                            System.out.println("Lista renomeada com sucesso para: " + tituloLista);
                        }
                    }
                    break;
                case "6":
                    if (listaSelecionada.limparLista()) {
                        System.out.println("Lista limpa com sucesso!");
                    } else {
                        System.out.println("A lista já estava vazia.");
                    }
                    break;

                case "7":
                    controller.excluirListaReproducao(tituloLista);
                    System.out.println("Lista excluída com sucesso!");
                    editando = false;
                    break;

                case "8":
                    editando = false;
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
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
            System.out.println("8. ⏹ Parar e voltar ao menu principal");
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
}