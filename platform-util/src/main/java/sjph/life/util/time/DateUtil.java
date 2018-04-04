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

    private ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

        @Override
        public DateFormat get() {
            return super.get();
        }

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
     * @throws ParseException
     */
    public String convertDateToString(Date date) throws ParseException {
        return df.get().format(date);
    }
}
