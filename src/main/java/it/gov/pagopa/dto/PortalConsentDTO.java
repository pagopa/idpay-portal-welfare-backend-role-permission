package it.gov.pagopa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PortalConsentDTO {

    @JsonProperty(value = "versionId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String versionId;

    @JsonProperty(value = "firstAcceptance")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Boolean firstAcceptance;

    public PortalConsentDTO() {
        this.versionId = null;
        this.firstAcceptance = null;
    }

    public PortalConsentDTO(String versionId) {
        this.versionId = versionId;
        this.firstAcceptance = null;
    }

    public PortalConsentDTO(String versionId, Boolean firstAcceptance) {
        this.versionId = versionId;
        this.firstAcceptance = firstAcceptance;
    }

}
