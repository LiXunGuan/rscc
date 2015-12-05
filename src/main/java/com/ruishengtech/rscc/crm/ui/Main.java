package com.ruishengtech.rscc.crm.ui;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yaoliceng on 2015/7/8.
 */
public class Main {

    public static void main(String[] args) throws Exception {


        XSSFWorkbook
                workbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\yaoliceng\\Desktop\\number.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> i = sheet.rowIterator();

        while (i.hasNext()){

           Row row = i.next();
           String name = String.valueOf((int)row.getCell(0).getNumericCellValue());
           String password = String.valueOf(row.getCell(2).getStringCellValue());

            File file = new File("D://number/"+name+".xml");
            FileUtils.write(file, getContent(name,password));
        }

    }

    public static String getContent(String name,String password){
        StringBuilder stringBuilder  =new StringBuilder();

        stringBuilder.append("<include>\n" +
                "  <gateway name=\""+name+"\">\n" +
                "  <param name=\"username\" value=\"+8621"+name+"@ims.sh.chinamobile.com\"/>\n" +
                "  <param name=\"realm\" value=\"ims.sh.chinamobile.com\"/> \n" +
                "  <param name=\"from-user\" value=\"+8621"+name+"\"/>\n" +
                "  <param name=\"from-domain\" value=\"ims.sh.chinamobile.com\"/>\n" +
                "  <param name=\"password\" value=\""+password+"\"/>\n" +
                "  <param name=\"register-proxy\" value=\"221.181.108.137\"/>\n" +
                "  <param name=\"outbound-proxy\" value=\"221.181.108.137\"/>\n" +
                "  <param name=\"register\" value=\"true\"/>\n" +
                "  </gateway>\n" +
                "</include>\n");

        return stringBuilder.toString();
    }

}
