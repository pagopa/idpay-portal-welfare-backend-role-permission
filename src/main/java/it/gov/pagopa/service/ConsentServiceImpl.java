package it.gov.pagopa.service;

import it.gov.pagopa.dto.ConsentDTO;
import it.gov.pagopa.model.PortalConsent;
import it.gov.pagopa.repository.ConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsentServiceImpl implements ConsentService {

    @Value("${portal.welfare.tos.privacy}")
    private String linkPrivacy;
    @Value("${portal.welfare.tos.tc}")
    private String linkTc;

    @Autowired
    private ConsentRepository consentRepository;

    @Override
    public ConsentDTO retrieveConsent(String userId, String acceptLanguage) {
        Optional<PortalConsent> consent = this.consentRepository.findById(userId);
        ConsentDTO consentDto = null;
        if (consent.isPresent()) {
            //check version changed



        } else {
            consentDto = ConsentDTO.builder().accepted(Boolean.FALSE).build();
        }
        return consentDto;
    }
}
