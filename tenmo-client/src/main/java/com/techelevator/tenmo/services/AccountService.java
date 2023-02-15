package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private final RestTemplate restTemplate = new RestTemplate();
    private Account currentUser;

    private static final String API_BASE_URL = "http://localhost:8080";

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public double getCurrentBalance() {

        double balance = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(headers);


        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "/balance", HttpMethod.GET, entity, Account.class);
            Account user = response.getBody();
            if (user != null) {
                balance = user.getBalance();
                System.out.println("Current balance is: $ " + balance);
            }
        } catch (RestClientResponseException | ResourceAccessException | NullPointerException e   ) {
            BasicLogger.log(e.getMessage());
        }
        if (balance != 0){

        }
        return balance;
    }

    public String makeTransfer(double transferAmount, int recipientId) {

        double balance = 0;
        double balanceA = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(currentUser.getAccountId(), headers);

        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL + "/" + currentUser.getAccountId() + "/balance", HttpMethod.PUT, entity, Account.class);
            Account user = response.getBody();
            if (user != null) {
                balance = user.getBalance();
                balance -= transferAmount;
                user.setBalance(balance);
            }
        } catch (RestClientResponseException | ResourceAccessException | NullPointerException e) {
            BasicLogger.log(e.getMessage());
        }

        HttpHeaders headersA = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity entityA = new HttpEntity<>(recipientId, headersA);

        try {
            ResponseEntity<Account> responseA =
                    restTemplate.exchange(API_BASE_URL + "/" + recipientId + "/balance", HttpMethod.PUT, entityA, Account.class);
            Account userA = responseA.getBody();
            if (userA != null) {
                balanceA = userA.getBalance();
                balanceA += transferAmount;
                userA.setBalance(balanceA);
            }
        } catch (RestClientResponseException | ResourceAccessException | NullPointerException e) {
            BasicLogger.log(e.getMessage());
        }
        return "Your new balance is " + balance;
    }


    public Account[] listTransferToAccounts() {

        Account[] availableAccounts = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(headers);


        try {
            ResponseEntity<Account[]> response =
                    restTemplate.exchange(API_BASE_URL + "/account" , HttpMethod.GET, entity, Account[].class);
            availableAccounts = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException | NullPointerException e) {
            BasicLogger.log(e.getMessage());
        }
        return availableAccounts;
    }




    /*
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
    */

}
