package net.torchbox.net;

import net.torchbox.net.raknet.*;
import net.torchbox.util.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

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

public class raknetHandler implements Runnable {

    DatagramSocket clientSocket;

    ByteBuffer buffer;

    public DatagramPacket dp;
    public DatagramSocket socket;
    public String motd;
    public String maxPlayers;
    public int players = 0;
    public Logger logger;
    public String ip;
    public int port;

    public Map<String, Session> sessions;

    public raknetHandler(String ip, int port, String motd, String maxPlayers) {
        this.ip = ip;
        this.port = port;
        this.motd = motd;
        this.maxPlayers = maxPlayers;
        this.sessions = new HashMap<String, Session>();
    }

    public void run() {
        try {
            this.handlePacket(this.motd, this.maxPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int convert(int n) {
        return Integer.valueOf(String.valueOf(n), 16);
    }

    public void handlePacket(String motd, String maxPlayers) throws IOException {
        this.logger = new Logger();
        InetSocketAddress address = new InetSocketAddress(this.ip, this.port);
        try {
            this.socket = new DatagramSocket(address);
            this.logger.net("Bound to [" + address.getAddress().toString().replace("/", "") + ":" + this.port + "] successfully!");
        }catch(SocketException e) {
            this.logger.error("Couldn't bind to " + address.getAddress().toString().replace("/", "") + ":" + this.port);
        }
        clientSocket = new DatagramSocket();
        byte[] recvBuf = new byte[1024 * 1024];
        while(true) {
            DatagramPacket dp = new DatagramPacket(recvBuf, recvBuf.length);
            this.socket.receive(dp);
            byte[] data = dp.getData();
            System.out.println("[Receive]: [PID: 0x" + convert(dp.getData()[0]) + "] [Src: " + dp.getAddress().toString().replace("/", "") + ":" + dp.getPort() + "]");
            if (data[0] == Protocol.CONNECTED_PING_OPEN_CONNECTION) {
                UNCONNECTED_PONG pkt = new UNCONNECTED_PONG(dp, motd, maxPlayers, players);
                Packet packet = new UNCONNECTED_PING(dp);
                packet._decode();
                pkt.pingID = ((UNCONNECTED_PING) packet).pingID;
                this.sendPacket(pkt.message.array(), pkt, dp.getAddress(), dp.getPort(), socket);
            } else {
                Session session;
                if(!this.sessions.containsKey(dp.getAddress().toString())){
                    session = new Session(dp, socket, motd, maxPlayers, this.players);
                    this.sessions.put(session.getAddress().toString(), session);
                    this.logger.net(session.getAddress().toString() + " opened a new Session!");
                } else {
                    session = this.sessions.get(dp.getAddress().toString());
                    this.players = sessions.size();
                    session.handlePackets(dp.getData(), dp);
                    this.sessions.remove(dp.getAddress().toString());
                    this.players = sessions.size();
                }

            }
        }
    }

    public void sendPacket(byte[] array, Packet packet, InetAddress clientAddr, int port, DatagramSocket socket) throws IOException {
        packet._encode();
        DatagramPacket pk = new DatagramPacket(array, array.length, clientAddr, port);
        socket.send(pk);
        System.out.println("Sent: [PID: 0x" + convert(pk.getData()[0]) + "] Remote: " + clientAddr.toString().replace("/", "") + ":" + port);
    }


}
