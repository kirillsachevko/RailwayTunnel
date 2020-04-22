package by.training.railwaytunnel.railwaydepot;

import by.training.railwaytunnel.junction.EastJunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class EastRailwayDepot implements RailwayDepot {

    private static final Logger log = LoggerFactory.getLogger(EastRailwayDepot.class);

    private int trainNumber;
    private CountDownLatch latch;

    public EastRailwayDepot(CountDownLatch latch, int trainNumber) {
        log.trace("Train " + trainNumber + " is created");

        this.latch = latch;
        this.trainNumber = trainNumber;
    }

    @Override
    public void run() {
        latch.countDown();
        try {
            EastJunction eastJunction = new EastJunction();
            eastJunction.raidThroughJunction(this);
        } catch (InterruptedException e) {
            log.error("EastRailwayDepot run error", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int getTrainNumber() {
        return trainNumber;
    }


}
