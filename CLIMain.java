package com.meraj.spring.app;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.meraj.spring.domain.IRoomDef;
import com.meraj.spring.domain.Reservation;
import com.meraj.spring.domain.ReservedRoomFeatured;
import com.meraj.spring.domain.RoomFactory;
import com.meraj.spring.services.ReservationService;
import com.meraj.spring.services.RoomsService;
import com.meraj.util.HotelData;
import com.meraj.util.RoomFeatures;
import com.meraj.util.RoomType;
import com.meraj.util.StringConstants;

@SpringBootApplication
@ComponentScan("com.meraj.spring.services")
public class CLIMain {

    // mandatory data population block
    static {
        HotelData.populate();
    }

    public static void main(String[] strs) {

        boolean stop = false;
        ApplicationContext context = SpringApplication.run(CLIMain.class, strs);

        try (Scanner in = new Scanner(System.in)) {
            while (!stop) {
                String s = cliString(StringConstants.START, in);
                if (s.equals("x")) {

                    stop = true;

                } else if (s.equals("m")) {

                    System.out.println(StringConstants.MENU);

                } else if (s.equals("a")) {

                    HotelData.rooms.values()
                        .forEach((x) -> System.out.println(x.getRoomType() + " room's price is:" + x.getPrice() + "$"));

                } else if (s.equals("n")) {

                    String name = cliString("Enter guest's name:", in);
                    String ccNo = cliString("Enter guest's credit card no:", in);
                    Arrays.stream(RoomType.values()).forEach((a) -> System.out.print(a + "-"));
                    String type = cliString("Enter room type:", in);
                    int days = cliInt("Enter no of days to stay:", in);
                    RoomType roomType = RoomType.valueOf(type.toUpperCase());

                    ReservationService rrs = context.getBean(ReservationService.class);
                    Optional<Reservation> reservation = rrs.addReservation(roomType, name, ccNo, LocalDate.now(), days);
                    if (reservation.isPresent()) {
                        List<RoomFeatures> featuresIncluded = reservation.get().getRoom().getFeatures();
                        for (RoomFeatures feature : HotelData.featuresCosts.keySet()) {
                            if (!featuresIncluded.contains(feature)) {
                                String addOnFeature =
                                    cliString("Do you want to add " + feature + " to your reservation?", in);
                                if (addOnFeature.equalsIgnoreCase("Y")) {
                                    ReservedRoomFeatured room = RoomFactory
                                        .getReservedRoomWithAddonFeature(reservation.get().getRoom(), feature);
                                    reservation.get().setRoom(room);
                                    reservation.get().setTotalCost(room.getDayCost() * days);
                                }
                            }
                        }

                        System.out
                            .println("The reservation is completed:" + System.lineSeparator() + reservation.get());
                        System.out.println(
                            "The total cost is: $" + reservation.get().getTotalCost() + System.lineSeparator());

                    }

                } else if (s.equals("r")) {

                    Arrays.stream(RoomType.values()).forEach((a) -> System.out.print(a + "-"));
                    String type = cliString("Enter room type:", in);
                    String date = cliString("Enter date (YYYY-MM-DD):", in);

                    RoomsService rs = context.getBean(RoomsService.class);
                    Optional<IRoomDef> room =
                        rs.getAvailableRoom(RoomType.valueOf(type.toUpperCase()), LocalDate.parse(date));

                    if (room.isPresent()) {
                        System.out.println("The room is available:" + room.get());
                    } else {
                        System.out.println("No room of type " + type + " is available..");
                    }

                } else if (s.equals("d")) {

                    RoomsService rs = context.getBean(RoomsService.class);

                    String discount = cliString("Press 'p' for percentage discount and 'u' for amount discount", in);
                    if (discount.equals("p")) {
                        Consumer<Integer> consumer = (x) -> rs.changePricePercentage(x);
                        apply("Enter discount percentage:", in, consumer);

                    } else if (discount.equals("u")) {
                        Consumer<Integer> consumer = (x) -> rs.changePrice(x);
                        apply("Enter discount amount:", in, consumer);
                    }
                } else if (s.equals("o")) {

                    String roomNo = cliString("Enter room number:", in);
                    String date = cliString("Enter checkin date (YYYY-MM-DD):", in);

                    ReservationService rs = context.getBean(ReservationService.class);
                    rs.checkout(roomNo, LocalDate.parse(date));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void apply(String message, Scanner in, Consumer<Integer> consumer) {
        int value = cliInt(message, in);
        consumer.accept(value);

        HotelData.rooms.values()
            .forEach((x) -> System.out.println(x.getRoomType() + " room's price is:" + x.getPrice() + "$"));
    }

    private static String cliString(String message, Scanner in) {
        boolean ok = false;
        StringBuilder name = new StringBuilder();
        while (!ok) {
            System.out.println(message);
            name.append(in.nextLine());
            if (name.length() > 0) {
                ok = true;
            }
        }
        return name.toString();
    }

    private static int cliInt(String message, Scanner in) {
        boolean ok = false;
        int value = Integer.MIN_VALUE;
        while (!ok) {
            System.out.println(message);
            value = in.nextInt();
            if (value != Integer.MIN_VALUE) {
                ok = true;
            }
        }
        return value;
    }
}
