package org.hotel.Service;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hotel.Model.HotelSer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HotelService {
    Map<Integer, HotelSer> hotelSerMap = new HashMap<>();

    public void loadHotelSerFromExcel(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(2);

        for (Row row : sheet) {
            Cell idCell = row.getCell(0);
            Cell priceCell = row.getCell(1);
            Cell nameCell = row.getCell(2);
            if (idCell == null || priceCell == null || nameCell == null) {
                break;
            }
            int id = (int) idCell.getNumericCellValue();
            int price = (int) priceCell.getNumericCellValue();
            String serviceName = nameCell.getStringCellValue();
            hotelSerMap.put(id, new HotelSer(id, price, serviceName));

        }

        workbook.close();
        inputStream.close();
    }

    public void allHotelSer() {
        String leftAlignFormat = "| %-17s | %-4d |%6d $|%n";
        System.out.format("+-------------------+------+--------+%n");
        System.out.format("|  Column name      | ID   |   price|%n");
        System.out.format("+-------------------+------+--------+%n");
        for (HotelSer hotelSer : hotelSerMap.values()) {
            System.out.format(leftAlignFormat, hotelSer.getSer_name(), hotelSer.getSer_id(), hotelSer.getSer_price());
        }
        System.out.format("+-------------------+------+--------+%n");
    }

    public HotelSer getHotelSer(int id) {
        return hotelSerMap.get(id);
    }
}