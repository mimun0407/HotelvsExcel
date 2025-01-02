package org.hotel.Service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hotel.Model.Customer;
import org.hotel.Model.Room;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RoomService {
    static Map<Integer, Room> roomMap=new HashMap<>();
    public void loadCRoomFromExcel(String filePath) {
        try (Workbook workbook = new XSSFWorkbook(new File(filePath))) {
            Sheet sheet = workbook.getSheetAt(1);
            for (Row row : sheet) {

                Cell idCell = row.getCell(0);
                Cell nameCell = row.getCell(1);
                Cell trueFalseCell = row.getCell(2);
                Cell priceCell = row.getCell(3);

                if (idCell == null || nameCell == null || trueFalseCell == null || priceCell == null) {
                    break;
                }

                int id = (int) idCell.getNumericCellValue();
                String name = nameCell.getStringCellValue();
                boolean trueFalse;

                if (trueFalseCell.getCellType() == CellType.BOOLEAN) {
                    trueFalse = trueFalseCell.getBooleanCellValue();
                } else if (trueFalseCell.getCellType() == CellType.STRING) {
                    trueFalse = Boolean.parseBoolean(trueFalseCell.getStringCellValue());
                } else {
                    System.out.println("Lỗi định dạng tại hàng: " + (row.getRowNum() + 1));
                    continue;
                }
                int price = (int) priceCell.getNumericCellValue();

                roomMap.put(id, new Room(id, name, trueFalse, price));
            }

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file Excel: " + e.getMessage());
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllRoom(){
        String leftAlignFormat = "| %-15s | %-4d |%6s|%7s$|%n";

        System.out.format("+-----------------+------+-------+-------+%n");
        System.out.format("| Column name     | ID   |booked | Price |%n");
        System.out.format("+-----------------+------+-------+-------+%n");
        for(Room r:roomMap.values()){
            System.out.format(leftAlignFormat, r.getRoomName(), r.getIdRoom(), r.isBooked(), r.getPrice());
        }
        System.out.format("+-----------------+------+-------+-------+%n");
    }

    public Room getRoomById(int id) {
        return roomMap.get(id);
    }

    public static Room getRoomByName(String name) {
        return roomMap.values().stream().filter(room -> room.getRoomName().equals(name)).findFirst().orElse(null);
    }

    public void RoomNotBooked() {
        String leftAlignFormat = "| %-15s | %-4d |%n";
        System.out.format("+-----------------+------+%n");
        System.out.format("| Column name     | ID   |%n");
        System.out.format("+-----------------+------+%n");
        for (Room r : roomMap.values()) {
            if (!r.isBooked()) {
                System.out.format(leftAlignFormat, r.getRoomName(), r.getIdRoom());
            }
        }
        System.out.format("+-----------------+------+%n");
    }
}
