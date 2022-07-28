package it.gov.pagopa.service;

import it.gov.pagopa.config.ConsentConfiguration;
import it.gov.pagopa.dto.ConsentDTO;
import it.gov.pagopa.dto.ConsentItemDTO;
import it.gov.pagopa.dto.github.CommitItem;
import it.gov.pagopa.model.Consent;
import it.gov.pagopa.model.LabelEnum;
import it.gov.pagopa.model.PortalConsent;
import it.gov.pagopa.repository.ConsentRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsentServiceImpl implements ConsentService {

    @Autowired
    private ConsentConfiguration consentConfiguration;

    @Autowired
    private ConsentRepository consentRepository;

    @Override
    public ConsentDTO retrieveConsent(String userId, String acceptLanguageInput) {

        Optional<PortalConsent> consentDb = this.consentRepository.findById(userId);
        ConsentDTO consentDto = ConsentDTO.builder().build();

        String acceptLanguage = this.consentConfiguration.getAllowedLocals().stream()
                .filter(lang -> lang.equals(acceptLanguageInput))
                .findFirst()
                .orElse(this.consentConfiguration.getDefaultLocale());

        PortalConsent portalConsent = consentDb.orElse(PortalConsent.builder().consents(Collections.emptyList()).build());
        Map<LabelEnum, String> actualConsent =
                portalConsent.getConsents().stream().collect(Collectors.toMap(Consent::getLabel, Consent::getSha));

        /*
        * TODO: example of api call, must be refactorized in order to working properly
        */

        if (consentConfiguration.getConsentMap().size() != portalConsent.getConsents().size()) {
            consentDto.setChanged(Boolean.TRUE);
            consentDto.setConsents(Collections.emptyList());//recupero link e stop
        } else {
            List<ConsentItemDTO> consentItemDtoList = new ArrayList<>();
            for (Map.Entry<String, Map<String, String>> consentConfig : consentConfiguration.getConsentMap().entrySet()) {

                Map<String, String> specificConfig = consentConfig.getValue();

                ResponseEntity<List<CommitItem>> responseEntity =
                        new RestTemplate().exchange(
                                String.format(consentConfiguration.getGithubCommitsUri(), consentConfig.getKey(), specificConfig.get("filename"), acceptLanguage, specificConfig.get("extension")),
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<CommitItem>>() {
                                }
                        );
                if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {

                    CommitItem commitItem = responseEntity.getBody().get(NumberUtils.INTEGER_ZERO);
                    String actualSha = actualConsent.get(LabelEnum.valueOf(consentConfig.getKey()));
                    if (!commitItem.getSha().equals(actualSha)) {
                        consentDto.setAccepted(Boolean.FALSE);
                        consentDto.setChanged(Boolean.TRUE);
                    }
                    consentItemDtoList.add(ConsentItemDTO.builder()
                            .sha(commitItem.getSha())
                            .label(ConsentItemDTO.LabelEnum.fromValue(consentConfig.getKey()))
                            .url(String.format(specificConfig.get("rawUrl"), consentConfig.getKey(), specificConfig.get("filename"), acceptLanguage, specificConfig.get("extension")))
                            .build());
                }
            }
            consentDto.setConsents(consentItemDtoList);

        }

/*
        {

            consentDto = ConsentDTO.builder().accepted(Boolean.FALSE).changed(Boolean.FALSE)
                    .build();
            List<ConsentItemDTO> consentItemDtoList = new ArrayList<>();
            for (Map.Entry<String, Map<String, String>> consentConfig : consentConfiguration.getConsentMap().entrySet()) {

                Map<String, String> specificConfig = consentConfig.getValue();

                ResponseEntity<List<CommitItem>> responseEntity =
                        new RestTemplate().exchange(
                                String.format(consentConfiguration.getGithubCommitsUri(), consentConfig.getKey(), specificConfig.get("filename"), acceptLanguage, specificConfig.get("extension")),
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<CommitItem>>() {
                                }
                        );
                if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                    CommitItem commitItem = responseEntity.getBody().get(NumberUtils.INTEGER_ZERO);
                    consentItemDtoList.add(ConsentItemDTO.builder()
                            .sha(commitItem.getSha())
                            .label(ConsentItemDTO.LabelEnum.fromValue(consentConfig.getKey()))
                            .url(String.format(specificConfig.get("rawUrl"), consentConfig.getKey(), specificConfig.get("filename"), acceptLanguage, specificConfig.get("extension")))
                            .build());
                }
            }
            consentDto.setConsents(consentItemDtoList);

        }
        */
        return consentDto;
    }

    @Override
    public void saveConsent(String userId, String acceptLanguage, List<ConsentItemDTO> body) {
        Optional<PortalConsent> consentDb = this.consentRepository.findById(userId);
        PortalConsent portalConsent = PortalConsent.builder().build();

        if (consentDb.isPresent()) {
            portalConsent = consentDb.get();
/*          portalConsent.addAcceptancesHistory(
                    AcceptancesHistory.builder()
                            .acceptedDateTime(portalConsent.getAcceptDatetime())
                            .consents(portalConsent.getConsents())
                            .build()
             );
*/
        }

        List<Consent> consent = body.stream().map(x -> Consent.builder()
                        .label(LabelEnum.valueOf(x.getLabel().name()))
                        .sha(x.getSha()).build())
                .collect(Collectors.toList());

        portalConsent.setAcceptDatetime(LocalDateTime.now());
        portalConsent.setConsents(consent);

        this.consentRepository.save(portalConsent);
    }


}
