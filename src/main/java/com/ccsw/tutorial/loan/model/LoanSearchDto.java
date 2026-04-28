package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

import java.util.Date;

/**
 * DTO para búsqueda de préstamos con filtros
 *
 * @author ccsw
 */
public class LoanSearchDto {

    private Long gameId;
    private Long clientId;
    private Date date;
    private PageableRequest pageable;

    /**
     * @return gameId
     */
    public Long getGameId() {
        return gameId;
    }

    /**
     * @param gameId new value of {@link #getGameId}.
     */
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * @return clientId
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * @param clientId new value of {@link #getClientId}.
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date new value of {@link #getDate}.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return pageable
     */
    public PageableRequest getPageable() {
        return pageable;
    }

    /**
     * @param pageable new value of {@link #getPageable}.
     */
    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}
