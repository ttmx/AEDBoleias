package app;

import java.io.Serializable;

public interface Date extends Serializable, Comparable<Date> {

    boolean isValid();
    int getYear();
    int getMonth();
    int getDay();
    int compareTo(Date o);
    String stringDate();
}
