package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";

    public static final String SERVICE_PATH = "/loan";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseType = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    private LoanDto buildValidLoanDto() {

        LoanDto dto = new LoanDto();

        GameDto game = new GameDto();
        game.setId(1L);

        ClientDto client = new ClientDto();
        client.setId(1L);

        dto.setGame(game);
        dto.setClient(client);
        dto.setStartDate(new Date());
        dto.setEndDate(new Date());

        return dto;
    }

    private Map<String, Object> buildSearchBody(int pageNumber, int pageSize) {

        Map<String, Object> pageable = new HashMap<>();
        pageable.put("pageNumber", pageNumber);
        pageable.put("pageSize", pageSize);

        Map<String, Object> body = new HashMap<>();
        body.put("pageable", pageable);

        return body;
    }

    @Test
    public void findShouldReturnPage() {

        Map<String, Object> body = buildSearchBody(0, 5);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(body), responseType);

        assertNotNull(response);

        assertNotNull(response.getBody());
    }

    @Test
    public void saveShouldCreateLoan() {

        LoanDto dto = buildValidLoanDto();

        ResponseEntity<Void> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteShouldRemoveLoan() {

        LoanDto dto = buildValidLoanDto();

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        Map<String, Object> body = buildSearchBody(0, 10);

        ResponseEntity<ResponsePage<LoanDto>> findResponse = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(body), responseType);

        assertNotNull(findResponse);
        assertNotNull(findResponse.getBody());
        assertNotNull(findResponse.getBody().getContent());

        LoanDto createdLoan = findResponse.getBody().getContent().stream().filter(loan -> loan.getGame() != null && loan.getClient() != null && loan.getGame().getId().equals(1L) && loan.getClient().getId().equals(1L)).findFirst()
                .orElse(null);

        assertNotNull(createdLoan);

        ResponseEntity<Void> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + createdLoan.getId(), HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}