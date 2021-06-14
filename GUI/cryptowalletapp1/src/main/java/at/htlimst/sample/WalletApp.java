package at.htlimst.sample;

import Exceptions.*;
import UI.GlobalContext;
import domain.*;
import infrastructure.CurrentCurrencyPrices;
import infrastructure.FileDataStore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ResourceBundle;

public class WalletApp extends Application {

    //UI Parts
    private static Stage mainStage;
    public static final String GLOBAL_WALLET_LIST = "walletlist";
    public static final String GLOBAL_BANK_ACCOUNT = "bankaccount";
    public static final String GLOBAL_CURRENT_CURRENCY_PRICES = "currencyprizes";
    public static String GLOBAL_SELECTED_FXML = "selectedwallet";


    public static void switchScene(String fxmlFile, String resourceBundle) {
        try {
            Parent root = FXMLLoader.load(WalletApp.class.getResource(fxmlFile), ResourceBundle.getBundle(resourceBundle));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (IOException e) {
            WalletApp.showErrorDialog("Could not load new scene");
            e.printStackTrace();
        }
    }

    public static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("An exception occurred: " + message);
        alert.showAndWait();
    }

    //File Handling

    private BankAccount loadBankAccountFromFile() throws RetrieveDataException {

        DataStore dataStore = new FileDataStore();
        BankAccount bankAccount = dataStore.retrieveBankAccount();
        System.out.println("Bankaccount loaded");
        return bankAccount;
    }

    private WalletList loadWalletListFromFile() throws RetrieveDataException {

        DataStore dataStore = new FileDataStore();
        WalletList walletList = dataStore.retrieveWalletList();
        System.out.println("WalletList loaded");
        return walletList;
    }

    private void storeBankAccountToFile(BankAccount bankAccount) throws SaveDataException {
        DataStore dataStore = new FileDataStore();
        dataStore.saveBankAccount(bankAccount);
    }

    private void storeWalletListToFile(WalletList walletList) throws SaveDataException {
        DataStore dataStore = new FileDataStore();
        dataStore.saveWalletList(walletList);

    }


    @Override
    public void start(Stage stage) throws IOException {

        mainStage = stage;


        BankAccount bankAccount = new BankAccount();
        WalletList walletList = new WalletList();

        try {
            bankAccount = loadBankAccountFromFile();
        } catch (RetrieveDataException e) {
            WalletApp.showErrorDialog("<Error on loading BankAccount data. Use new empty account!");
            e.printStackTrace();
        }
        try {
            walletList = loadWalletListFromFile();
        } catch (RetrieveDataException e) {
            WalletApp.showErrorDialog("<Error on loading WalletList data. Use new empty account!");
            e.printStackTrace();
        }

        //Fill GlobalContext

        GlobalContext.getGlobalContext().putStateFor(WalletApp.GLOBAL_WALLET_LIST, walletList);
        GlobalContext.getGlobalContext().putStateFor(WalletApp.GLOBAL_BANK_ACCOUNT, bankAccount);
        GlobalContext.getGlobalContext().putStateFor(WalletApp.GLOBAL_CURRENT_CURRENCY_PRICES, new CurrentCurrencyPrices());


        switchScene("main.fxml", "at.htlimst.sample.main");
    }

    @Override
    public void stop() throws IOException {
        WalletList walletList = (WalletList) GlobalContext.getGlobalContext().getStateFor(WalletApp.GLOBAL_WALLET_LIST);
        BankAccount bankAccount = (BankAccount) GlobalContext.getGlobalContext().getStateFor(WalletApp.GLOBAL_BANK_ACCOUNT);
        try {
            storeBankAccountToFile(bankAccount);
            System.out.println("BankAccount details stored to file!");
            storeWalletListToFile(walletList);
            System.out.println("WalletList stored to file!");
        } catch (SaveDataException e) {
            WalletApp.showErrorDialog("Could not store! - Data Loss Danger");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        launch(args);
    }
}

