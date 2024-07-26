package domain.paymeny.controller;
import com.sparta.PaymentApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest(classes = PaymentApplication.class) // 메인 클래스를 지정합니다.
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testKakaoPaySuccess() throws Exception {
        mockMvc.perform(get("/payment/kakaoPaySuccess")
                        .param("pg_token", "test_pg_token"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentResult"))
                .andExpect(model().attribute("message", "결제 성공"));
    }

    @Test
    public void testKakaoPayCancel() throws Exception {
        mockMvc.perform(get("/payment/kakaoPayCancel"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentResult"))
                .andExpect(model().attribute("message", "결제 취소"));
    }
}