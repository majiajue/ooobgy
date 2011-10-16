package com.ooobgy.income.pojo;

import java.math.BigDecimal;

/**
 * 其他税率
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-10-16
 */
public class OtherTaxConf {
    private BigDecimal interestTax;
    private BigDecimal accidentalTax;
    
    public BigDecimal getInterestTax() {
        return interestTax;
    }
    public void setInterestTax(BigDecimal interestTax) {
        this.interestTax = interestTax;
    }
    public BigDecimal getAccidentalTax() {
        return accidentalTax;
    }
    public void setAccidentalTax(BigDecimal accidentalTax) {
        this.accidentalTax = accidentalTax;
    }
}
