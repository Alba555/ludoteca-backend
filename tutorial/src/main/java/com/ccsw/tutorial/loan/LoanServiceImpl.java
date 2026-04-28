package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> find(LoanSearchDto dto) {

        Specification<Loan> spec = LoanSpecification.createSpecification(dto);

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) {

        Loan loan;

        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.loanRepository.findById(id).orElse(null);
        }

        if (dto.getEndDate().before(dto.getStartDate())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha fin no puede ser anterior a la de inicio");
        }

        long diff = dto.getEndDate().getTime() - dto.getStartDate().getTime();
        long days = diff / (1000 * 60 * 60 * 24);

        if (days > 14) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El préstamo no puede superar 14 días");
        }

        List<Loan> overlappingGame = this.loanRepository.findOverlappingGame(dto.getGame().getId(), id, dto.getStartDate(), dto.getEndDate());

        if (!overlappingGame.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El juego ya está prestado en ese rango de fechas");
        }

        List<Loan> overlappingClient = this.loanRepository.findOverlappingClient(dto.getClient().getId(), id, dto.getStartDate(), dto.getEndDate());

        if (overlappingClient.size() >= 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente ya tiene 2 préstamos en ese rango de fechas");
        }

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setClient(clientService.get(dto.getClient().getId()));
        loan.setStartDate(dto.getStartDate());
        loan.setEndDate(dto.getEndDate());

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {

        this.loanRepository.deleteById(id);
    }
}
