package br.com.alura.literalura.principal;

import br.com.alura.literalura.service.Livros;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                        
                        ***************** LITEALURA *****************
                        
                        Escolha o número da opção no menu:
                        
                        *********************************************
                        
                        1 - Buscar livro pelo título
                        2 - Listar livros registrados
                        3 - Listar autores registrados
                        4 - Listar autores vivos em um determinado ano
                        5 - Listar livro em um determinado idioma
                        0 - Sair
                        """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            Livros ln = new Livros();

            switch (opcao) {
                case 1:
                    ln.buscarLivro();
                    break;
                case 2:
                    ln.listarLivros();
                    break;
                case 3:
                    ln.listarAutores();
                    break;
                case 4:
                    ln.listarAutoresVivos();
                    break;
                case 5:
                    ln.listarLivroPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
