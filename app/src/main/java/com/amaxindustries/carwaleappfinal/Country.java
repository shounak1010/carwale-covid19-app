package com.amaxindustries.carwaleappfinal;

import java.util.Comparator;
import java.util.Date;

public class Country {
    public String country;
    public long confirmed;
    public long recovered;
    public long critical;
    public long deaths;
    public long total;
    public String latitude;
    public String longitude;
    public Date lastChange;
    public Date lastUpdate;

    public Country() {
    }

    public Country(String country, long confirmed, long recovered, long critical, long deaths,long total, String latitude, String longitude, Date lastChange, Date lastUpdate) {
        this.country = country;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.critical = critical;
        this.deaths = deaths;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastChange = lastChange;
        this.lastUpdate = lastUpdate;
    }

    public static Comparator<Country> CountryNameComparatorAsc = new Comparator<Country>() {

        public int compare(Country s1, Country s2) {
            String Country1 = s1.getCountry().toUpperCase();
            String Country2 = s2.getCountry().toUpperCase();

            //ascending order
            return Country1.compareTo(Country2);

        }
    };

    public static Comparator<Country> CountryNameComparatorDsc = new Comparator<Country>() {

        public int compare(Country s1, Country s2) {
            String Country1 = s1.getCountry().toUpperCase();
            String Country2 = s2.getCountry().toUpperCase();

            //descending order
            return Country2.compareTo(Country1);

        }
    };
    /*Comparator for sorting the list*/
    public static Comparator<Country> CountryTotalComparator = new Comparator<Country>() {

        public int compare(Country s1, Country s2) {

            double Total1 = s1.getConfirmed();
            double Total2 = s2.getConfirmed();

            /*For ascending order*/
            // return Comfirmed1-Comfirmed2;

            /*For descending order*/
            return (int) (Total2 - Total1);
        }
    };



    @Override
    public String toString() {
        return country + "/" + confirmed + "/" + deaths + "/" + recovered;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    public double getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(long critical) {
        this.critical = critical;
    }

    public double getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(long Total) {
        this.total = total;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
