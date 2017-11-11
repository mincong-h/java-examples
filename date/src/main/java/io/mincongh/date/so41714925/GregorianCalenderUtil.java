package io.mincongh.date.so41714925;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Mincong Huang
 */
public class GregorianCalenderUtil {

  private GregorianCalenderUtil() {
    // Utility class, do not instantiate
  }

  public static XMLGregorianCalendar unmarshal(String value)
      throws ParseException, DatatypeConfigurationException {

    final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(inputFormat.parse(value));
    return DatatypeFactory.newInstance().newXMLGregorianCalendar(
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH) + 1,
        cal.get(Calendar.DAY_OF_MONTH),
        DatatypeConstants.FIELD_UNDEFINED,
        DatatypeConstants.FIELD_UNDEFINED,
        DatatypeConstants.FIELD_UNDEFINED,
        DatatypeConstants.FIELD_UNDEFINED,
        DatatypeConstants.FIELD_UNDEFINED
    );
  }
}
