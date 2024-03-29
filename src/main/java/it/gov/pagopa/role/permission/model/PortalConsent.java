package it.gov.pagopa.role.permission.model;

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
    private LocalDateTime acceptanceDate;
    private String versionId;
    /*
    TODO keep it?
    private List<AcceptancesHistory> history; //storico accettazioni
    */

    public PortalConsent(String userId, String versionId) {
        this.userId = userId;
        this.acceptanceDate = LocalDateTime.now();
        this.versionId = versionId;
    }
}