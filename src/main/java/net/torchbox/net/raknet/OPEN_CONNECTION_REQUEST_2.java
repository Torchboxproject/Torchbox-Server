package net.torchbox.net.raknet;

import net.torchbox.net.Packet;
import net.torchbox.net.Protocol;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/2/16.
 */
public class OPEN_CONNECTION_REQUEST_2 extends Packet {

    public byte id = 0x07;
    public short port;
    public short mtu;
    public long clientGUID;
    public byte cookie;
    public byte security;
    public ByteBuffer buffer;
    public ByteBuffer message;
    public DatagramPacket dp;

    public OPEN_CONNECTION_REQUEST_2(DatagramPacket dp) {
        this.dp = dp;
        this.message = ByteBuffer.allocate(34);
    }

    @Override
    public void _encode() {
        this.message.put(Protocol.MAGIC);
        this.message.put(security);
        this.message.put(cookie);
        this.message.putShort(port);
        this.message.putShort(mtu);
        this.message.putLong(clientGUID);
    }

    @Override
    public void _decode() {
        this.buffer = ByteBuffer.wrap(dp.getData());
        this.buffer.get(Protocol.MAGIC.length);
        security = this.buffer.get();
        cookie = this.buffer.get();
        port = this.buffer.getShort();
        mtu = this.buffer.getShort();
        clientGUID = this.buffer.getLong();
    }

    public byte getId() {
        return id;
    }

}
