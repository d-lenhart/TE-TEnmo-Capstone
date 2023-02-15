package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao){
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public double getBalance(Principal user){
        String username = user.getName();
        int userId = this.userDao.findIdByUsername(username);
        double balance = accountDao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public List<Integer> listAvailableAccounts(Principal user) {
        String username = user.getName();
        this.userDao.findIdByUsername(username);
        List<Integer> available = accountDao.getTransferToAccounts();


        return available;
    }


    @RequestMapping(path = "transfer/{accountIdA}/{accountIdB}/{transferAmount}", method = RequestMethod.PUT)
    public String executeTransfer(@Valid @PathVariable int accountIdA, @Valid @PathVariable int accountIdB,
                                        @Valid @PathVariable double transferAmount, Principal user) {
        String username = user.getName();
        this.userDao.findIdByUsername(username);
        double balance = accountDao.getBalance(accountIdA);
        String validName = accountDao.getNameById(accountIdA);
        if (!username.equals(validName)) {
            return "You must use your own Account ID.";
        } else if (accountIdA == accountIdB) {
        } else if ((transferAmount > 0) && (transferAmount < balance)) {
                    accountDao.sendTransfer(accountIdA, accountIdB, transferAmount);
                    balance -= transferAmount;
                    int userIdA = accountDao.getUserId(accountIdA);
                    int userIdB = accountDao.getUserId(accountIdB);
                    return "User " + userIdA + " sent $" + transferAmount + " to User " + userIdB + ".\n" + userIdA + ", your new balance is: $" + balance;
        } else {
            return "Insufficient funds or invalid amount";
        }
        return "breh...you know what you did... you can't send money to yourself";
    }

    @RequestMapping(path = "transfer", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal user) {
        String username = user.getName();
        int userId = this.userDao.findIdByUsername(username);
        List<Transfer> transfers = accountDao.listTransfers(userId);
        return transfers;
    }

    @RequestMapping(path = "transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getSelectedTransfer(@Valid @PathVariable int transferId, Principal user) {
        String username = user.getName();
        int userId = this.userDao.findIdByUsername(username);
        Transfer transfer = accountDao.transfersFromId(userId, transferId);
        return transfer;

    }


    //Number 4
    // step one -- reveal list of account_id's to send
    //amount to
    //step two -- get transfer amount from a
    // balance from account_id
    //step three -- check that transfer amount is not more
    //than balance, also not 0 or negative
    //step four -- decrease "from" account/increase "to account"



}
