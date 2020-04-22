import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import by.training.railwaytunnel.tunnel.NorthTunnel;
import by.training.railwaytunnel.tunnel.SouthTunnel;
import by.training.railwaytunnel.tunnel.Tunnel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class TunnelEntryTest {

    @After
    public void afterMethod() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Test
    public void shouldReturnThreadConditionAfterRaidIntoNorthTunnelFromWest() throws InterruptedException {
        Tunnel tunnel = NorthTunnel.getInstance();

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                tunnel.rideIntoTunnelFromWest(this);
            }

            @Override
            public int getTrainNumber() {
                return 101;
            }
        });

        train.start();

        long trainNumber = tunnel.getTunnel().take().getTrainNumber();

        Assert.assertEquals(101,trainNumber);
    }

    @Test
    public void shouldReturnThreadConditionAfterRaidIntoNorthTunnelFromEast() throws InterruptedException {
        Tunnel tunnel = NorthTunnel.getInstance();

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                tunnel.rideIntoTunnelFromEast(this);
            }

            @Override
            public int getTrainNumber() {
                return 111;
            }
        });

        train.start();

        long trainNumber = tunnel.getTunnel().take().getTrainNumber();

        Assert.assertEquals(111,trainNumber);
    }

    @Test
    public void shouldReturnThreadConditionAfterRaidIntoSouthTunnelFromWest() throws InterruptedException {
        Tunnel tunnel = SouthTunnel.getInstance();

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                tunnel.rideIntoTunnelFromWest(this);
            }

            @Override
            public int getTrainNumber() {
                return 201;
            }
        });

        train.start();

        long trainNumber = tunnel.getTunnel().take().getTrainNumber();

        Assert.assertEquals(201,trainNumber);
    }

    @Test
    public void shouldReturnThreadConditionAfterRaidIntoSouthTunnelFromEast() throws InterruptedException {
        Tunnel tunnel = SouthTunnel.getInstance();

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                tunnel.rideIntoTunnelFromEast(this);
            }

            @Override
            public int getTrainNumber() {
                return 211;
            }
        });

        train.start();

        long trainNumber = tunnel.getTunnel().take().getTrainNumber();

        Assert.assertEquals(211,trainNumber);
    }
}
