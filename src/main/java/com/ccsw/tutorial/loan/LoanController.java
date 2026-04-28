package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author ccsw
 *
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar préstamos con filtros
     */
    @Operation(summary = "Find")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDto> find(@RequestBody LoanSearchDto dto) {

        return loanService.find(dto).map(e -> mapper.map(e, LoanDto.class));
    }

    /**
     * Guardar / actualizar
     */
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(required = false) Long id, @RequestBody LoanDto dto) {

        loanService.save(id, dto);
    }

    /**
     * Borrar
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {

        loanService.delete(id);
    }
}
