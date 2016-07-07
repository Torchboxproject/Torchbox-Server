package net.torchbox.net.raknet;

import net.torchbox.net.Packet;

/**
 * Created by addisonparkhurst on 7/3/16.
 */
public class Cancel_Connection extends Packet {

    public byte id = 0x15;

    @Override
    public void _encode() {

    }

    @Override
    public void _decode() {

    }

    public byte getId() {
        return id;
    }

}
