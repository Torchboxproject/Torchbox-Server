package net.torchbox.net.raknet;

import net.torchbox.net.Packet;
import net.torchbox.net.Protocol;

import java.net.DatagramPacket;

/**
 * Created by addisonparkhurst on 6/30/16.
 */
public class BroadcastPacket extends Packet {

    public String name = "MCCPP;MINECON;Torch-box";

    DatagramPacket pack;

    public BroadcastPacket() {}

    @Override
    public void _decode() {

    }

    @Override
    public void _encode() {
        this.getBuffer().putInt(name.length());
        this.getBuffer().put(name.getBytes());
        this.getBuffer().put(Protocol.MAGIC);
    }
}
