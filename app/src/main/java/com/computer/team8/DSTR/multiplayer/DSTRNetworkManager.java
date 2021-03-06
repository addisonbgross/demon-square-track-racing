package com.computer.team8.DSTR.multiplayer;

public class DSTRNetworkManager {
    private int dataCounter, delayCounter, writeDelay;
    private final int SLOW_DELAY = 500000;
    private final int FAST_DELAY = 10;

    public DSTRNetworkManager() {
        dataCounter = 0;
        delayCounter = 0;
        writeDelay = FAST_DELAY;
    }

    public boolean sendMessage(String mesg, String speed) {
        // set message sending speed
        switch (speed) {
            case "fast":
                writeDelay = FAST_DELAY;
                break;
            case "slow":
                writeDelay = SLOW_DELAY;
                break;
            default:
                writeDelay = FAST_DELAY;
                break;
        }

        if (!DSTRBluetooth.isConnected()) {
            delayCounter = 0;
            dataCounter = 0;
            return true;
        }

        ++delayCounter;

        if (delayCounter > writeDelay && DSTRBluetooth.isConnected()) {
            if (dataCounter < mesg.length()) {
                DSTRBluetooth.write("" + mesg.charAt(dataCounter));
            }

            delayCounter = 0;
            dataCounter++;

            if (dataCounter > mesg.length()) {
                delayCounter = 0;
                dataCounter = 0;
                return true;
            }
        }

        return false;
    }

    public void sendComplete() {
        if (!DSTRBluetooth.isConnected()) {
            return;
        }

        DSTRBluetooth.write("$$");
    }

    public String getMessage() {
        String message = DSTRBluetooth.read();
        if (message != null) {
            return message;
        } else {
            return "";
        }
    }
}
