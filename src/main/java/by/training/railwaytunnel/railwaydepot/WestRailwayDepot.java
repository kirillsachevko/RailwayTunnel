package by.training.railwaytunnel.railwaydepot;

import by.training.railwaytunnel.junction.WestJunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class WestRailwayDepot implements RailwayDepot {

    private static final Logger log = LoggerFactory.getLogger(WestRailwayDepot.class);

    private int trainNumber;
    private CountDownLatch latch;

    public WestRailwayDepot(CountDownLatch latch, int trainNumber) {
        log.trace("Train " + trainNumber + " is created");

        this.latch = latch;
        this.trainNumber = trainNumber;
    }

    @Override
    public void run() {
        latch.countDown();
        try {
            WestJunction westJunction = new WestJunction();
            westJunction.raidThroughJunction(this);
        } catch (InterruptedException e) {
            log.error("WestRailwayDepot run error", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int getTrainNumber() {
        return trainNumber;
    }

}
