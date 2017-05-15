package com.example.yzubritskiy.loadersresearch.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by yzubritskiy on 5/15/2017.
 */

public class MockDataHelper {
    private static class MockOwner{
        private MockOwner(){}
        private static String firstNames[] = new String[]{"John", "Sam", "Jack", "Hue", "Mike", "Jorge", "Liliana", "Vanda", "Kristin"};
        private static String secondNames[] = new String[]{"Smith", "White", "Black", "Lorie", "Jackson", "Kahovski", "Berimor", "Holms", "Shwarzeneger"};
        private static String dates[] = new String[]{"2000-08-30", "2010-10-15", "1980-01-01", "1990-07-20", "1993-12-15", "1979-09-17", "1986-05-05", "1968-10-27", "1997-10-15"};
    }

    private static class MockCar{
        private MockCar(){}
        private static int numbers[] = new int[]{9999, 8888, 7777, 6666, 5555, 4444, 3333, 2222, 1111};
        private static int years[] = new int[]{2010, 2009, 2008, 2007, 2006, 2005, 2004, 2003, 2002, 2001};
        private static String models[] = new String[]{"Porsche", "Cadillac", "Dodge", "DMV", "Mercedes", "RangeRover", "Mitsubishi", "Toyota", "Audi"};
    }

    private MockDataHelper(){}

    public static Owner createRandomOwner(){
        Random random = new Random();

        String firstName = MockOwner.firstNames[random.nextInt(MockOwner.firstNames.length-1)];
        String secondName = MockOwner.secondNames[random.nextInt(MockOwner.secondNames.length-1)];
        String dateString = MockOwner.dates[random.nextInt(MockOwner.dates.length-1)];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Owner(firstName, secondName, date);
    }

    public static Car createRandomCar(){
        Random random = new Random();
        int year = MockCar.years[random.nextInt(MockCar.years.length-1)];
        int number = MockCar.numbers[random.nextInt(MockCar.numbers.length-1)];
        String model = MockCar.models[random.nextInt(MockCar.models.length-1)];
        return new Car(number, year, model);
    }

}
