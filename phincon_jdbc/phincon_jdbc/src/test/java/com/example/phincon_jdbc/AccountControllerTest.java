package com.example.phincon_jdbc;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.phincon_jdbc.controller.AccountController;
import com.example.phincon_jdbc.controller.UserController;
import com.example.phincon_jdbc.model.Account;
import com.example.phincon_jdbc.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    UserController userController;

    @Autowired
    AccountController accountController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void whenAccountControllerInjected_thenNotNull() throws Exception {
        log.info("assert accountController is not null");
        assertThat(accountController).isNotNull();
    }

    // @Test
    // public void whenGetRequestToAccounts_thenCorrectResponse() throws Exception {
    //     log.info("assert GET /api/accounts is correct response");
    //     mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts").contentType(MediaType.APPLICATION_JSON))
    //             .andDo(log())
    //             .andExpect(status().isOk())
    //             .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON));
    // }

    @Test
    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
        Account account = new Account(1, "1982094121", 10000);
        String accountJson = objectMapper.writeValueAsString(account);
        log.info("Account: {}", accountJson);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts").content(accountJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isCreated());

    }

    // @Test
    // public void whenPostRequestToUsersAndInValidUser_thenCorrectReponse() throws Exception {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     Account account = new Account(2, "1982094121", 10000);
    //     String accountJson = objectMapper.writeValueAsString(account);
    //     mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
    //             .content(accountJson)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andDo(log())
    //             .andExpect(status().isBadRequest());

    // }
}
