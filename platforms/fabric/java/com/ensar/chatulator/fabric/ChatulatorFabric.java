package com.ensar.chatulator.fabric;

import com.ensar.chatulator.Chatulator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;

public final class ChatulatorFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // ALLOW_CHAT: false zurueckgeben = Nachricht wird nicht gesendet
        ClientSendMessageEvents.ALLOW_CHAT.register(message -> !Chatulator.handleChatMessage(message));
    }
}
