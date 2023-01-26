package it.gov.pagopa.connector.onetrust;

import it.gov.pagopa.dto.onetrust.PrivacyNoticesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "onetrust") //TODO url
public interface OneTrustRestClient {

    @GetMapping(value = "/privacynotices/{id}")
    PrivacyNoticesDTO getPrivacyNotices(@PathVariable("id") String id, @RequestParam String date);

}
