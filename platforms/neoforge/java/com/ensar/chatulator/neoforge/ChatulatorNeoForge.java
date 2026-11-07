package com.ensar.chatulator.neoforge;

import com.ensar.chatulator.Chatulator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Chatulator.MOD_ID, dist = Dist.CLIENT)
public final class ChatulatorNeoForge {
    public ChatulatorNeoForge() {
        NeoForge.EVENT_BUS.addListener(ChatulatorNeoForge::onClientChat);
    }

    private static void onClientChat(ClientChatEvent event) {
        if (Chatulator.handleChatMessage(event.getMessage())) {
            event.setCanceled(true);
        }
    }
}
