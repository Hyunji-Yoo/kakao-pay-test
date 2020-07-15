package com.kakaopay.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.test.vo.CancelRequestVo;
import com.kakaopay.test.vo.PayRequestVo;
import com.kakaopay.test.vo.PaymentVo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PaymentController paymentController;

    @Test
    void executeAndCancelPaymentTest() throws Exception {
        PayRequestVo payRequestVo = getPayRequestVoInstance();

        String content = objectMapper.writeValueAsString(payRequestVo);
        MvcResult result = mockMvc.perform(post("/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        content = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        PaymentVo paymentResultVo = mapper.readValue(content, PaymentVo.class);
        CancelRequestVo cancelRequestVo = new CancelRequestVo();
        cancelRequestVo.setManagementNo(paymentResultVo.getManagementNo());
        cancelRequestVo.setAmount(paymentResultVo.getAmount());
        content = objectMapper.writeValueAsString(cancelRequestVo);
        mockMvc.perform(delete("/payment/{managementNo}", paymentResultVo.getManagementNo())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(content)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    private PayRequestVo getPayRequestVoInstance() {
        PayRequestVo vo = new PayRequestVo();
        vo.setCardNo("1234567890123456");
        vo.setExpiryDate("1125");
        vo.setCvc("777");
        vo.setInstallmentPlan("0");
        vo.setAmount(110000L);
        vo.setVat(10000L);
        return vo;
    }
}