package infrastructure;

import Exceptions.GetCurrentPriceException;
import domain.CryptoCurrency;
import domain.CurrentPriceForCurrency;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrentCurrencyPrices implements CurrentPriceForCurrency {

    @Override
    public BigDecimal getCurrentPrice(CryptoCurrency cryptoCurrency) throws GetCurrentPriceException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                URI.create("https://api.coingecko.com/api/v3/simple/price?ids="
                        + cryptoCurrency.getCurrencyName()
                        + "&vs_currencies=eur")).header("accept", "application/json").build();

        try {
            HttpResponse<String> result = client.send(request, HttpResponse.BodyHandlers.ofString());
            String[] split = result.body().split(":");
            String result2 = split[2].substring(0, split[2].length() - 2);
            return new BigDecimal(result2);


        } catch (IOException ioE) {
            ioE.printStackTrace();
            throw new GetCurrentPriceException("IOException: " + ioE.getMessage());

        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new GetCurrentPriceException("Call Interrupted: " + e.getMessage());

        } catch (NumberFormatException nFE) {
            throw new GetCurrentPriceException("Conversion of Value not possible: " + nFE.getMessage());
        }
    }

}
