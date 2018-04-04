package sjph.life.util.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Shaohui Guo
 *
 */
public class DateUtil {

    private ThreadLocal<DateFormat> df;

    /**
     * @param lifeDateFormat 
     * 
     */
    public DateUtil(LifeDateFormat lifeDateFormat) {
        df = new ThreadLocal<DateFormat>() {

            @Override
            public DateFormat get() {
                return super.get();
            }

            @Override
            protected DateFormat initialValue() {
                return new SimpleDateFormat(lifeDateFormat.getFormat());
            }

            @Override
            public void remove() {
                super.remove();
            }

            @Override
            public void set(DateFormat value) {
                super.set(value);
            }
        };
    }

    /**
     * @param dateString
     * @return
     * @throws ParseException
     */
    public Date convertStringToDate(String dateString) throws ParseException {
        return df.get().parse(dateString);
    }

    /**
     * @param date
     * @return
     */
    public String convertDateToString(Date date) {
        return df.get().format(date);
    }
}
