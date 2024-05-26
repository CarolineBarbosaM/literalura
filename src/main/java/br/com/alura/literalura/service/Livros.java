package br.com.alura.literalura.service;

import br.com.alura.literalura.model.AutorModel;
import br.com.alura.literalura.model.LivroModel;
import br.com.alura.literalura.model.enums.Idioma;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.consumo.ConsumoApi;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Livros {

    private Scanner leitura = new Scanner(System.in);
    private final String URL = "http://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private Dados conversor = new Dados();

    public LivroRepository repositorio;

    private String title;
    private List<Autor> authors;
    private List<String> languages;

    @JsonProperty("download_count")
    private int downloadCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Autor> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Autor> authors) {
        this.authors = authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public void buscarLivro() {

        System.out.println("Informe o título do livro: ");
        var tituloBusca = leitura.nextLine();
        var cadastrarNovo = "s";

        while (cadastrarNovo.equalsIgnoreCase("s")) {

            var tituloBuscaFormatado = tituloBusca.toLowerCase().replace(" ", "%20");
            var json = consumoApi.obterDados(URL + tituloBuscaFormatado);
            API resultadoApi = conversor.ObterDados(json, API.class);

            if (resultadoApi != null && resultadoApi.getResults() != null && !resultadoApi.getResults().isEmpty()) {
                System.out.println(" \n =============== Obra(s) Localizadas =============== \n");

                for (Livros livro : resultadoApi.getResults()) {
                    System.out.println("Título: " + livro.getTitle());
                    System.out.println("Autore(s) da obra ");

                    for (Autor autor : livro.getAuthors()) {
                        System.out.println("  Nome: " + autor.getName());
                        System.out.println("  Ano de Nascimento: " + autor.getBirthYear());
                        System.out.println("  Ano de Morte: " + autor.getDeathYear());
                    }
                    System.out.println("Idiomas: " + String.join(", ", livro.getLanguages()));
                    System.out.println("Downloads até agora: " + livro.getDownloadCount());
                }
                System.out.println("===================================\n" +
                        "Deseja salvar o(s) título(s)? (s/n) \n" +
                        "=================================== \n");
                var salvarBanco = leitura.nextLine();

                if (salvarBanco.equalsIgnoreCase("s")) {
                    System.out.println("\n ***** dados salvos com sucesso ***** \n");
                    List<LivroModel> livros = new ArrayList<>();

                    Set<String> titulosUnicos = new HashSet<>();

                    for (Livros dadosLivro : resultadoApi.getResults()) {

                        if (titulosUnicos.add(dadosLivro.getTitle())) {
                            LivroModel livro = new LivroModel(dadosLivro);

                            for (String idiomaStr : dadosLivro.getLanguages()) {
                                Idioma idiomaEnum = Idioma.fromCode(idiomaStr.toLowerCase());
                                if (idiomaEnum != null) {
                                    livro.setIdiomas(idiomaEnum.getIdiomaTraduzido());
                                    break;
                                }
                            }

                            livros.add(livro);
                            repositorio.save(livro);
                        } else {
                            System.out.println("Títulos duplicados não registrados: " + dadosLivro.getTitle());
                        }
                    }

                    cadastrarNovo = "n";
                } else {
                    cadastrarNovo = "n";
                }
            } else {
                System.out.println("Nenhum título encontrado com este nome: " + tituloBusca);
                System.out.println("Deseja pesquisar outro título? (s/n)");
                cadastrarNovo = leitura.nextLine();
            }
        }
    }

    public void listarLivros() {
        List <LivroModel> acervo = repositorio.findAll();
        if(acervo.isEmpty()){
            System.out.println("Acervo ainda sem registros! \n");
            acervo.forEach(System.out::println);
        } else {
            System.out.println("Acervo Registrado: \n");
            acervo.forEach(System.out::println);
        }
    }

    public void listarAutores() {
        List<AutorModel> acervo = repositorio.findNomeAutores();
        if(acervo.isEmpty()){
            System.out.println("Acervo ainda sem registros! \n");
            acervo.forEach(System.out::println);
        } else {
            System.out.println("Acervo Registrado: \n");
            acervo.forEach(System.out::println);
        }

    }

    public void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisar: ");
        Integer ano = leitura.nextInt();
        List<AutorModel> acervo = repositorio.findAutoresEmDeterminadoAno(ano);
        if(acervo.isEmpty()){
            System.out.println("Acervo ainda sem registros! \n");
            acervo.forEach(System.out::println);
        } else {
            System.out.println("Acervo Registrado: \n");
            acervo.forEach(System.out::println);
        }

    }

    public void listarLivroPorIdioma() {
        System.out.println("Digite o idioma que deseja consultarr: ");
        String idioma = leitura.nextLine();

    }
}
