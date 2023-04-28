package com.project.gabojago.gabojagouser.dto.sells;

import java.util.Date;

import lombok.Data;

@Data
public class KakaoPayApprovalDto {

    //response
    private String aid, tid, cid,name,uId;
    private String total,partner_order_id, partner_user_id, payment_method_type;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private int sId;
    private Date created_at, approved_at;


}
