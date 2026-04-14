package com.deliverytech.exception;

/**
 * Exceção personalizadas para representar erros de regra de negócio na aplicação.
 * Servirá como base para execeções mais específicas.
 */
public class BusinessException extends RuntimeException {

    /**
     * Construtor que recebe a mensagem e erro.
     * @param message A mensagem descritiva do erro.
     */
    public BusinessException(String message) {
        super(message);
    }
}
