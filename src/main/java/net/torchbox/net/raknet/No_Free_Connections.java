package net.torchbox.net.raknet;

import net.torchbox.net.Packet;

/**
 * Created by addisonparkhurst on 7/3/16.
 */
public class No_Free_Connections extends Packet {

    public byte id = 0x14;

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
