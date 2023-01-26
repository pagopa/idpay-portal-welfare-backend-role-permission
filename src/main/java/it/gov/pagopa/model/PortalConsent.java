package it.gov.pagopa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document("portal_consent")
@NoArgsConstructor
@AllArgsConstructor
public class PortalConsent {
    @Id
    private String userId;
    private LocalDateTime acceptDate;
    private String versionId;
    /*
    TODO keep it?
    private List<AcceptancesHistory> history; //storico accettazioni
     */
}