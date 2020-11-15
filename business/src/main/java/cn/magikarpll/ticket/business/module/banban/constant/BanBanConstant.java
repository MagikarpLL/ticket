package cn.magikarpll.ticket.business.module.banban.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BanBanConstant {

    //已备案且属于罗湖，福田，龙华，南山的模拟机编号
    public static final String SIMULATOR_NUMBERS = "SG-116,SG-117,SG-114,SG-110,SG-111,SG-119,SG-120,SG-095,SG-096,SG-106,SG-220,SG-098,SG-099,SG-188,SG-189,SG-192,SG-193,SG-182,5G-187,SG-208,SG-218,SG-045,SG-046,SG-062,SG-064,SG-223,SG-224,SG-225,SG-226,SG-243,SG-040,SG-244,SG-246,SG-247,SG-248,SG-201,SG-202,SG-203,SG-209,SG-217,SG-251,SG-252,SG-253,SG-255,SG-256";

    //已备案的模拟机编号
    public static final Set<String> SIMULATOR_NUMBER_SET = new HashSet<>(Arrays.asList(SIMULATOR_NUMBERS.split(",")));


}
