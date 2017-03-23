/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author gta1
 */
public class SchemaManagerTest {

    private SchemaManager schemaManager;

    private SimpleDateFormat df;

    @Before
    public void setUp() {
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
    }

    @Test
    public void testGetFirstDateOfFirstWeekInMonth() throws ParseException {
        SchemaManager instance = new SchemaManager();

        String dateAsString = "2017-02-01";
        Date startDate = df.parse(dateAsString);
        int weeksToAdd = 0;

        String firstDateAsString = "2017-01-30";
        Date firstDateInFirstWeek = df.parse(firstDateAsString);

        Date result = instance.getFirstDateOfWeek(startDate, weeksToAdd);

        assertEquals(firstDateInFirstWeek, result);
    }

    @Test
    public void testGetLastDateOfFirstWeekInMonth() throws ParseException {
        SchemaManager instance = new SchemaManager();

        String dateAsString = "2017-02-01";
        Date startDate = df.parse(dateAsString);
        int weeksToAdd = 0;

        String secondDateAsString = "2017-02-03";
        Date firstDateInFirstWeek = df.parse(secondDateAsString);
        Calendar first = Calendar.getInstance();
        first.setTime(instance.getFirstDateOfWeek(startDate, weeksToAdd));

        Date result = instance.getLastDateOfWeek(first);

        assertEquals(firstDateInFirstWeek, result);
    }
}
