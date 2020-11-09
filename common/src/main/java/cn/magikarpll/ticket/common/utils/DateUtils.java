package cn.magikarpll.ticket.common.utils;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
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



}
