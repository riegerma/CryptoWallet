package domain;

import Exceptions.RetrieveDataException;
import Exceptions.SaveDataException;

public interface DataStore {
    void saveBankAccount(BankAccount bankAccount) throws SaveDataException;

    void saveWalletList(WalletList walletLIst) throws SaveDataException;

    BankAccount retrieveBankAccount() throws RetrieveDataException;

    WalletList retrieveWalletList() throws RetrieveDataException;
}