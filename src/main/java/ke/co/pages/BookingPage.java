package ke.co.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import ke.co.Fare;
import ke.co.MyFileWriter;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class BookingPage {

    private WebClient webClient;
    private HtmlPage page;


    BookingPage(WebClient webClient) {
        this.webClient = webClient;
    }


    void getSchedule(List<String> urls) {
        List<Fare> fareList = Collections.synchronizedList(new ArrayList<>());

        Collections.synchronizedList(urls).parallelStream().forEach(url -> {
            Logger.info("[TEST] == Going to get schedule of " + url);
            try {
                page = webClient.getPage(url);
            } catch (IOException e) {
                Logger.warn("[TEST] == Could not get page with schedule [" + url + "]\n" + e.getMessage());
            }

            List<HtmlElement> headerElements = page.getByXPath("//table[@class='table']//tr[contains(@style,'border-bottom')]");

            Fare fare = new Fare();
            for (HtmlElement element : headerElements) {
                for (int i = 0; i < element.getByXPath("td").size(); i++) {

                    HtmlTableDataCell bus = (HtmlTableDataCell) element.getByXPath("td").get(0);
                    HtmlTableDataCell fromTo = (HtmlTableDataCell) element.getByXPath("td").get(1);
                    HtmlTableDataCell reportingTime = (HtmlTableDataCell) element.getByXPath("td").get(2);
                    HtmlTableDataCell departureTime = (HtmlTableDataCell) element.getByXPath("td").get(3);
                    HtmlTableDataCell priceFrom = (HtmlTableDataCell) element.getByXPath("td").get(4);
                    HtmlAnchor linkToSeats = (HtmlAnchor) element.getByXPath("td/a").get(0);

                    fare.setBus(bus.getTextContent());
                    fare.setFromTo(fromTo.getTextContent());
                    fare.setReportingTime(reportingTime.getTextContent());
                    fare.setDepartureTime(departureTime.getTextContent());
                    fare.setPriceFrom(priceFrom.getTextContent());
                    fare.setAvailableSeats(getSeats(linkToSeats.getAttribute("href")));
                    fare.setLink(url);
                }
            }

            fareList.add(fare);
        });

        MyFileWriter.writeToExcel(fareList);
    }

    private synchronized List<String> getSeats(String href) {

        List<String> aveSeats = new ArrayList<>();
        HtmlPage pageSeats = null;
        try {
            pageSeats = webClient.getPage(href);
        } catch (IOException e) {
            Logger.warn("[TEST] == Could not get page with seats [" + href + "]\n" + e.getMessage());
        } finally {
            Optional<List<HtmlElement>> optionalHtmlElements = Optional.ofNullable(pageSeats.getByXPath("//*[@id='seat_aval']"));
            optionalHtmlElements.ifPresent(htmlElements -> htmlElements.forEach(seatElement -> aveSeats.add(seatElement.getTextContent().trim())));

            if (aveSeats.isEmpty())
                aveSeats.add("NULL_SEATS");
        }
        return aveSeats;
    }


}
