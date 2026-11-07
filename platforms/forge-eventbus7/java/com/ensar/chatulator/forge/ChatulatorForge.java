package com.ensar.chatulator.forge;

import com.ensar.chatulator.Chatulator;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Chatulator.MOD_ID)
public final class ChatulatorForge {
    public ChatulatorForge(FMLJavaModLoadingContext context) {
        // EventBus 7: ein Predicate-Listener, der true zurueckgibt, bricht das Event ab
        ClientChatEvent.BUS.addListener(ChatulatorForge::onClientChat);
    }

    private static boolean onClientChat(ClientChatEvent event) {
        return Chatulator.handleChatMessage(event.getMessage());
    }
}
