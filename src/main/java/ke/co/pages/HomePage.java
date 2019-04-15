package ke.co.pages;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HomePage {

    private WebClient webClient;
    private HtmlPage page;

    public HomePage(WebClient webClient) {
        this.webClient = webClient;
    }

    public void startSearch(List<String> cities) {
        List<String> urls = Collections.synchronizedList(new ArrayList<>());

        Collections.synchronizedList(cities).parallelStream().forEach(from -> {
            for (String to : cities) {
                if (!from.equals(to)) {
                    String url = "https://www.modern.co.ke/index.php/online/bus_view?fromtwn="
                            + from
                            + "&totwn="
                            + to
                            + "&traveldate="
                            + "22-02-2019" //todo
                            + "&source_check=2&currency=KES&traveldate_r=";

                    try {
                        page = webClient.getPage(url);
                    } catch (IOException e) {
                        Logger.warn("[TEST] == Could not get the target page for city [" + from + " - " + to + "]\n" + e.getMessage());
                    }

                    Optional<DomNode> optional = Optional.ofNullable(page.querySelector("div.grid_7.no-margin > table > tbody"));
                    optional.ifPresent(node -> urls.add(url));
                }
            }
        });

        Logger.info("[TEST] == Collecting URLs is finished.");

        BookingPage bookingPage = new BookingPage(webClient);
        bookingPage.getSchedule(urls);
    }

}

