package com.deliverytech.exception;

/**
 * Exceção para indicar que uma entidade não foi encontrada no banco de dados.
 */
public class EntityNotFoundException extends BusinessException {

    /**
     * Construtor que formata uma mensagem padrão de "não encontraao".
     * @param entityName A mensagem descritiva do erro.
     * @param id O identificador que não foi encontrado.
     */
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s com ID %d não encontrado", entityName, id));
    }
}
