package org.hotel.Service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hotel.Model.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BookingService {

    static Map<Integer, Bookings> bookings = new HashMap<>();
    Map<Integer, Bill> bills = new HashMap<>();
    Map<Integer, RoomCustomer> roomCustomerMap = new HashMap<>();


    public void getHotelSerByCus(Customer customer){
        List<Bookings> bookingsList=getBookings(customer);
        for(Bookings booking:bookingsList){
            String hotelSer= booking.getHotelSer().getSer_name();
            int price=booking.getHotelSer().getSer_price();
            String name=booking.getCustomer().getCustomerName();
            System.out.println("History");
            System.out.println("Customer name: "+name);
            System.out.println("Hotel Ser: "+hotelSer);
            System.out.println("Price: "+price);
        }
    }
    public void getAllBill(String path) throws IOException, ParseException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Bills");
        for (Row row : sheet) {
            Cell idBill=row.getCell(0);
            Cell roomName=row.getCell(1);
            Cell date=row.getCell(3);
            Cell price=row.getCell(2);
            Cell userName=row.getCell(4);
            if (idBill==null){
                continue;
            }
            int id=(int) idBill.getNumericCellValue();
            String name=roomName.getStringCellValue();
            String datetime=date.getStringCellValue();
            int value=(int) price.getNumericCellValue();
            String customerName=userName.getStringCellValue();
            Customer customer=CustomerService.getCustomerByName(customerName);
            Room room=RoomService.getRoomByName(name);
            Date date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(datetime);
            bills.put(id, new Bill(id, room, value, date1, customer));
        }
        String leftAlignFormat = "| %-15s | %-4d |%15s|%6d$|%13s|%n";

        System.out.format("+-----------------+------+----------------------------+-------+-------------|%n");
        System.out.format("|    Room name    |  ID  |             Date           | Price |    Customer  %n");
        System.out.format("+-----------------+------+----------------------------+-------+-------------|%n");
        for(Bill bill:bills.values()){
            System.out.format(leftAlignFormat, bill.getRoom().getRoomName(), bill.getIdBill(),bill.getDate(),bill.getPrice(),bill.getCustomer().getCustomerName());
        }
        System.out.format("+-----------------+------+----------------------------+-------+-------------|%n");
    }
    public void ExportFile() throws IOException {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String filePath = "/home/dang/Bản tải về/FileExcel/Bills_" + dateStr + ".xlsx";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bills");

        // Tạo header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Room Name", "ID", "Date", "Price ($)", "Customer"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);

            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        // Ghi dữ liệu từ bills vào các hàng tiếp theo
        int rowIndex = 1;
        for (Bill bill : bills.values()) {
            Row row = sheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(bill.getRoom().getRoomName());
            row.createCell(1).setCellValue(bill.getIdBill());
            row.createCell(2).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bill.getDate()));
            row.createCell(3).setCellValue(bill.getPrice());
            row.createCell(4).setCellValue(bill.getCustomer().getCustomerName());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Excel file created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    public void add(Room room, Customer customer, HotelSer hotel) {
        int bookingId=0;
        if (bookings.isEmpty()){
            bookingId=1;
        }
        else {
            bookingId = Collections.max(bookings.keySet())+1;
        }
        Date date = new Date();
        Bookings booking = new Bookings(bookingId,date,room,customer,hotel);
        bookings.put(bookingId,booking);
    }
    public static List<HotelSer> getHotelSer( ) {
        return bookings.values().stream()
                .map(Bookings::getHotelSer)
                .collect(Collectors.toList());
    }
    public List<Bookings>getBookings(Customer customer){
        return bookings.values().stream().filter(bookings1 -> bookings1.getCustomer().equals(customer)).toList();
    }

    public void NewBills(Customer customer, Room room, String filePath) {
        int billId = bills.isEmpty() ? 1 : Collections.max(bills.keySet()) + 1;
        List<HotelSer> hotelServices = getHotelSer();
        Date date = new Date();
        System.out.println("Customer Name: " + customer.getCustomerName());
        System.out.println("Room Name: " + room.getRoomName());
        System.out.println("Date: " + date);
        int price = room.getPrice();
        if (hotelServices.isEmpty()){
            System.out.print(" ");
        }
        else {
            for (HotelSer hotelSer : hotelServices) {
                System.out.println("Service Name: " + hotelSer.getSer_name());
                price += hotelSer.getSer_price();
            }
        }


        System.out.println("Service price: " + price + "$");
        Bill bill = new Bill(billId, room, price, date, customer);
        bills.put(billId, bill);
        room.setBooked(false);
        bookings.clear();
        roomCustomerMap.clear();

        try {
            Workbook workbook;
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet billSheet = workbook.getSheet("Bills");
            if (billSheet == null) {
                billSheet = workbook.createSheet("Bills");
                Row billHeader = billSheet.createRow(0);
                billHeader.createCell(0).setCellValue("Bill ID");
                billHeader.createCell(1).setCellValue("Room Name");
                billHeader.createCell(2).setCellValue("Total Price");
                billHeader.createCell(3).setCellValue("Date");
                billHeader.createCell(4).setCellValue("Customer Name");
            }

            int lastRowNum = billSheet.getLastRowNum();
            Row row = billSheet.createRow(lastRowNum + 1);
            row.createCell(0).setCellValue(bill.getIdBill());
            row.createCell(1).setCellValue(bill.getRoom().getRoomName());
            row.createCell(2).setCellValue(bill.getPrice());
            row.createCell(3).setCellValue(bill.getDate().toString());
            row.createCell(4).setCellValue(bill.getCustomer().getCustomerName());

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            workbook.close();

            System.out.println("Bill đã được lưu vào file Excel tại: " + filePath);
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu bill vào file Excel: " + e.getMessage());
        }
    }

    public void Connect(Customer customer, Room room) {
        int bookingId = 0;
        if (roomCustomerMap.isEmpty()) {
            bookingId = 1;
        } else {
            bookingId = Collections.max(roomCustomerMap.keySet()) + 1;
        }
        room.setBooked(true);
        RoomCustomer roomCustomer = new RoomCustomer(customer, bookingId, room);
        roomCustomerMap.put(bookingId, roomCustomer);
    }
    public  boolean isCustomerExists( Customer inputCustomer) {
        for (RoomCustomer roomCustomer : roomCustomerMap.values()) {
            if (roomCustomer.getCustomer().equals(inputCustomer)) {
                return true;
            }
        }
        return false;
    }
    public int getRoomId(Customer customer) {
        RoomCustomer customer1=  roomCustomerMap
                .values()
                .stream()
                .filter(roomCustomer -> roomCustomer
                        .getCustomer()
                        .equals(customer)).toList().getFirst();
        return customer1.getRoom().getIdRoom();
    }
}
