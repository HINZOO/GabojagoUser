package com.project.gabojago.gabojagouser.service.sells;

import java.net.URI;
import java.net.URISyntaxException;


import com.project.gabojago.gabojagouser.dto.sells.KakaoPayApprovalDto;
import com.project.gabojago.gabojagouser.dto.sells.KakaoPayReadyDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;

@Service
@Log
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyDto kakaoPayReadyDto;
    private KakaoPayApprovalDto kakaoPayApprovalDto;

    public String kakaoPayReady(KakaoPayApprovalDto kakaoPayApprovalDto) {
        this.kakaoPayApprovalDto = kakaoPayApprovalDto;
        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "96f3c77b826eb1f10d4e5ba630d7d704");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", kakaoPayApprovalDto.getUId());
        params.add("item_name", kakaoPayApprovalDto.getItem_name());
        params.add("quantity", "1");
        params.add("total_amount", kakaoPayApprovalDto.getTotal());
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:7777/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:7777/sells/"+ kakaoPayApprovalDto.getSId()+"/detail.do");
        params.add("fail_url", "http://localhost:7777/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyDto = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyDto.class);

            log.info("" + kakaoPayReadyDto);

            return kakaoPayReadyDto.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "/pay";

    }

    public KakaoPayApprovalDto kakaoPayInfo(String pg_token) {


        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "96f3c77b826eb1f10d4e5ba630d7d704");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyDto.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", kakaoPayApprovalDto.getUId());
        params.add("pg_token", pg_token);
        params.add("total_amount", kakaoPayApprovalDto.getTotal());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
             kakaoPayApprovalDto = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalDto.class);
            log.info("" + kakaoPayApprovalDto);

            return kakaoPayApprovalDto;

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
