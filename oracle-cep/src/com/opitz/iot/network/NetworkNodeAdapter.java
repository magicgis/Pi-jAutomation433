package com.opitz.iot.network;


import com.bea.wlevs.ede.api.RunnableBean;
import com.bea.wlevs.ede.api.StreamSender;
import com.bea.wlevs.ede.api.StreamSource;

import java.util.HashMap;

/**
 * User: Pascal
 * Date: 09.04.14
 * Time: 13:22
 */
public class NetworkNodeAdapter implements RunnableBean, StreamSource {

        private static final int SLEEP_MILLIS = 10000;
        private boolean suspended;
        private StreamSender eventSender;
        private DiscoveryService discoveryService = new DiscoveryService();

        public NetworkNodeAdapter() {
            super();
        }

    public void run() {
        suspended = false;
        while (!isSuspended()) { // Generate messages forever...

            discoveryService.pingAllInSubnet(); // maybe later with parameter for different pingTimeout
            HashMap<String, NetworkNode> nodes = discoveryService.getAllDevicesFromArpCache();
            for(NetworkNode node : nodes.values()){
                generateDeviceMessage(node);
            }

            try {
                synchronized (this) {
                    wait(SLEEP_MILLIS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateDeviceMessage(NetworkNode node) {
        System.out.println("NetworkNodeAdapter Sending Event : " + node);
        eventSender.sendInsertEvent(node);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bea.wlevs.ede.api.StreamSource#setEventSender(com.bea.wlevs.ede.api
     * .StreamSender)
     */
    public void setEventSender(StreamSender sender) {
        eventSender = sender;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.bea.wlevs.ede.api.SuspendableBean#suspend()
     */
    public synchronized void suspend() {
        suspended = true;
    }

    private synchronized boolean isSuspended() {
        return suspended;
    }
}