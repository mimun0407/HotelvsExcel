package org.hotel.Service;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hotel.Model.Customer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
   static Map<Integer, Customer> customerMap = new HashMap<>();

    public void loadCustomerFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                if (idCell == null || nameCell == null) break;
                int id = (int) idCell.getNumericCellValue();
                String name = nameCell.getStringCellValue();
                customerMap.put(id, new Customer(id, name));

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi đọc file Excel: " + filePath);
        }
    }


    public void getAllCustomer() {
        String leftAlignFormat = "| %-15s | %-4d |%n";

        System.out.format("+-----------------+------+%n");
        System.out.format("| Column name     | ID   |%n");
        System.out.format("+-----------------+------+%n");
        for (Customer value : customerMap.values()) {
            System.out.format(leftAlignFormat, value.getCustomerName(), value.getIdCustomer());
        }
        System.out.format("+-----------------+------+%n");
    }

    public Customer getCustomerById(int id) {
        return customerMap.get(id);
    }
    public static Customer getCustomerByName(String name) {
        return customerMap.values().stream().filter(value -> value.getCustomerName().equals(name)).findFirst().orElse(null);
    }
}
