package br.com.alura.literalura.model;

import br.com.alura.literalura.service.Autor;
import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class AutorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer nascAno;
    private Integer mortAno;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private LivroModel livro;

    public AutorModel(Autor dadosAutor, LivroModel livro) {
        this.nome = dadosAutor.getName();
        this.nascAno = dadosAutor.getBirthYear();
        this.mortAno = dadosAutor.getDeathYear();
        this.livro = livro;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNascAno() {
        return nascAno;
    }

    public void setNascAno(Integer nascAno) {
        this.nascAno = nascAno;
    }

    public Integer getMortAno() {
        return mortAno;
    }

    public void setMortAno(Integer mortAno) {
        this.mortAno = mortAno;
    }

    public LivroModel getLivro() {
        return livro;
    }

    public void setLivro(LivroModel livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nascAno=" + nascAno +
                ", mortAno=" + mortAno +
                ", livro=" + (livro != null ? livro.getTitulo() : "null") +
                '}';

    }
}
