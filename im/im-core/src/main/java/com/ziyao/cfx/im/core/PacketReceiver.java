package com.ziyao.cfx.im.core;

import com.ziyao.cfx.im.api.Connection;
import com.ziyao.cfx.im.api.Packet;

/**
 * @author ziyao zhang
 * @since 2023/7/3
 */
public interface PacketReceiver {
    void onReceive(Packet packet, Connection connection);
}
