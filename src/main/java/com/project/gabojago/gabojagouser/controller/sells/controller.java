package com.project.gabojago.gabojagouser.controller.sells;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//@Controller
//@RequestMapping("/jq")
//public class controller {
//
//    @RequestMapping("/jq.do")
//    public ModelAndView main(ModelAndView mv, HttpSession s, RedirectView rv){
//        mv.setViewName("jq/test");
//        return mv;
//    }
//    @RequestMapping("/pay.do")
//    public String serve(Model model,@RequestParam("pg_token") String pgToken){
//        model.addAttribute("pgToken",pgToken);
//        return "/sells/pay";
//    }
//    @RequestMapping("/pay.do")
//    public String serve(Model model,@RequestParam("pg_token") String pgToken){
//        model.addAttribute("pgToken",pgToken);
//        return "/sells/pay";
//    }
//    @RequestMapping("/kakaopay.do")
//    @ResponseBody
//    public String pay() {
//        try {
//            URL adress = new URL("https://kapi.kakao.com/v1/payment/ready");
//            HttpURLConnection connection = (HttpURLConnection) adress.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Authorization", "KakaoAK 96f3c77b826eb1f10d4e5ba630d7d704");
//            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//            connection.setDoOutput(true);
//            String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=Ticket&quantity=1&total_amount=2200&vat_amount=200&tax_free_amount=0&approval_url=http://localhost:7777/jq/pay.do&fail_url=http://localhost/jq/fail.do&cancel_url=http://localhost:7777/sells/list.do";
//            OutputStream outStream = connection.getOutputStream();
//            DataOutputStream data = new DataOutputStream(outStream);
//            data.writeBytes(param);
//            data.close();
//
//            int result = connection.getResponseCode();
//
//            InputStream input;
//            if (result == 200) {
//                input = connection.getInputStream();
//            } else {
//                input = connection.getErrorStream();
//            }
//            InputStreamReader reader = new InputStreamReader(input);
//            BufferedReader bufferedReader = new BufferedReader(reader);
//            return bufferedReader.readLine();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
