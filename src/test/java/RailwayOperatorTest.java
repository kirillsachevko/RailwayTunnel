
import by.training.railwaytunnel.railwaydepot.EastRailwayDepot;
import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import by.training.railwaytunnel.railwaydepot.WestRailwayDepot;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RailwayOperatorTest {

    @Test
    public void shouldReturnNumberOfTrainsPassedTunnels() throws InterruptedException {

        ExecutorService operator = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(20);
        List<Integer> trainList = new ArrayList<>();
        int trainNumber = 1;

        for (int i = 0; i < 10; i++) {
            RailwayDepot trainFromWest = new WestRailwayDepot(latch, trainNumber);
            RailwayDepot trainFromEast = new EastRailwayDepot(latch, trainNumber+100);
            operator.execute(trainFromWest);
            operator.execute(trainFromEast);
            trainList.add(trainFromWest.getTrainNumber());
            trainList.add(trainFromEast.getTrainNumber());

            trainNumber++;

        }
        latch.await();
        operator.shutdown();
        Integer[] result = new Integer[trainList.size()];
        trainList.toArray(result);

        Thread.sleep(3000);

        Integer[] expected = new Integer[]{1,101,2, 102, 3, 103, 4, 104, 5, 105, 6, 106, 7, 107, 8, 108, 9, 109, 10, 110};


        Assert.assertArrayEquals(expected, result);

    }
}
