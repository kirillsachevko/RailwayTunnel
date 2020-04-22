import by.training.railwaytunnel.junction.EastJunction;
import by.training.railwaytunnel.junction.Junction;
import by.training.railwaytunnel.junction.WestJunction;
import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import org.junit.Assert;
import org.junit.Test;


public class JunctionTest {

    @Test
    public void shouldReturnThreadConditionAfterWestTaskImpl() throws InterruptedException {
        Junction junction = new WestJunction();
        final String[] message = new String[1];

        Thread train = new Thread(new RailwayDepot() {
            public void run() {
                try {
                    junction.raidThroughJunction(this);
                    Thread.sleep(500);
                    message[0] = "Train was raid throw WestJunction successfully";

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public int getTrainNumber() {
                return 0;
            }
        });

        train.start();

        boolean result = train.isAlive();
        Assert.assertTrue(result);

        Thread.sleep(700);
        String taskComplete = message[0];

        Assert.assertEquals("Train was raid throw WestJunction successfully", taskComplete);

    }

    @Test
    public void shouldReturnThreadConditionAfterEastTaskImpl() throws InterruptedException {
        Junction junction = new EastJunction();
        final String[] message = new String[1];

        Thread train = new Thread(new RailwayDepot() {
            public void run() {
                try {
                    junction.raidThroughJunction(this);
                    Thread.sleep(500);
                    message[0] = "Train was raid throw EastJunction successfully";

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public int getTrainNumber() {
                return 0;
            }
        });

        train.start();

        boolean result = train.isAlive();
        Assert.assertTrue(result);

        Thread.sleep(700);
        String taskComplete = message[0];

        Assert.assertEquals("Train was raid throw EastJunction successfully", taskComplete);

    }
}
