package net.torchbox.net.pe;

import net.torchbox.net.Packet;

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

public class McpeServerExchange extends Packet {

    public byte id = 0x04;

    public String serverPubKey;
    public String randKey;

    public void _encode() {
        this.getBuffer().putLong(serverPubKey.length());
        this.getBuffer().putLong(randKey.length());
    }

    public void _decode() {

    }

}
