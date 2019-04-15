package ke;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import ke.co.pages.HomePage;
import org.tinylog.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main {

    private static WebClient webClient = new WebClient();


    public static void main(String[] args) throws IOException {

        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);

        HomePage homePage = new HomePage(webClient);
        homePage.startSearch(getCityList());

        webClient.close();
    }


    private static List<String> getCityList() throws IOException {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        URL url;
        WebRequest webRequest;
        WebResponse webResponse;
        Set<String> citiesSet = new TreeSet<>();

        for (Character character : alphabet) {
            url = new URL("https://www.modern.co.ke/search.php?term=" + character);
            webRequest = new WebRequest(url, HttpMethod.POST);
            webResponse = webClient.loadWebResponse(webRequest);
            String result = webResponse.getContentAsString();
            String arr = result.substring(1, result.length() - 1);
            String[] arrayList = arr.split(",");
            citiesSet.addAll(Arrays.asList(arrayList));
        }

        citiesSet.remove("ul");

        List<String> citiesList = new ArrayList<>();
        for (String city : citiesSet) {
            city = city.substring(1, city.length() - 1);
            citiesList.add(city);
        }

        Logger.info("[TEST] == Collecting cities is finished.");
        return citiesList;
    }


}
