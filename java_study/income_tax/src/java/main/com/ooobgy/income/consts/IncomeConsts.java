package com.ooobgy.income.consts;

import java.math.BigDecimal;

/**
 * 公共常量配置
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-10-16
 */
public class IncomeConsts {

    //第一部分，配置文件常量配置
    /** 配置文件配置项分隔符 . */
    public static final String DOT = ".";
    /** 税务部分配置 */
    public static final String TAX = "tax";
    /** 利息所得税税率 */
    public static final String INTEREST_TAX = TAX + DOT + "interest";
    /** 意外所得税税率 */
    public static final String ACCIDENTAL_TAX = TAX + DOT + "accidental";
    /** 月薪个人所得税配置 */
    public static final String SALARY_TAX = TAX + DOT + "salary";
    /** 月薪个税起征额 */
    public static final String SALARY_TAX_BASE = SALARY_TAX + DOT + "base";
    /** 月薪个税征收分级配置 */
    public static final String SALARY_TAX_LEVEL = SALARY_TAX + DOT + "level";
    /** 月薪个税征收分级内 <起征点，税率> 分隔符,例如xx-yy表示征收部分超过“xx”，税率百分比为“yy” */
    public static final String SALARY_TAX_LEVEL_SEP = "-";
    
    /** 社保部分配置 */
    public static final String SOCIAL_SEC = "ss";
    /** 社保部分配置 */
    public static final String SALARY_AVG = SOCIAL_SEC + DOT + "avgs";
    /** 公司社保部分配置 */
    public static final String SS_COM = SOCIAL_SEC + DOT + "com";
    /** 个人社保部分配置 */
    public static final String SS_INDV = SOCIAL_SEC + DOT + "indv";
    /** 养老保险 */
    public static final String SS_PENSION = "pension";
    /** 医疗保险 */
    public static final String SS_MEDICARE = "medicare";
    /** 失业保险 */
    public static final String SS_UNEMP = "unemp";
    /** 工伤保险 */
    public static final String SS_INJURE = "injure";
    /** 生育保险 */
    public static final String SS_MATERNITY = "maternity";
    /** 公积金 */
    public static final String SS_ACCFUND = "accfund";
    
    /** 常数100 */
    public static final BigDecimal HUNDRED = new BigDecimal("100");
    /** 常数0.01 */
    public static final BigDecimal PERCENT_1 = new BigDecimal("0.01");
    /** 常数0 */
    public static final BigDecimal ZERO = new BigDecimal("0");
}
