package net.torchbox.net.raknet;

import net.torchbox.net.Packet;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/3/16.
 */
public class Incoming_Connection extends Packet {

    public byte id = 0x13;

    public InetAddress clientAddr;
    public InetAddress[] systemAddrs;
    public short port;
    public long inTimestamp;
    public long outTimestamp;
    public ByteBuffer buffer;
    public ByteBuffer message;
    public DatagramPacket dp;

    public Incoming_Connection(DatagramPacket dp) {
        this.dp = dp;
        this.message = ByteBuffer.allocate(100);
    }

    @Override
    public void _encode() {
        this.putAddress(clientAddr, port, this.message);
        try {
            systemAddrs = InetAddress.getAllByName("127.0.0.1");
            for(InetAddress addr : systemAddrs) {
                this.putAddress(addr, port, this.message);
            }
        }catch(UnknownHostException e) {
            e.printStackTrace();
        }
        this.message.putLong(inTimestamp);
        this.message.putLong(outTimestamp);
    }

    @Override
    public void _decode() {
        this.buffer = ByteBuffer.wrap(dp.getData());
        this.getAddress(clientAddr, port, this.message);
        inTimestamp = this.message.getLong();
        outTimestamp = this.message.getLong();
    }

    public byte getId() {
        return id;
    }

}
