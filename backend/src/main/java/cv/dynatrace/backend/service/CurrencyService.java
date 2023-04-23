package cv.dynatrace.backend.service;

import cv.dynatrace.backend.entity.Currency;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CurrencyService {
    private static final String nbpApiAddress = "http://api.nbp.pl/api/exchangerates/rates";
    @Autowired
    public CurrencyService(){

    }

    /**
     * Method for getting the exchange rate of the desired currency from desired day
     * @param currencyCode - the ISO currency code (ISO 4217)
     * @param date - the date
     * @return - The currency object representing fetched data
     */
    public Optional<Currency> getExchangeRate(String currencyCode, LocalDate date){
        try {

            String url = String.format("%s/a/%s/%s", nbpApiAddress, currencyCode, date.toString());

            HttpResponse<String> response = CurrencyService.sendRequest(url);
            int statusCode = response.statusCode();
            String responseBody = response.body();


            // return based on response code
            if(statusCode == 200){
                // parse the json object
                JSONParser jp = new JSONParser();
                JSONObject jo = (JSONObject)jp.parse(responseBody);

                // parse rates array and get the desired rate (first one)
                JSONArray rates = (JSONArray)jo.get("rates");
                JSONObject rate = (JSONObject)rates.get(0);
                double exchangeRate = (Double)rate.get("mid");

                // return the result
                return Optional.of(
                        Currency.builder().code(currencyCode).exchangeRate(exchangeRate).build()
                );
            }else{
                // in case of any errors return an empty optional
                return Optional.empty();
            }


        } catch (ParseException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method for getting the min and max exchange rates from given last n numberOfQuotations
     * @param currencyCode - the ISO currency code (ISO 4217)
     * @param numberOfQuotations - the number of last quotations taken into consideration
     * @return - The currency object representing fetched data
     */
    public Optional<Currency> getMinAndMaxExchangeRates(String currencyCode, int numberOfQuotations){
        try {

            // create request
            String url = String.format("%s/a/%s/last/%d", nbpApiAddress, currencyCode, numberOfQuotations);


            // send request
            HttpResponse<String> response = CurrencyService.sendRequest(url);
            int statusCode = response.statusCode();
            String responseBody = response.body();


            // return based on response code
            if(statusCode == 200){
                // parse the json object
                JSONParser jp = new JSONParser();
                JSONObject jo = (JSONObject)jp.parse(responseBody);

                // parse rates array
                JSONArray rates = (JSONArray)jo.get("rates");

                double minValue = (Double)((JSONObject)rates.get(0)).get("mid");
                double maxValue = (Double)((JSONObject)rates.get(0)).get("mid");

                for(int i =0; i <rates.size(); i++){
                    JSONObject singleRate = (JSONObject)rates.get(i);
                    double singleRateValue = (Double)singleRate.get("mid");

                    if(singleRateValue < minValue){
                        minValue = singleRateValue;
                    }

                    if(singleRateValue > maxValue){
                        maxValue = singleRateValue;
                    }

                }


                // return the result
                return Optional.of(
                        Currency.builder().code(currencyCode).max(maxValue).min(minValue).build()
                );
            }else{
                // in case of any errors return an empty optional
                return Optional.empty();
            }


        } catch (IOException | InterruptedException |ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The method for getting the major difference between buy and sell price from last numberOfQuotations
     * @param currencyCode - the ISO currency code (ISO 4217)
     * @param numberOfQuotations - the number of last quotations taken into consideration
     * @return - The currency object representing fetched data
     */
    public Optional<Currency> getMaxDifference(String currencyCode, int numberOfQuotations) {

        try {
            String url = String.format("%s/c/%s/last/%d", nbpApiAddress, currencyCode, numberOfQuotations);


            // send request
            HttpResponse<String> response = CurrencyService.sendRequest(url);
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if(statusCode == 200){
                // parse the json object
                JSONParser jp = new JSONParser();
                JSONObject jo = (JSONObject)jp.parse(responseBody);

                // parse rates array
                JSONArray rates = (JSONArray)jo.get("rates");
                //JSONObject rate = (JSONObject)rates.get(0);
                //double exchangeRate = (Double)rate.get("mid");

                JSONObject firtObject = (JSONObject)rates.get(0);

                double maxDifference = Math.abs((Double)firtObject.get("bid") - (Double)firtObject.get("ask"));

                for(int i =0; i <rates.size(); i++){
                    JSONObject singleRate = (JSONObject)rates.get(i);
                    double singleRateDifference = Math.abs((Double)singleRate.get("bid") - (Double)singleRate.get("ask"));
                    System.out.println(singleRateDifference);

                    if(singleRateDifference > maxDifference){
                        maxDifference = singleRateDifference;
                    }


                }


                // return the result
                return Optional.of(
                        Currency.builder().code(currencyCode).majorDifference(maxDifference).build()
                );
            }else{
                // in case of any errors return an empty optional
                return Optional.empty();
            }

        } catch (IOException | InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * A method for sending a request to specified url
     * @param url - the url
     * @return - HttpResponses object with web server response
     * @throws IOException
     * @throws InterruptedException - exceptions are thrown when connection fails
     */
    public static HttpResponse<String> sendRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).header("accept", "application/json").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

}
