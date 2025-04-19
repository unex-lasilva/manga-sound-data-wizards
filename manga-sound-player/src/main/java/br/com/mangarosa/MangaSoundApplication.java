package br.com.mangarosa;

import java.util.Scanner;

public class MangaSoundApplication

{
    public static void main( String[] args ) {
        MangaController controller = new MangaController();


        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n🎵 MangaSound - Menu Principal 🎵");
            System.out.println("1. Adicionar Música ao Repositório");
            System.out.println("2. Criar Lista de Reprodução");
            System.out.println("3. Editar Lista de Reprodução");
            System.out.println("4. Executar Lista de Reprodução");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o título da música: ");
                    String tituloMusica = scanner.nextLine();

                    System.out.print("Digite o caminho do arquivo (path): ");
                    String pathMusica = scanner.nextLine();

                    System.out.print("Digite o nome do artista: ");
                    String nomeArtista = scanner.nextLine();

                    controller.adicionarMusica(tituloMusica, pathMusica, nomeArtista);
                    System.out.println("🎶 Música adicionada com sucesso!");
                    break;
                case 2:
                    System.out.print("Digite o título da nova lista de reprodução: ");
                    String tituloLista = scanner.nextLine();

                    controller.criarListaReproducao(tituloLista);
                    System.out.println("📃 Lista de reprodução criada com sucesso!");
                    break;
                case 3:
                    // chamar método para editar lista
                    break;
                case 4:
                    // executar lista de reprodução
                    break;
                case 5:
                    System.out.println("Encerrando o MangaSound. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);

        scanner.close();
    }
}


