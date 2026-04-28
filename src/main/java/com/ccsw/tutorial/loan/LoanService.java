package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author ccsw
 *
 */
public interface LoanService {

    /**
     * Recupera préstamos con filtros
     *
     * @param dto filtros
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> find(LoanSearchDto dto);

    /**
     * Guarda o modifica un préstamo
     *
     * @param id PK de la entidad
     * @param dto datos
     */
    void save(Long id, LoanDto dto);

    /**
     * Elimina un préstamo
     *
     * @param id PK
     */
    void delete(Long id);
}
