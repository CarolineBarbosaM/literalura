package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.AutorModel;
import br.com.alura.literalura.model.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<LivroModel, Long> {

    List<LivroModel> findAll();

    @Query("SELECT a.nome FROM Autor a")
    List<AutorModel> findNomeAutores();

    @Query("SELECT a FROM Autor a WHERE a.nascAno IS NOT NULL AND (a.mortAno IS NULL OR :ano <= a.mortAno) AND :ano >= a.nascAno")
    List<AutorModel> findAutoresEmDeterminadoAno(Integer ano);
}
