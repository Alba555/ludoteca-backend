package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface ClientService {

    /**
     * Recupera todas los clientes
     *
     * @return {@link List} de {@link Client}
     */
    List<Client> findAll();

    /**
     * Guarda o modifica un cliente, dependiendo de si el identificador está o no informado
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, ClientDto dto);

    /**
     * Borra un cliente
     *
     * @param id PK de la entidad
     */
    void delete(Long id);

    /**
     * Recupera un {@link Client} a partir de su id
     *
     * @param id PK de la entidad
     * @return {@link Client}
     */
    Client get(Long id);
}