package net.torchbox.net.raknet;

import net.torchbox.net.Packet;
import net.torchbox.net.Protocol;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Created by addisonparkhurst on 7/4/16.
 */
public class UNCONNECTED_PONG extends Packet {

    public byte id = 0x1C;

    public long pingID;
    public long serverID;
    public int players;
    public String maxPlayers;
    public String motd;
    public String serverName;
    public ByteBuffer buffer;
    public ByteBuffer message;
    public DatagramPacket dp;

    public UNCONNECTED_PONG(DatagramPacket dp, String motd, String maxPlayers, int players) {
        this.dp = dp;
        this.motd = motd;
        this.maxPlayers = maxPlayers;
        this.players = players;
        this.serverName = "MCPE;" + this.motd + ";8 1;0.15.0.0;" + this.players + ";" + this.maxPlayers;
        this.message = ByteBuffer.allocate(35+this.serverName.length());
    }

    public void _encode() {
        this.message.put(getId());
        this.message.putLong(pingID);
        this.message.putLong(serverID);
        this.message.put(Protocol.MAGIC);
        this.putString(serverName, this.message);
    }

    public void _decode() {
        buffer = ByteBuffer.wrap(dp.getData());
        pingID = buffer.getLong();
        buffer.get(Protocol.MAGIC.length);
        serverName = this.getString(buffer);
    }

    public byte getId() {
        return id;
    }

}
