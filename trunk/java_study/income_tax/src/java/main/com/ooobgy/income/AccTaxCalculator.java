package com.ooobgy.income;

import java.math.BigDecimal;

import com.ooobgy.income.pojo.OtherTaxConf;

/**
 * 偶然所得收入，个税计算器
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-10-18
 */
public class AccTaxCalculator {
    /**
     * 禁用构造，提供static工具api
     */
    private AccTaxCalculator() {
    }
    
    public static BigDecimal calculateTax(BigDecimal income, OtherTaxConf conf){
        return income.multiply(conf.getAccidentalTax());
    }
    
    public static BigDecimal calculatePostTaxIncome(BigDecimal income, OtherTaxConf conf){
        return income.subtract(calculateTax(income, conf));
    }
}
