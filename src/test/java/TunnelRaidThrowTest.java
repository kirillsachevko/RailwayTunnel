import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import by.training.railwaytunnel.tunnel.NorthTunnel;
import by.training.railwaytunnel.tunnel.SouthTunnel;
import by.training.railwaytunnel.tunnel.Tunnel;
import org.junit.Assert;
import org.junit.Test;

public class TunnelRaidThrowTest {

    @Test
    public void shouldReturnThreadConditionAfterRaidThrowNorthTunnelFromWest() throws InterruptedException {
        Tunnel tunnel = NorthTunnel.getInstance();
        String[] message = new String[2];

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                try {
                    tunnel.rideIntoTunnelFromWest(this);
                    message[0] = String.valueOf(tunnel.getTunnel().take().getTrainNumber());
                    tunnel.rideThroughTunnelWestEast(this);
                    message[1] = " raid throw NorthTunnel successfully";

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public int getTrainNumber() {
                return 101;
            }
        });

        train.start();

        Thread.sleep(4000);

        String result = "Train number " + message[0] + message[1];


        Assert.assertEquals("Train number 101 raid throw NorthTunnel successfully", result);
    }

    @Test
    public void shouldReturnThreadConditionAfterRaidThrowNorthTunnelFromEast() throws InterruptedException {
        Tunnel tunnel = NorthTunnel.getInstance();
        String[] message = new String[2];

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                try {
                    tunnel.rideIntoTunnelFromEast(this);
                    message[0] = String.valueOf(tunnel.getTunnel().take().getTrainNumber());
                    tunnel.rideThroughTunnelEastWest(this);
                    message[1] = " raid throw NorthTunnel successfully";

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public int getTrainNumber() {
                return 111;
            }
        });

        train.start();

        Thread.sleep(4000);

        String result = "Train number " + message[0] + message[1];


        Assert.assertEquals("Train number 111 raid throw NorthTunnel successfully", result);
    }

    @Test
    public void shouldReturnThreadConditionAfterRaidThrowSouthTunnelFromWest() throws InterruptedException {
        Tunnel tunnel = SouthTunnel.getInstance();
        String[] message = new String[2];

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                try {
                    tunnel.rideIntoTunnelFromWest(this);
                    message[0] = String.valueOf(tunnel.getTunnel().take().getTrainNumber());
                    tunnel.rideThroughTunnelWestEast(this);
                    message[1] = " raid throw NorthTunnel successfully";

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public int getTrainNumber() {
                return 201;
            }
        });

        train.start();

        Thread.sleep(4000);

        String result = "Train number " + message[0] + message[1];


        Assert.assertEquals("Train number 201 raid throw NorthTunnel successfully", result);
    }

    @Test
    public void shouldReturnThreadConditionAfterRaidIThrowSouthTunnelFromEast() throws InterruptedException {
        Tunnel tunnel = SouthTunnel.getInstance();
        String[] message = new String[2];

        Thread train = new Thread(new RailwayDepot() {
            @Override
            public void run() {
                try {
                    tunnel.rideIntoTunnelFromEast(this);
                    message[0] = String.valueOf(tunnel.getTunnel().take().getTrainNumber());
                    tunnel.rideThroughTunnelEastWest(this);
                    message[1] = " raid throw NorthTunnel successfully";

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public int getTrainNumber() {
                return 211;
            }
        });

        train.start();

        Thread.sleep(4000);

        String result = "Train number " + message[0] + message[1];


        Assert.assertEquals("Train number 211 raid throw NorthTunnel successfully", result);
    }
}
