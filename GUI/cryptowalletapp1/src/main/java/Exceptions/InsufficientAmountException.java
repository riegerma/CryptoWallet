package Exceptions;

public class InsufficientAmountException extends Exception {
    public InsufficientAmountException() {
        super("Insufficient Amount of Crypto Currency in Wallet!");
    }
}
