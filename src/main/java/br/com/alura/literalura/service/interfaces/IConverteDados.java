package br.com.alura.literalura.service.interfaces;

public interface IConverteDados {

    <T> T ObterDados(String json, Class<T> classe);
}
