package net.torchbox.net.raknet;

import net.torchbox.net.Packet;
import net.torchbox.net.Protocol;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/2/16.
 */
public class OPEN_CONNECTION_REQUEST_1 extends Packet {

    public byte id = 0x08;
    public byte raknetProtocol;
    public short mtu;
    public ByteBuffer buffer;
    public ByteBuffer message;
    public DatagramPacket dp;

    public OPEN_CONNECTION_REQUEST_1(DatagramPacket dp) {
        this.dp = dp;
        this.message = ByteBuffer.allocate(100);
    }

    @Override
    public void _encode() {
        this.message.put(Protocol.MAGIC);
        this.message.put(raknetProtocol);
        this.message.putShort(mtu);
    }

    @Override
    public void _decode() {
        this.buffer = ByteBuffer.wrap(dp.getData());
        this.buffer.get(Protocol.MAGIC.length);
        raknetProtocol = this.buffer.get();
        mtu = this.buffer.getShort();
    }

    public byte getId() {
        return id;
    }

}
