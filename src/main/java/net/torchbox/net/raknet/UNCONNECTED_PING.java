package net.torchbox.net.raknet;

import net.torchbox.net.Packet;
import net.torchbox.net.Protocol;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/4/16.
 */
public class UNCONNECTED_PING extends Packet {

    public byte id = 0x01;

    public long pingID;

    DatagramPacket dp;
    ByteBuffer buffer;

    public UNCONNECTED_PING(DatagramPacket dp) {
        this.dp = dp;
    }

    public void _encode() {
        ByteBuffer message = ByteBuffer.allocate(100);
        message.putLong(pingID);
        message.put(Protocol.MAGIC);
    }

    public void _decode() {
        buffer = ByteBuffer.wrap(this.dp.getData());
        pingID = this.buffer.getLong();
        buffer.get(Protocol.MAGIC.length);
    }

    public byte getId() {
        return id;

    }

}
