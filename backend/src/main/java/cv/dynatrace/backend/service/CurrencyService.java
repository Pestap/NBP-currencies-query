package cv.dynatrace.backend.service;


import cv.dynatrace.backend.entity.CurrencyExchangeRate;
import cv.dynatrace.backend.entity.CurrencyMajorBuySellDifference;
import cv.dynatrace.backend.entity.CurrencyMinMaxRate;
import org.decimal4j.util.DoubleRounder;
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
    public CurrencyService(){}

    /**
     * Method for getting the exchange rate of the desired currency from desired day
     * @param currencyCode - the ISO currency code (ISO 4217)
     * @param date - the date
     * @return - The currency object representing fetched data
     */
    public Optional<CurrencyExchangeRate> getExchangeRate(String currencyCode, LocalDate date){
        try {
            // construct the url
            String url = String.format("%s/a/%s/%s", nbpApiAddress, currencyCode, date.toString());

            // send the request
            HttpResponse<String> response = CurrencyService.sendRequest(url);

            // get status code and body
            int statusCode = response.statusCode();
            String responseBody = response.body();

            // if status code is different than 200, return empty optional
            if(statusCode != 200){
                return Optional.empty();
            }
            // if response code 200
            // parse the json object
            JSONParser parser = new JSONParser();
            JSONObject responseJSONObject = (JSONObject)parser.parse(responseBody);

            // parse rates array and get the desired rate (first one)
            JSONArray rates = (JSONArray)responseJSONObject.get("rates");

            JSONObject rate = (JSONObject)rates.get(0);
            double exchangeRate = (Double)rate.get("mid");

            // return the result
            return Optional.of(
                    CurrencyExchangeRate.builder().code(currencyCode).exchangeRate(exchangeRate).quotationDate(date).build()
            );


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
    public Optional<CurrencyMinMaxRate> getMinAndMaxExchangeRates(String currencyCode, int numberOfQuotations){
        try {

            // create request
            String url = String.format("%s/a/%s/last/%d", nbpApiAddress, currencyCode, numberOfQuotations);


            // send request
            HttpResponse<String> response = CurrencyService.sendRequest(url);

            // get status code and body
            int statusCode = response.statusCode();
            String responseBody = response.body();


            // if status code different from 200
            if(statusCode != 200){
                return Optional.empty();
            }

            // parse the json object
            JSONParser parser = new JSONParser();
            JSONObject responseJSONObject = (JSONObject)parser.parse(responseBody);

            // parse rates array
            JSONArray rates = (JSONArray)responseJSONObject.get("rates");

            double minValue = (Double)((JSONObject)rates.get(0)).get("mid");
            LocalDate minDate = LocalDate.parse((String)((JSONObject)rates.get(0)).get("effectiveDate"));
            double maxValue = (Double)((JSONObject)rates.get(0)).get("mid");
            LocalDate maxDate = LocalDate.parse((String)((JSONObject)rates.get(0)).get("effectiveDate"));

            // iterate through rates list
            for(int i =0; i <rates.size(); i++){
                // get i-th object on list
                JSONObject singleRate = (JSONObject)rates.get(i);
                double singleRateValue = (Double)singleRate.get("mid");

                // compare and replace the min and max values (and quotation dates) if necessary
                if(singleRateValue < minValue){
                    minValue = singleRateValue;
                    minDate = LocalDate.parse((String)singleRate.get("effectiveDate"));
                }

                if(singleRateValue > maxValue){
                    maxValue = singleRateValue;
                    maxDate = LocalDate.parse((String)singleRate.get("effectiveDate"));
                }

            }


            // return the result
            return Optional.of(
                    CurrencyMinMaxRate.builder()
                            .code(currencyCode)
                            .max(maxValue)
                            .maxDate(maxDate)
                            .min(minValue)
                            .minDate(minDate)
                            .build()
            );



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
    public Optional<CurrencyMajorBuySellDifference> getMaxDifference(String currencyCode, int numberOfQuotations) {

        try {

            // construct the url
            String url = String.format("%s/c/%s/last/%d", nbpApiAddress, currencyCode, numberOfQuotations);


            // send request
            HttpResponse<String> response = CurrencyService.sendRequest(url);
            int statusCode = response.statusCode();
            String responseBody = response.body();

            // if response code is different from 200
            if(statusCode != 200){
                return Optional.empty();
            }

            // if response code 200
            // parse the json object
            JSONParser parser = new JSONParser();
            JSONObject responseJSONObject = (JSONObject)parser.parse(responseBody);

            // parse rates array
            JSONArray rates = (JSONArray)responseJSONObject.get("rates");

            // set inital values for comparing
            JSONObject firstObject = (JSONObject)rates.get(0);

            double maxDifference = Math.abs((Double)firstObject.get("bid") - (Double)firstObject.get("ask"));

            LocalDate maxDifferenceDate = LocalDate.parse((String)firstObject.get("effectiveDate"));


            for(int i =0; i <rates.size(); i++){
                // get the i-th quotation
                JSONObject singleRate = (JSONObject)rates.get(i);

                // calculate the difference
                double singleRateDifference = Math.abs((Double)singleRate.get("bid") - (Double)singleRate.get("ask"));

                // compare and replace if necessary
                if(singleRateDifference > maxDifference){
                    maxDifference = singleRateDifference;
                    maxDifferenceDate = LocalDate.parse((String)singleRate.get("effectiveDate"));
                }


            }

            // round the value to 4 decimal places (as in NBP api)
            double roundedMaxDifference = DoubleRounder.round(maxDifference, 4);

            // return the result
            return Optional.of(
                    CurrencyMajorBuySellDifference.builder()
                            .code(currencyCode)
                            .majorDifference(Double.valueOf(roundedMaxDifference))
                            .quotationDate(maxDifferenceDate)
                            .build()
            );

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
