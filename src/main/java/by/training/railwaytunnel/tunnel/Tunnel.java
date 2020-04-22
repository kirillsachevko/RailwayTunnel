package by.training.railwaytunnel.tunnel;

import by.training.railwaytunnel.railwaydepot.RailwayDepot;

import java.util.concurrent.BlockingQueue;

public interface Tunnel {

    void rideThroughTunnelEastWest(RailwayDepot train) throws InterruptedException;

    void rideThroughTunnelWestEast(RailwayDepot train) throws InterruptedException;

    void rideIntoTunnelFromWest(RailwayDepot train);

    void rideIntoTunnelFromEast(RailwayDepot train);

    boolean isWestSemaphore();

    boolean isEastSemaphore();

    void setWestSemaphore(boolean westSemaphore);

    void setEastSemaphore(boolean eastSemaphore);

    BlockingQueue<RailwayDepot> getTunnel();

    int getWestCounter();

    int getEastCounter();

}
