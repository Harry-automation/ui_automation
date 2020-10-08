package dataProviders.excelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExcelReader {
    static Logger log = LogManager.getLogger(ExcelReader.class);

    public static final String PATH = System.getProperty("user.dir")
            + "\\src\\test\\java\\dataProviders\\testData\\" + "CreateAccountData" + ".xlsx";
    private static FileInputStream fileInputStream;
    public static XSSFWorkbook wb;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static Map<String, List<String>> dataMap;

    static {
        intializeReader();
        loadDataMap();
    }

    public static void intializeReader() {
        dataMap = new HashMap<String, List<String>>();
        loadExcelFile(PATH);
    }

    public static void loadExcelFile(String path) {
        try {
            File file = new File(path);
            fileInputStream = new FileInputStream(file);
            wb = new XSSFWorkbook(fileInputStream);
            sheet = wb.getSheetAt(0);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel File not found at " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDataMap() {
        try{

        for (int rowIndex = 1; rowIndex < sheet.getLastRowNum() + 1; rowIndex++) {
            row = sheet.getRow(rowIndex);
            cell = row.getCell(1);
            cell.setCellType(CellType.STRING);
            String key = row.getCell(1).getStringCellValue();

            List<String> rowValues = new ArrayList<String>();
            for (int columnIndex = 2; columnIndex < row.getLastCellNum(); columnIndex++) {
                cell = row.getCell(columnIndex);
                cell.setCellType(CellType.STRING);
                String cellValue = cell.getStringCellValue();
                rowValues.add(cellValue);
                dataMap.put(key, rowValues);
            }
        }
    } catch(Exception e){
        System.out.println(e);}
    }

    public static Map<String, List<String>> getDataMap() {
        return dataMap;
    }

    public static List<String> getListOfValues(String key) throws Exception {
        List<String> myVal = getDataMap().get(key);
        return myVal;
    }

    public static String getDatasetValue(String key, int dataset) throws Exception {

        List<String> listOfValues = getListOfValues(key);
        if (listOfValues == null || listOfValues.isEmpty() || listOfValues.size() < dataset - 1) {
            log.info("The list of test data values from Excel for key =" + key + " is empty!");
            return "";
        }
        return listOfValues.get(dataset - 1);
    }

}