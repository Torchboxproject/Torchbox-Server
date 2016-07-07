package net.torchbox;

import net.torchbox.net.Session;
import net.torchbox.util.Logger;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Properties;

import net.torchbox.net.raknetHandler;
import net.torchbox.util.TextColor;

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

public class Torchbox {

    public Logger logger;

    public DatagramSocket socket;
    public DatagramSocket clientSock;

    public String ip;
    public String port;
    public String motd;
    public String maxPlayers;


    public raknetHandler handler;

    public static void main(String[] args) {
        new Torchbox().start(args);
    }

    public void setup() {
        this.logger = new Logger();
    }

    public void start(String[] args) {
        setup();
        loadProperties();

        startNetworking();
        this.logger.info("Done...");
    }

    public void loadProperties() {

        this.logger.info(TextColor.ANSI_YELLOW + "Loading properties..." + TextColor.ANSI_RESET);

        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream("server.properties");

            prop.setProperty("proxy-ip", "0.0.0.0");
            prop.setProperty("proxy-port", "19132");
            prop.setProperty("motd", "Torchbox MCPE Server");
            prop.setProperty("max-players", "15");
            try {
                prop.store(output, null);
                FileInputStream input = new FileInputStream("server.properties");
                prop.load(input);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        this.ip = prop.getProperty("proxy-ip");
        this.port = prop.getProperty("proxy-port");
        this.motd = prop.getProperty("motd");
        this.maxPlayers = prop.getProperty("max-players");

        this.logger.info("Starting MC:PE Server version " + TextColor.ANSI_BLUE + Versions.MCPE_VERSION + TextColor.ANSI_RESET);
        this.logger.info("This server is running Torchbox version " + TextColor.ANSI_CYAN + Versions.TORCHBOX_VERSION + TextColor.ANSI_RESET + " " + Versions.CODENAME);
        this.logger.info("Torchbox is licensed under the MIT license");
    }

    public void startNetworking() {
        System.out.println("Starting networking...");
        new Thread(new raknetHandler(this.ip, Integer.parseInt(this.port), this.motd,this.maxPlayers)).start();
    }

    public Logger getLogger() {
        return this.logger;
    }
}
