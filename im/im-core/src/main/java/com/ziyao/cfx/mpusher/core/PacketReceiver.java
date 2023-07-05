package com.ziyao.cfx.mpusher.core;

import com.ziyao.cfx.mpusher.api.Connection;
import com.ziyao.cfx.mpusher.api.Packet;

/**
 * @author ziyao zhang
 * @since 2023/7/3
 */
public interface PacketReceiver {
    void onReceive(Packet packet, Connection connection);
}
