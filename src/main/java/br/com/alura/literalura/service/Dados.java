package br.com.alura.literalura.service;

import br.com.alura.literalura.service.interfaces.IConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Dados implements IConverteDados {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T ObterDados(String json, Class<T> classe) {
        try {

            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
    }
}
