package ke.co;

import java.util.List;

public class Fare {

    private String bus;
    private String fromTo;
    private String reportingTime;
    private String departureTime;
    private String priceFrom;
    private List<String> availableSeats;
    private String link;

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getFromTo() {
        return fromTo;
    }

    public void setFromTo(String fromTo) {
        this.fromTo = fromTo;
    }

    public String getReportingTime() {
        return reportingTime;
    }

    public void setReportingTime(String reportingTime) {
        this.reportingTime = reportingTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public String getAvailableSeats() {
        return availableSeats.toString();
    }

    public void setAvailableSeats(List<String> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Fare{" +
                "bus='" + bus + '\'' +
                ", fromTo='" + fromTo + '\'' +
                ", reportingTime='" + reportingTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", priceFrom='" + priceFrom + '\'' +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
