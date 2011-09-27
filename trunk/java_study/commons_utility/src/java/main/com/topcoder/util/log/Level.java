package com.topcoder.util.log;

/**
 * 描述log的等级枚举，参看log4j的level
 * @author Frogcherry 周晓龙 frogcherry@gmail.com
 * @version 1.0.0
 * @since 1.0.0
 */
public enum Level {
    /**
     * debug级别log
     */
    DEBUG,
    /**
     * info级别log
     */
    INFO,
    /**
     * warn级别log
     */
    WARN,
    /**
     * error级别log
     */
    ERROR,
    /**
     * fatal级别log
     */
    FATAL,
    /**
     * 全部log
     */
    ALL,
    /**
     * 关闭log
     */
    OFF
}
