package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface AccountDao {

    String getNameById(int accountId);

    double getBalance(int accountId);

    List<Integer> getTransferToAccounts();

    void sendTransfer(int accountFrom, int accountTo, double amount);

    void rejectTransferWithSameId(int accountFrom, int accountTo, double amount);

    List<Transfer> listTransfers(int userId);

    int getUserId(int accountId);

    Transfer mapRowToTransfer(SqlRowSet rowSet);

    Transfer transfersFromId(int userId, int transferId);


}
