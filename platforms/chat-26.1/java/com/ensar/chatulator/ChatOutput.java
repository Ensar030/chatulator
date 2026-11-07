package com.ensar.chatulator;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/** Chat-Ausgabe fuer Minecraft 26.1.x */
final class ChatOutput {
    private ChatOutput() {
    }

    static void show(Component message) {
        Minecraft.getInstance().gui.getChat().addClientSystemMessage(message);
    }
}
