package com.ooobgy.demo;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ooobgy.income.conf.ConfReader;
import com.ooobgy.income.exception.IllTaxConfException;
import com.ooobgy.income.pojo.OtherTaxConf;
import com.ooobgy.income.pojo.SalaryTaxConf;

public class TaxPlatform extends JApplet{
    private static final long serialVersionUID = 450344345065490509L;
    private SalaryTaxConf stConf;
    private OtherTaxConf otConf;
    private final String defPropFilePath;
    private SinglePanel sp;
    
    public TaxPlatform(String defPropFilePath) throws HeadlessException {
        super();
        this.defPropFilePath = defPropFilePath;
    }

    private JPanel setUpView() throws IllTaxConfException, FileNotFoundException, IOException {
        this.stConf = ConfReader.makeSalaryTaxConf(defPropFilePath);
        this.otConf = ConfReader.makeOtherTaxConf(defPropFilePath);
        sp = new SinglePanel(stConf, otConf, false);
        JPanel singlePanel = sp.setUpView();
        
        JPanel context = new JPanel(new GridLayout(1, 2));
        context.add(singlePanel);
        return context;
    }
    
    @Override
    public void start() {
        JPanel context;
        try {
            context = setUpView();
            Container content = getContentPane();
            content.add(context);
        } catch (IllTaxConfException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        TaxPlatform tf = new TaxPlatform("conf/tax-conf.properties");
        
        tf.start();
        JFrame jf = new JFrame();
        jf.getContentPane().add(tf);
        jf.setTitle("ooobgy个税计算器        (单位:元)");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
}
