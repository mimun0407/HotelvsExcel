package org.hotel.main;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hotel.Model.Customer;
import org.hotel.Model.Room;
import org.hotel.Service.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {
    static CustomerService customerService = new CustomerService();
    static HotelService hotelService = new HotelService();
    static BookingService bookingService = new BookingService();
    static RoomService roomService = new RoomService();
    static int idUser = 0;
    static int idRoom = 0;
    static int idHotel = 0;
    public static final String fileName="/home/dang/Bản tải về/ase.xlsx";


    public static void main(String[] args) throws IOException, InvalidFormatException, ParseException {
        customerService.loadCustomerFromExcel(fileName);
        hotelService.loadHotelSerFromExcel(fileName);
        roomService.loadCRoomFromExcel(fileName);
        boolean mainLoop = true;

        Scanner input = new Scanner(System.in);
        while (mainLoop) {
            System.out.println("---------------------Welcome to my program, Please enter Customer ID: -------------------------------");
            customerService.getAllCustomer();
            System.out.println("6, Exit");

            int idCus = Validation.getValidInteger(input, "Please enter a valid Number (1-6):");
            boolean mainLoop2 = false;

            if (idCus == 6) {
                mainLoop = false;
                break;
            }

            switch (idCus) {
                case 1: idUser = 1; break;
                case 2: idUser = 2; break;
                case 3: idUser = 3; break;
                case 4: idUser = 4; break;
                case 5: idUser = 5; break;
                case 100: mainLoop2 = true; break;
                default:
                    System.out.println("Invalid Customer ID. Please try again.");
                    continue;
            }

            while (mainLoop2) {
                System.out.println("Admin menu: \n1. View all bills \n2. View all room not booked \n3. Enter new Customer ID");
                int admin = Validation.getValidInteger(input, "Please enter a valid Number (1-3):");

                if (admin == 3) {
                    mainLoop2 = false;
                    idCus = 0;
                    break;
                }

                switch (admin) {
                    case 1:
                        fileController();
                        break;
                    case 2:roomService.RoomNotBooked();
                    break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        continue;
                }
            }

            if (idCus == 0) continue;

            Customer cus = customerService.getCustomerById(idCus);
            if (bookingService.isCustomerExists(cus)) {
                idRoom = bookingService.getRoomId(cus);
                System.out.println("Customer already exists. Proceeding with existing booking...");
            } else {
                boolean roomLoop = true;
                while (roomLoop) {
                    System.out.println("-------------------------Please enter room number: --------------------------");
                    roomService.getAllRoom();
                    System.out.println("6, Back to Chosen User");

                    int room = Validation.getValidInteger(input, "Please enter a valid room number (1-6):");

                    if (room == 6) {
                        break;
                    }

                    switch (room) {
                        case 1: idRoom = 1; break;
                        case 2: idRoom = 2; break;
                        case 3: idRoom = 3; break;
                        case 4: idRoom = 4; break;
                        case 5: idRoom = 5; break;
                        default:
                            System.out.println("Invalid room number. Please try again.");
                            continue;
                    }

                    Room r = roomService.getRoomById(idRoom);
                    if (r.isBooked()) {
                        System.out.println("This room is already booked. Please try again.");
                    } else {
                        bookingService.Connect(cus, r);
                        roomLoop = false;
                    }
                }
            }

            Room r = roomService.getRoomById(idRoom);

            boolean hotelServiceLoop = true;
            while (hotelServiceLoop) {
                System.out.println("---------------------Do you want to use a service?------------------");
                hotelService.allHotelSer();
                System.out.println("6, Check Bill");
                System.out.println("7, Back to Customer Selection");
                System.out.println("8, History Service");

                int hotelServiceID = Validation.getValidInteger(input, "Please enter a valid service number (1-8):");

                if (hotelServiceID == 6) {
                    bookingService.NewBills(cus, r, fileName);
                    hotelServiceLoop = false;

                    break;
                } else if (hotelServiceID == 7) {
                    hotelServiceLoop = false;
                    break;
                }
                switch (hotelServiceID) {
                    case 1: idHotel = 1; break;
                    case 2: idHotel = 2; break;
                    case 3: idHotel = 3; break;
                    case 4: idHotel = 4; break;
                    case 5: idHotel = 5; break;
                    case 8: bookingService.getHotelSerByCus(cus); break;
                    default:
                        System.out.println("Invalid service number. Please try again.");
                        continue;
                }
                bookingService.add(r, cus, hotelService.getHotelSer(idHotel));
            }
        }

    }
    public static void fileController() throws IOException, ParseException {
        Scanner input = new Scanner(System.in);
        bookingService.getAllBill(fileName);
        System.out.println("\n1. export file \n2. exit");
        int option = Validation.getValidInteger(input, "Please enter a valid number (1-2):");
        if (option == 1) {
            bookingService.ExportFile();
        }
        if (option == 2) {
            System.out.println(" ");
        }
    }
}