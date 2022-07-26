package it.gov.pagopa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document("portal_consent")
@NoArgsConstructor
@AllArgsConstructor
public class PortalConsent {

    @Id
    private String userId;
    private LocalDateTime acceptDatetim;
    private List<Consent> consents; //blocco di consensi
    private List<AcceptancesHistory> history; //storico accettazioni

}
