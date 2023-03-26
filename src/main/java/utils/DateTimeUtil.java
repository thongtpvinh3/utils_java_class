package utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class DateTimeUtil {
    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

    /*

     */
    public static List<LocalDate> getDateBetweenTwoDate(String startDate, String endDate) {
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);
        long numOfDaysBetween = ChronoUnit.DAYS.between(sDate, eDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween + 1)
                .mapToObj(sDate::plusDays)
                .collect(Collectors.toList());
    }

    private static String getListDateStr(String startDate, String endDate, DateTimeFormatter formatter) {
        List<LocalDate> dateBetweenTwoDate = getDateBetweenTwoDate(startDate, endDate);
        String result = dateBetweenTwoDate.stream()
                .map(formatter::format)
                .collect(Collectors.toList())
                .stream().collect(Collectors.joining("','", "'", "'"));

        System.out.println(result);
        return result;
    }

    public static String getListDateStrYYMMDD(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return getListDateStr(startDate, endDate, formatter);
    }

    public static String convertDate2DateTimeStr(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(timestamp));
    }

    public static String getOffsetDateTimeNow() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);
        return offsetDateTime.format(dtf);
    }
}
