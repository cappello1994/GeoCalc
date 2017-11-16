package cis.gvsu.edu.geocalculator;

import org.joda.time.format.DateTimeFormatter;
import org.parceler.Parcel;

@Parcel
public class LocationLookup {
    String _key;
    String timestamp;
    double origLat;
    double origLng;
    double endLat;
    double endLng;

    public String getTimestamp() {return timestamp; }
    public String get_key() {
        return _key;
    }

    public double getOrigLat() {
        return origLat;
    }

    public double getOrigLng() {
        return origLng;
    }

    public double getEndLat() {
        return endLat;
    }

    public double getEndLng() {
        return endLng;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public void setOrigLat(double origLat) {
        this.origLat = origLat;
    }

    public void setOrigLng(double origLng) {
        this.origLng = origLng;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}