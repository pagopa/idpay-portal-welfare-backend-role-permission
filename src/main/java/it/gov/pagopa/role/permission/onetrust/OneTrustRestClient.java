package it.gov.pagopa.role.permission.onetrust;

import it.gov.pagopa.role.permission.dto.onetrust.PrivacyNoticesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "app.rest-client.one-trust.service.name", url = "${app.rest-client.one-trust.service.privacy-notices.base-url}")
public interface OneTrustRestClient {

    @GetMapping(value = "/privacynotices/{id}")
    PrivacyNoticesDTO getPrivacyNotices(
            @PathVariable("id") String id,
            @RequestParam String date,
            @RequestHeader("Authorization") String bearer);

}