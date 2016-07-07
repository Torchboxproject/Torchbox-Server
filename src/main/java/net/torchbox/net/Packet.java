package net.torchbox.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Base64;

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

public abstract class Packet {

    ByteBuffer buffer;

    public abstract void _encode();

    public abstract void _decode();

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void putAddress(InetAddress address, short port, ByteBuffer buffer) {
        byte[] bytes = address.getAddress();
        for(byte b : bytes) {
            buffer.put((byte)(b & 0xFF));
        }
        this.getBuffer().putShort(port);
    }

    public void getAddress(InetAddress address, short port, ByteBuffer buffer) {
        byte[] bytes = {(byte)(this.buffer.get() & 0xFF), (byte)(this.buffer.get() & 0xFF), (byte)(this.buffer.get()
                & 0xFF), (byte)(this.buffer.get() & 0xFF)};
        byte[] byteAddr = address.getAddress();
        try {
            address = InetAddress.getByAddress(bytes);
        }catch(UnknownHostException e) {
            e.printStackTrace();
        }
        port = buffer.getShort();
    }

    public void putString(String string, ByteBuffer buffer) {
        buffer.putShort((short) string.getBytes().length);
        buffer.put(string.getBytes());
    }

    public String getString(ByteBuffer buffer) {
        return new String(buffer.array());
    }

    public void put(byte b) {

    }
}
