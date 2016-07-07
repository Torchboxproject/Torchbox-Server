package net.torchbox.net.raknet;

import net.torchbox.net.Packet;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/3/16.
 */
public class Connection_Request_Accepted extends Packet {

    public byte id = 0x10;

    public InetAddress[] systemAddrs;
    public InetAddress systemAddr;
    public short port;
    public long systemIndex;
    public long inTimestamp;
    public long outTimestamp;
    public ByteBuffer buffer;
    public ByteBuffer message;
    public DatagramPacket dp;

    public Connection_Request_Accepted(DatagramPacket dp) {
        this.dp = dp;
        this.message = ByteBuffer.allocate(100);
    }

    @Override
    public void _encode() {
        try {
            systemAddr = InetAddress.getByName("127.0.0.1");
            this.message.putLong(systemIndex);
            systemAddrs = InetAddress.getAllByName("127.0.0.1");
            this.putAddress(systemAddr, port, message);
            for(InetAddress addr : systemAddrs) {
                this.putAddress(addr, port, message);
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
        systemIndex = this.buffer.getLong();
        inTimestamp = this.buffer.getLong();
        outTimestamp = this.buffer.getLong();
    }

    public byte getId() {
        return id;
    }

}
