package net.torchbox.net.raknet;

import net.torchbox.net.Packet;

/**
 * Created by addisonparkhurst on 7/3/16.
 */
public class ConnectionRequest extends Packet {

    public byte id = 0x09;

    public long clientGUID;
    public long timestamp;

    @Override
    public void _encode() {
        this.getBuffer().putLong(clientGUID);
        this.getBuffer().putLong(timestamp);
    }

    @Override
    public void _decode() {
        clientGUID = this.getBuffer().getLong();
        timestamp = this.getBuffer().getLong();
    }

    public byte getId() {
        return id;
    }

}
