package it.gov.pagopa.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.dto.ConsentDTO;
import it.gov.pagopa.dto.ConsentItemDTO;
import it.gov.pagopa.service.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ConsentApiController implements ConsentApi {

    @Autowired
    private ConsentService consentService;


    @Override
    public ResponseEntity<ConsentDTO> retrieveConsent(String userId, String acceptLanguage) {
        return ResponseEntity.ok(this.consentService.retrieveConsent(userId, acceptLanguage));
    }

    @Override
    public ResponseEntity<Void> saveConsent(String userId, String acceptLanguage, List<ConsentItemDTO> body) {
        return null;
    }
}
