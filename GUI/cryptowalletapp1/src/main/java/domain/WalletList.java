package domain;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WalletList implements Serializable {

    private HashMap<CryptoCurrency, Wallet> wallets;

    public WalletList() {

        this.wallets = new HashMap<>();
    }

    public void addWallet(Wallet x) {
        if (x != null && !this.wallets.containsKey(x.getCryptoCurrency())) {
            this.wallets.put(x.getCryptoCurrency(), x);
        }
    }


    public Wallet getWallet(CryptoCurrency cryptoCurrency) {

        return this.wallets.get(cryptoCurrency);
    }

    public List<Wallet> getWalletsAsObservableList() {

        return wallets.values().stream().collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "WalletLIst{" +
                "wallets=" + wallets +
                '}';
    }
}
