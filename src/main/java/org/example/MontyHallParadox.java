package org.example;

import lombok.Getter;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Random;

public class MontyHallParadox {
    private static final int NUM_DOORS = 3;
    private static final int CAR_DOOR = 1;
    private final Random random;

    @Getter
    private int switchWins = 0;
    @Getter
    private int stayWins = 0;

    public MontyHallParadox() {
        random = new Random();
    }

    public int chooseDoor() {
        return random.nextInt(NUM_DOORS) + 1;
    }

    public int openDoor(int chosenDoor) {
        int openedDoor;
        do {
            openedDoor = random.nextInt(NUM_DOORS) + 1;
        } while (openedDoor == chosenDoor || openedDoor == CAR_DOOR);
        return openedDoor;
    }

    public int switchDoor(int chosenDoor, int openedDoor) {
        int newDoor;
        do {
            newDoor = random.nextInt(NUM_DOORS) + 1;
        } while (newDoor == chosenDoor || newDoor == openedDoor);
        return newDoor;
    }

    public double[] playGame(int numIterations) {
        for (int i = 0; i < numIterations; i++) {
            int chosenDoor = chooseDoor();
            int openedDoor = openDoor(chosenDoor);
            int switchedDoor = switchDoor(chosenDoor, openedDoor);
            if (chosenDoor == CAR_DOOR) {
                stayWins++;
            } else if (switchedDoor == CAR_DOOR) {
                switchWins++;
            }
        }
        return new double[]{switchWins, stayWins};
    }

    public double[] getResult(int totalIterations, int switchWins, int stayWins) {
        double switchWinPercentage = (switchWins * 100.0) / totalIterations;
        double stayWinPercentage = (stayWins * 100.0) / totalIterations;
        System.out.println("______________________________________________________________________________________");
        System.out.println("Выиграл при смене двери (в процентах): " + switchWinPercentage + "%");
        System.out.println("Выиграл при оставлении первоначального выбора (в процентах): " + stayWinPercentage + "%");
        System.out.println("Процент выигрышей при смене двери выше, чем при оставлении первоначального выбора.");
        System.out.println("______________________________________________________________________________________");
        return new double[]{stayWinPercentage, stayWinPercentage, stayWins};
    }

    public static void main(String[] args) {
        System.out.println("______________________________________________________________________________________");

        MontyHallParadox game = new MontyHallParadox();
        game.playGame(1000);

        int stayWins = game.getStayWins();
        int switchWins = game.getSwitchWins();
        System.out.println("Количество побед при смене двери -> " + switchWins);
        System.out.println("Количество побед при оставлении первоначально выбранной двери -> " + stayWins);

        double[] values = game.getResult(1000, switchWins ,stayWins);
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for (double v : values) {
            descriptiveStatistics.addValue(v);
        }

        double mean = descriptiveStatistics.getMean();
        double median = descriptiveStatistics.getPercentile(50);
        double standardDeviation = descriptiveStatistics.getStandardDeviation();
        System.out.println("Использование библиотеки Apache Commons Math3");
        System.out.println("Среднее -> " + mean);
        System.out.println("Медианна -> " + median);
        System.out.println("Среднеквадратичное отклонение -> " + standardDeviation);

        System.out.println("______________________________________________________________________________________\n");
    }

}