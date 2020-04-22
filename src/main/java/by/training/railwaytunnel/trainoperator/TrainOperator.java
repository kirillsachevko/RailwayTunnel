package by.training.railwaytunnel.trainoperator;


import by.training.railwaytunnel.railwaydepot.EastRailwayDepot;
import by.training.railwaytunnel.railwaydepot.WestRailwayDepot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrainOperator {

    private static final Logger log = LoggerFactory.getLogger(TrainOperator.class);

    private ExecutorService dispatcher;

    public void startTrains(int trainsForRaidFromWest, int trainsForRaidFromEast) {

        int allTrainsNumber = trainsForRaidFromEast + trainsForRaidFromWest;

        dispatcher = Executors.newFixedThreadPool(allTrainsNumber);
        CountDownLatch latch = new CountDownLatch(allTrainsNumber);

        int trainNumber;

        for (int i = 0; i < trainsForRaidFromWest; i++) {
            trainNumber = i + 1;
            dispatcher.execute(new WestRailwayDepot(latch, trainNumber));
            log.trace("Train " + trainNumber + " is ready to start");
        }

        for (int i = 0; i < trainsForRaidFromEast; i++) {
            trainNumber = i + 1;
            dispatcher.execute(new EastRailwayDepot(latch, trainNumber + 100));
            log.trace("Train " + trainNumber + " is ready to start");
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Latch await error", e);
            Thread.currentThread().interrupt();
        }

        dispatcher.shutdown();
    }

    public boolean isTrainRaidFinished() {
        return dispatcher.isTerminated();
    }
}
