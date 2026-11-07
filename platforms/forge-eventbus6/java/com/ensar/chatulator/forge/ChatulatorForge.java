package com.ensar.chatulator.forge;

import com.ensar.chatulator.Chatulator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Chatulator.MOD_ID)
public final class ChatulatorForge {
    public ChatulatorForge() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(this::onClientChat);
        }
    }

    private void onClientChat(ClientChatEvent event) {
        if (Chatulator.handleChatMessage(event.getMessage())) {
            event.setCanceled(true);
        }
    }
}
