package app;

/*
 * 
 *         Transforms the date (String) into three integers(Year, Month, Day)
 */
public class BasicDate implements Date {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MAXMONTH = 12;
	private static final int MINMONTH = 1;


	private static final int NUM_FIELDS = 3;

	private int[] rawDate;

	private String string;
	/***
	 * Builds a new raw date object.
	 * 
	 * @param date -- a string of the form N1-N2-N3, where N1,N2,N3 are positive
	 *             numbers representable as integers.
	 */
	public BasicDate(String date) {
		string = date;
		String[] split = date.split("-");
		rawDate = new int[NUM_FIELDS];

		for (int i = 0; i < split.length; i++) {
			rawDate[i] = Integer.parseInt(split[i].trim());
		}

	}

	/**
	 * Checks if the date is valid
	 * 
	 * @return boolean value, true if valid, false if not.
	 */
	public boolean isValid() {
		return (isDayValid() && isMonthValid());
	}

	/**
	 * Checks if month is valid
	 * 
	 * @return boolean value, true if valid, false if not.
	 */
	private boolean isMonthValid() {
		return (getMonth() >= MINMONTH && getMonth() <= MAXMONTH);
	}

	/**
	 * Checks if day is valid
	 * 
	 * @return boolean value, true if valid, false if not.
	 */
	private boolean isDayValid() {
		boolean valid = false;
		switch (getMonth()) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			valid = getDay() > 0 && getDay() <= 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			valid = getDay() > 0 && getDay() <= 30;
			break;
		case 2:
			if (isLeapYear())
				valid = getDay() > 0 && getDay() <= 29;
			else
				valid = getDay() > 0 && getDay() <= 28;

			break;

		}

		return valid;
	}

	/**
	 * Checks if the year is a Leap Year
	 * 
	 * @return boolean value, true if valid, false if not.
	 */
	private boolean isLeapYear() {
		int year = getYear();
		return (year % 4 == 0 || year % 400 == 0) && year % 100 != 0;

	}

	/**
	 * Returns the year field of this date, assuming the string used in the
	 * constructor was a valid date (i.e., isValid() ).
	 * 
	 */
	public int getYear() {
		return rawDate[2];
	}

	/**
	 * Returns the day field of this date, assuming the string used in the
	 * constructor was a valid date (i.e., isValid() ).
	 * 
	 */
	public int getDay() {
		return rawDate[0];
	}

	/**
	 * Returns the month field of this date, assuming the string used in the
	 * constructor was a valid date (i.e., isValid() ).
	 * 
	 */
	public int getMonth() {
		return rawDate[1];
	}

	@Override
	public int compareTo(Date o) {
		int behind = -1;
		int equal = 0;
		int front = 1;

		if(getYear() > o.getYear())
			return front;
		if(getYear()< o.getYear())
			return behind;
		
		if(getMonth() > o.getMonth())
			return front;
		if(getMonth()< o.getMonth())
			return behind;
		
		if(getDay()> o.getDay())
			return front;
		if(getDay()< o.getDay())
			return behind;
		return equal;

	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Date) {
			return (getYear() == ((Date) obj).getYear() && getMonth() == ((Date) obj).getMonth() && getDay() == ((Date) obj).getDay());
		}else{
			return false;
		}
	}
	@Override
	public String stringDate(){
		return string;
	}
}
