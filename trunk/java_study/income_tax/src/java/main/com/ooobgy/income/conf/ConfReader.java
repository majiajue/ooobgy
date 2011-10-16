package com.ooobgy.income.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.ooobgy.comm.util.PropertiesUtility;
import com.ooobgy.income.consts.IncomeConsts;
import com.ooobgy.income.exception.IllTaxConfException;
import com.ooobgy.income.pojo.OtherTaxConf;
import com.ooobgy.income.pojo.SalaryTaxConf;

/**
 * 读取转换配置税率计算配置
 * 
 * @author Frogcherry 周晓龙
 * @Email frogcherry@gmail.com
 * @created 2011-10-16
 */
public class ConfReader {
    /**
     * 禁用构造，只提供静态工具方法
     */
    private ConfReader() {
    }

    public static SalaryTaxConf makeSalaryTaxConf(String filePath) throws IllTaxConfException, FileNotFoundException, IOException{
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(filePath)));
        
        return makeSalaryTaxConf(properties);
    }
    
    public static OtherTaxConf makeOtherTaxConf(String filePath) throws IllTaxConfException, FileNotFoundException, IOException{
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(filePath)));
        
        return makeOtherTaxConf(properties);
    }
    
    /**
     * 从配置量生成SalaryTaxConf
     * 
     * @param properties
     * @return
     */
    public static SalaryTaxConf makeSalaryTaxConf(Properties properties) throws IllTaxConfException {
        SalaryTaxConf stc = new SalaryTaxConf();

        try {
            stc.setSalaryBase(getDecimalProp(properties, IncomeConsts.SALARY_TAX_BASE));
            stc.setAvgSalary(getDecimalProp(properties, IncomeConsts.SALARY_AVG));
            
            stc.setComPension(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_PENSION).multiply(IncomeConsts.PERCENT_1));
            stc.setComMedicare(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_MEDICARE).multiply(IncomeConsts.PERCENT_1));
            stc.setComUnEmp(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_UNEMP).multiply(IncomeConsts.PERCENT_1));
            stc.setComInjure(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_INJURE).multiply(IncomeConsts.PERCENT_1));
            stc.setComMaternity(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_MATERNITY).multiply(IncomeConsts.PERCENT_1));
            stc.setComAccFund(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_ACCFUND).multiply(IncomeConsts.PERCENT_1));

            stc.setIndvPension(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_PENSION).multiply(IncomeConsts.PERCENT_1));
            stc.setIndvMedicare(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_MEDICARE).multiply(IncomeConsts.PERCENT_1));
            stc.setIndvUnEmp(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_UNEMP).multiply(IncomeConsts.PERCENT_1));
            stc.setIndvAccFund(getDecimalProp(properties, IncomeConsts.SS_COM + IncomeConsts.DOT + IncomeConsts.SS_ACCFUND).multiply(IncomeConsts.PERCENT_1));
            
            
            Properties lvProps = PropertiesUtility.getSubConfiguration(properties, IncomeConsts.SALARY_TAX_LEVEL);
            Map<BigDecimal, BigDecimal> taxLevlRates = new LinkedHashMap<BigDecimal, BigDecimal>();
            for (int i = 1; i <= lvProps.size(); i++) {
                String[] items = lvProps.getProperty("" + i).split(IncomeConsts.SALARY_TAX_LEVEL_SEP);
                taxLevlRates.put(new BigDecimal(items[0]), (new BigDecimal(items[1])).multiply(IncomeConsts.PERCENT_1));
            }
            stc.setTaxLevlRates(taxLevlRates);
        } catch (NumberFormatException e) {
            throw new IllTaxConfException("个税征收等级配置数值格式错误", e);
        } //当配置不存在时或个税等级配置错误还可能抛出IllTaxConfException，已包含所需信息，让它自然抛出即可
        
        return stc;
    }

    public static OtherTaxConf makeOtherTaxConf(Properties properties) throws IllTaxConfException {
        OtherTaxConf otc = new OtherTaxConf();
        try{
            otc.setAccidentalTax(getDecimalProp(properties, IncomeConsts.ACCIDENTAL_TAX));
            otc.setInterestTax(getDecimalProp(properties, IncomeConsts.INTEREST_TAX));
        } catch (NumberFormatException e) {
            throw new IllTaxConfException("个税征收等级配置数值格式错误", e);
        }//当配置不存在时或个税等级配置错误还可能抛出IllTaxConfException，已包含所需信息，让它自然抛出即可
        
        return otc;
    }
    
    /**
     * 返回{@link BigDecimal}类型的配置信息
     * @param properties
     * @param key
     * @return
     */
    private static BigDecimal getDecimalProp(Properties properties, String key) {
        return new BigDecimal(PropertiesUtility.getStringProperty(
                properties, key, true,
                IllTaxConfException.class));
    }
}
