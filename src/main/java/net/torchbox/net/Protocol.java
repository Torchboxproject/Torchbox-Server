package net.torchbox.net;

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

public class Protocol {

    public static final byte[] MAGIC = {(byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0xFE, (byte) 0xFE,
            (byte) 0xFE, (byte) 0xFE, (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, (byte) 0xFD, (byte) 0x12, (byte) 0x34,
            (byte) 0x56, (byte) 0x78};

    public static final byte CONNECTED_PING_OPEN_CONNECTION = (byte) 0x01;
    public static final byte UNCONNECTED_PING_OPEN_CONNECTION = (byte) 0x02;
    public static final byte OPEN_CONNECTION_REQUEST_1 = (byte) 0x05;
    public static final byte OPEN_CONNECTION_REQUEST_2 = (byte) 0x07;

}
