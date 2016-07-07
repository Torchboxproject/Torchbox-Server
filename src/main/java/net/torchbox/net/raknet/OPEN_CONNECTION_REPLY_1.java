package net.torchbox.net.raknet;

import net.torchbox.net.Packet;
import net.torchbox.net.Protocol;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/2/16.
 */
public class OPEN_CONNECTION_REPLY_1 extends Packet {

    public byte id = 0x06;
    public long serverID;
    public short mtu;
    public ByteBuffer buffer;
    public ByteBuffer message;
    public DatagramPacket dp;

    public OPEN_CONNECTION_REPLY_1(DatagramPacket dp) {
        this.dp = dp;
        this.message = ByteBuffer.allocate(28);
    }

    @Override
    public void _encode() {
        this.message.put(getId());
        this.message.put(Protocol.MAGIC);
        this.message.putLong(serverID);
        this.message.put((byte) 0);
        this.message.putShort(this.mtu);
    }

    @Override
    public void _decode() {
        this.buffer = ByteBuffer.wrap(dp.getData());
        serverID = this.buffer.getLong();
        this.buffer.get();
        mtu = this.buffer.getShort();
    }

    public byte getId() {
        return id;
    }

}
