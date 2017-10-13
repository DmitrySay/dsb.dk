package com.epam.dsb.dk.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ApachePOIExcelReader {

    FileInputStream excelFile;
    Workbook workbook;


    public ApachePOIExcelReader() {
    }

    public ApachePOIExcelReader(String pathTofile) {
        try {
            excelFile = new FileInputStream(new File(pathTofile));
            workbook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    Method read from Excel file key and value from first and second columns
    and put them to HashMap. The data mast have been organised before use
     */
    public HashMap<String, String> readExcelFileToHashMap(int sheet)  {
        if (workbook != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            Sheet datatypeSheet = getSheet(sheet);
            Iterator<Row> iterator = datatypeSheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                String parameter = null;
                String value = null;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            parameter = getCellText(cell);
                            break;
                        case 1:
                            value = getCellText(cell);
                            break;
                    }

                }
                hashMap.put(parameter, value);
            }
            return hashMap;
        } else {
            try {
                throw new Exception("Workbook is not defined");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Sheet getSheet(int var) {
        return workbook.getSheetAt(var);
    }

    public Sheet getSheet(String var) {
        return workbook.getSheet(var);
    }


    public String getCellText(Cell cell) {
        String result = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    result = cell.getDateCellValue().toString();
                } else {
                    result = Double.toString(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                result = Boolean.toString(cell.getBooleanCellValue());
                break;
            case FORMULA:
                result = cell.getCellFormula().toString();
                break;
            case BLANK:
                break;
        }
        return result;
    }

    public void close(){
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
