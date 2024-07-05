package com.example.bootcamp.consume_api.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.bootcamp.consume_api.model.SmsGatewayRequest;
import com.example.bootcamp.consume_api.model.SmsGatewayResponse;

@RestController
@RequestMapping("/api/sms")
public class SmsGatewayController {

    static final Logger logger = LoggerFactory.getLogger(SmsGatewayController.class);

    @Autowired
    @Qualifier("restTemplateSmsGateway")
    RestTemplate restTemplate;

    @Value("${service.api.smsgateway.url}")
    private String smsGatewayUrl;

    @Value("${service.api.smsgateway.signature}")
    private String smsGatewaySignature;

    @PostMapping("/send")
    public ResponseEntity<?> sendSms(@RequestBody SmsGatewayRequest smsGatewayRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("user-agent", "Application");
        headers.add("X-signature", smsGatewaySignature);
        HttpEntity<SmsGatewayRequest> entity = new HttpEntity<SmsGatewayRequest>(smsGatewayRequest, headers);
        logger.info("entity={}", entity.getBody());
        logger.info("headers={}", entity.getHeaders());

        try {
            ResponseEntity<SmsGatewayResponse> responseEntity = restTemplate.exchange(smsGatewayUrl, HttpMethod.POST, entity, SmsGatewayResponse.class);

            SmsGatewayResponse smsGwResponse = responseEntity.getBody();
            logger.info("response={}", smsGwResponse);

            smsGwResponse.setTransactionId(smsGatewayRequest.getTransactionId());
            return ResponseEntity.ok(smsGwResponse);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
