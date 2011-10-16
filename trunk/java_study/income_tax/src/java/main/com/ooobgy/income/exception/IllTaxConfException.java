package com.ooobgy.income.exception;

/**
 * 表示读取到错误配置的异常
 * @author Frogcherry 周晓龙
 * @Email  frogcherry@gmail.com
 * @created  2011-10-16
 */
public class IllTaxConfException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = -6746594246210543691L;

    public IllTaxConfException() {
        super();
    }

    public IllTaxConfException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public IllTaxConfException(String arg0) {
        super(arg0);
    }

    public IllTaxConfException(Throwable arg0) {
        super(arg0);
    }

}
