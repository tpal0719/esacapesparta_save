package com.sparta.kakaopayment.dto;

import net.minidev.json.JSONArray;

public class KakaoPaymentRequestDto {

    private String cid;
//    private String cid_secret;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
//    private String item_code;
    private Integer quantity;
    private Integer total_amount;
    private Integer tax_free_amount;
//    private Integer vat_amount;
//    private Integer green_deposit;
    private String approval_url;
    private String cancel_url;
    private String fail_url;
//    private JSONArray available_cards;
//    private String payment_method_type;
//    private Integer install_month;
//    private String use_share_installment;
//    private String custom_json;

}
