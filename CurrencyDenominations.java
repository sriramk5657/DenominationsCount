

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CurrencyDenominations	 {

    static Map<String, List<Double>> currencyMap = new HashMap<>();

	public static void main(String[] args) {
		Scanner sc = null;
		try {
        	sc = new Scanner(System.in);
            System.out.println("Enter Currency: "); // Enter any currency type as follows... USD, INR, CAD, AUD, EUR
            String currency = sc.nextLine();
            currency = currency.toUpperCase();
            System.out.println("Enter Amount: ");
            Double amount = sc.nextDouble();
            if(currencyMap.containsKey(currency)) {
        		Map<Double, Integer> result = getDenominationsCount(currency, amount);
        		if(result.isEmpty()) {
					throw new Exception();
				}

                for (Double key : result.keySet()) {
                    System.out.println("Denomination : " +key +" -----> Count = " + result.get(key));
                }
        	}else {
        		System.out.println("Invalid Currency:");
        	}
            
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
			sc.close();
		}
    }

    /**
     * This method takes currency and amount as input and returns the Map of
     * Denominations and count as result.
     * 
     * @param currency
     * @param amount
     * @return Map<Double, Integer> currency and count
     */
    public static Map<Double, Integer> getDenominationsCount(String currency, double amount) {
        
    	
    	Map<Double, Integer> result = new LinkedHashMap<>();
        List<Double> coinsList = currencyMap.get(currency);
        Collections.sort(coinsList, Collections.reverseOrder());
        if (amount <= 0) {
        	return result;
        }
        for (Double v : coinsList) {
            int count = 0;
            if(v <= amount) {
            	count = (int)(amount / v);
            	amount = roundDouble(amount - (v * count) , 2);
            }
            if (count != 0) {
                result.put(v, count);
            }
        }
        return result;
    }

    /**
     * List of denominations for different currencies.
     * 
     */
    static {
    	currencyMap.put("USD", Arrays.asList(0.01d, 0.05d, 0.10d, 0.25d, 0.50d, 1d, 5d, 10d, 20d, 50d, 100d));
        currencyMap.put("INR", Arrays.asList(0.50d, 1d, 2d, 5d, 10d, 20d, 50d, 100d, 200d, 500d, 2000d));
        currencyMap.put("CAD", Arrays.asList(0.05d, 0.10d, 0.25d, 0.50d, 1d, 2d, 5d, 10d, 20d, 50d, 100d));
        currencyMap.put("AUD", Arrays.asList(0.05d, 0.10d, 0.20d, 0.50d, 1d, 2d, 5d, 10d, 20d, 50d, 100d));
        currencyMap.put("EUR", Arrays.asList(0.01d, 0.02d, 0.05d, 0.10d, 0.20d, 0.50d, 1d, 2d, 5d, 10d, 20d, 50d, 100d, 200d, 500d));
    }
    
    private static double roundDouble(double d, int places) {

		return new BigDecimal(Double.toString(d)).setScale(places, RoundingMode.HALF_UP).doubleValue();

	}
}
