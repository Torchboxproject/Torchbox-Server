package net.torchbox.net;

import net.torchbox.net.raknet.OPEN_CONNECTION_REPLY_1;
import net.torchbox.net.raknet.OPEN_CONNECTION_REPLY_2;
import net.torchbox.net.raknet.OPEN_CONNECTION_REQUEST_1;
import net.torchbox.net.raknet.OPEN_CONNECTION_REQUEST_2;
import net.torchbox.util.Logger;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Copyright 2016 Torchbox Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Session {

    private DatagramSocket socket;
    public DatagramPacket dp;

    public String motd;
    public String maxPlayers;
    public int players;
    public Long serverid;
    public boolean isRunning = false;

    public Session(DatagramPacket dp, DatagramSocket socket, String motd, String maxPlayers, int players) {
        this.dp = dp;
        this.socket = socket;
        this.motd = motd;
        this.maxPlayers = maxPlayers;
        this.players = players;
    }

    public InetAddress getAddress() {
        return this.dp.getAddress();
    }

    public void handlePackets(byte[] data, DatagramPacket dp) throws IOException {
        this.serverid = new Random().nextLong();
        if (data[0] == Protocol.OPEN_CONNECTION_REQUEST_1) {
            OPEN_CONNECTION_REPLY_1 pkt = new OPEN_CONNECTION_REPLY_1(dp);
            Packet packet = new OPEN_CONNECTION_REQUEST_1(dp);
            packet._decode();
            pkt.mtu = (short) (((OPEN_CONNECTION_REQUEST_1) packet).mtu + 1447);
            pkt.serverID = this.getServerid();
            this.sendPacket(pkt.message.array(), pkt, dp.getAddress(), dp.getPort(), this.socket);
        } else if (data[0] == Protocol.OPEN_CONNECTION_REQUEST_2) {
            OPEN_CONNECTION_REQUEST_2 packet = new OPEN_CONNECTION_REQUEST_2(dp);
            packet._decode();

            OPEN_CONNECTION_REPLY_2 pkt = new OPEN_CONNECTION_REPLY_2(dp);
            pkt.serverGUID = new Random().nextLong();
            pkt.port = (short) dp.getPort();
            pkt.mtu = (short) 1464;
            this.sendPacket(pkt.message.array(), pkt, this.dp.getAddress(), dp.getPort(), this.socket);
        }

    }

    public void sendPacket(byte[] array, Packet packet, InetAddress clientAddr, int port, DatagramSocket socket) throws IOException {
        packet._encode();
        DatagramPacket pk = new DatagramPacket(array, array.length, clientAddr, port);
        socket.send(pk);
        System.out.println("Sent: [PID: 0x" + convert(pk.getData()[0]) + "] Remote: " + clientAddr.toString().replace("/", "") + ":" + port);
    }


    public Long getServerid() {
        return this.serverid;
    }

    public static int convert(int n) {
        return Integer.valueOf(String.valueOf(n), 16);
    }
}
