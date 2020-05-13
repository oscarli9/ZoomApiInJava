package zoomapi;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class UtilTest extends Assert {
    Util util = new Util();

    @Test
    public void isStr() {
        String str = "It is a string.";
        assertTrue(util.isStrType(str));
    }

    @Test
    public void isNotStr() {
        int num = 8;
        Object obj = new Object();
        assertFalse(util.isStrType(num));
        assertFalse(util.isStrType(obj));
    }

    @Test
    public void dateToStrTest() {
        Date date = new Date(111, Calendar.OCTOBER, 18, 22, 10, 45);
        assertEquals("2011-10-18 22:10:45", util.dateToStr(date));
    }
}
