package cn.magikarpll.ticket.business.module.banban.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BanBanConstant {

    //已备案且属于罗湖，福田，龙华，南山的模拟机编号
    public static final String SIMULATOR_NUMBERS = "SG-116,SG-117,SG-114,SG-110,SG-111,SG-119,SG-120,SG-095,SG-096,SG-106,SG-220,SG-098,SG-099,SG-188,SG-189,SG-192,SG-193,SG-182,5G-187,SG-208,SG-218,SG-045,SG-046,SG-062,SG-064,SG-223,SG-224,SG-225,SG-226,SG-243,SG-040,SG-244,SG-246,SG-247,SG-248,SG-201,SG-202,SG-203,SG-209,SG-217,SG-251,SG-252,SG-253,SG-255,SG-256,SG-257,SG-258SG-007,SG-006,SG-005,SG-023,SG-024,SG-025,SG-011,SG-012,SG-013,SG-014,SG-019,SG-020,SG-021,SG-022,SG-140,SG-142,SG-143,SG-144,SG-173,SG-174,SG-137,SG-175,SG-180,SG-181,SG-161,SG-166,SG-167,SG-168";

    //已备案的模拟机编号
    public static final Set<String> SIMULATOR_NUMBER_SET = new HashSet<>(Arrays.asList(SIMULATOR_NUMBERS.split(",")));

    //斑斑驾道常用分隔符
    public static final String BANBAN_DELIMETER = "-";

}
