package net.torchbox.net.raknet;

import net.torchbox.net.Packet;
import net.torchbox.net.Protocol;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/3/16.
 */
public class OPEN_CONNECTION_REPLY_2 extends Packet {

    public byte id = 0x08;
    public long serverGUID;
    public short port;
    public short mtu;
    public ByteBuffer buffer;
    public ByteBuffer message;
    public DatagramPacket dp;

    public OPEN_CONNECTION_REPLY_2(DatagramPacket dp) {
        this.dp = dp;
        this.message = ByteBuffer.allocate(100);
    }

    @Override
    public void _encode() {
        this.message.put(getId());
        this.message.put(Protocol.MAGIC);
        this.message.putLong(serverGUID);
        this.message.putShort(port);
        this.message.putShort(mtu);
        this.message.put((byte) 0);
    }

    @Override
    public void _decode() {
        this.buffer = ByteBuffer.wrap(dp.getData());
        this.buffer.get(Protocol.MAGIC.length);
        serverGUID = this.buffer.getLong();
        port = this.buffer.getShort();
        mtu = this.buffer.getShort();
    }

    public byte getId() {
        return id;
    }
}
