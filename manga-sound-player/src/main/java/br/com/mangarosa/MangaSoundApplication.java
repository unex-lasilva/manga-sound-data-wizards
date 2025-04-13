package main.java.br.com.mangarosa;

import java.util.Scanner;

public class MangaSoundApplication
{
    public static void main( String[] args ) {

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
                    // chamar classe/método para adicionar música
                    break;
                case 2:
                    // chamar método para criar lista
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


