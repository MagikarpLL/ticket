package cn.magikarpll.ticket.common.utils;

import cn.magikarpll.ticket.common.constants.CommonConstants;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    /**
     * Hours per day.
     */
    public static final int HOURS_PER_DAY = 24;
    /**
     * Minutes per hour.
     */
    public static final int MINUTES_PER_HOUR = 60;
    /**
     * Minutes per day.
     */
    public static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
    /**
     * Seconds per minute.
     */
    public static final int SECONDS_PER_MINUTE = 60;
    /**
     * Seconds per hour.
     */
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    /**
     * Seconds per day.
     */
    public static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

    /**
     * 计算给定现在时间与指定时间的时间差，结果单位为秒
     * 若现在时间小于指定时间，则直接相减
     * 若现在时间大于指定时间，则与下一周期的指定时间相减
     * @param scheduleTime  参数的时间格式必须为  '10:15:30'
     * @return
     */
    public static Long computeTimeDurationBeforeScheduleEveryday(String scheduleTime){
        LocalTime localScheduleTime = LocalTime.parse(scheduleTime, DateTimeFormatter.ISO_LOCAL_TIME);
        return computeTimeDurationBeforeScheduleEveryday(localScheduleTime);
    }

    /**
     * 计算给定时间与现在的时间差，结果单位为秒
     * @param scheduleTime
     * @return
     */
    public static Long computeTimeDurationBeforeScheduleEveryday(LocalTime scheduleTime){
        if(scheduleTime.isBefore(LocalTime.now())){
            return Duration.between(scheduleTime, LocalTime.now()).getSeconds();
        }else{
            return SECONDS_PER_DAY - Duration.between(scheduleTime, LocalTime.now()).getSeconds();
        }
    }

    /**
     * 返回指定日期的日期字符串  11-11
     * 如果参数为空则默认返回当日
     * 如果参数为数字，则为加上delay天的日期
     * @param delay
     * @return
     */
    public static String getSpecifyDate(Integer delay){
        LocalDate localDate = LocalDate.now();
        if(null == delay){
            return getMonthDateOfDate(localDate);
        }else{
            localDate.plusDays(delay);
            return getMonthDateOfDate(localDate);
        }

    }


    /**
     * 返回指定日期的 月份日期格式的字符串 11-11
     * @param localDate
     * @return
     */
    public static String getMonthDateOfDate(LocalDate localDate){
        return localDate.getMonth() + "-" + localDate.getDayOfMonth();
    }

    /**
     * 判断给定的11-11格式的日期是否为工作日周一到周五
     * @param monthDay
     * @return
     */
    public static Boolean isWeekDay(String monthDay){
        String[] monthDayList = monthDay.split("-");
        if(null == monthDayList || monthDayList.length != 2){
            return null;
        }
        if(!StringUtils.isNumeric(monthDayList[0]) || !StringUtils.isNumeric(monthDayList[1])){
            return null;
        }
        int[] monthDayInt = Arrays.stream(monthDayList).mapToInt(Integer::parseInt).toArray();
        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), monthDayInt[0] , monthDayInt[1]);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return !(DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek));
    }

    /**
     * 判断给定的时间周期是否在给定的时间周期范围内
     * @param time  09:00-10:00
     * @param period 09:00-15:00
     * @return
     */
    public static Boolean checkTimeWithinPeroid(String time, String period){
        String[] times = time.split(CommonConstants.HYPHEN_DELIMETER);
        String[] periods = period.split(CommonConstants.HYPHEN_DELIMETER);
        if(times.length!=2 || periods.length!=2){
            return null;
        }
        if(times[0].compareTo(periods[0])<0){
            return false;
        }
        if(times[1].compareTo(periods[1])>0){
            return false;
        }
        return true;
    }







}
