package by.training.railwaytunnel.tunnel;

import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SouthTunnel implements Tunnel {

    private static final SouthTunnel instance = new SouthTunnel(true, true);
    private static final Logger log = LoggerFactory.getLogger(SouthTunnel.class);

    private final BlockingQueue<RailwayDepot> SOUTH_TUNNEL = new LinkedBlockingQueue<>(2);

    private final int CAPACITY = 2;
    private final long TIME_TO_RIDE_THROUGH = 3000;

    private Timer switchSemaphore = new Timer(true);

    private boolean westSemaphore;
    private boolean eastSemaphore;
    private int westCounter = 0;
    private int eastCounter = 0;

    private SouthTunnel(boolean westSemaphore, boolean eastSemaphore) {
        this.westSemaphore = westSemaphore;
        this.eastSemaphore = eastSemaphore;
    }

    public static SouthTunnel getInstance() {
        return instance;
    }


    @Override
    public void rideThroughTunnelEastWest(RailwayDepot train) {
        log.debug(train + " invokes rideThroughTunnelEastWest");

        rideThroughTunnel();
        eastCounter++;

        switchSemaphore.schedule(new TimerTask() {
            @Override
            public void run() {
                westSemaphore = SOUTH_TUNNEL.isEmpty();
            }
        }, 200);

        eastSemaphore = SOUTH_TUNNEL.size() < CAPACITY;
        log.trace("SemaphoreLights are switched");
    }

    @Override
    public void rideThroughTunnelWestEast(RailwayDepot train) {
        log.debug(train + " invokes rideThroughTunnelWestEast");

        rideThroughTunnel();
        westCounter++;

        switchSemaphore.schedule(new TimerTask() {
            @Override
            public void run() {
                eastSemaphore = SOUTH_TUNNEL.isEmpty();
            }
        }, 200);

        westSemaphore = SOUTH_TUNNEL.size() < CAPACITY;
        log.trace("SemaphoreLights are switched");

    }

    @Override
    public void rideIntoTunnelFromWest(RailwayDepot train) {
        log.debug(train + " invokes rideIntoTunnelFromWest");

        rideIntoTunnel(train);
        westSemaphore = SOUTH_TUNNEL.size() < CAPACITY;
    }

    @Override
    public void rideIntoTunnelFromEast(RailwayDepot train) {
        log.debug(train + " invokes rideIntoTunnelFromEast");

        rideIntoTunnel(train);
        eastSemaphore = SOUTH_TUNNEL.size() < CAPACITY;
    }


    private void rideIntoTunnel(RailwayDepot train) {
        try {
            SOUTH_TUNNEL.put(train);
            log.debug(train + " entries into SouthTunnel");
        } catch (InterruptedException e) {
            log.error("rideIntoTunnel error", e);
            Thread.currentThread().interrupt();
        }
    }

    private void rideThroughTunnel() {
        try {
            SOUTH_TUNNEL.poll(TIME_TO_RIDE_THROUGH, TimeUnit.MILLISECONDS);
            log.debug("Train raid away from SouthTunnel");
        } catch (InterruptedException e) {
            log.error("rideThroughTunnel error", e);
            Thread.currentThread().interrupt();
        }
    }

    public boolean isWestSemaphore() {
        return westSemaphore;
    }

    public boolean isEastSemaphore() {
        return eastSemaphore;
    }

    @Override
    public void setWestSemaphore(boolean westSemaphore) {
        this.westSemaphore = westSemaphore;
    }

    @Override
    public void setEastSemaphore(boolean eastSemaphore) {
        this.eastSemaphore = eastSemaphore;
    }

    @Override
    public BlockingQueue<RailwayDepot> getTunnel() {
        return SOUTH_TUNNEL;
    }

    @Override
    public int getWestCounter() {
        return westCounter;
    }

    @Override
    public int getEastCounter() {
        return eastCounter;
    }
}
