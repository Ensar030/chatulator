package com.ensar.chatulator;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

/**
 * Loader-unabhaengige Logik. Wird von Fabric, Forge und NeoForge (alle Versionen) aufgerufen.
 * Nutzt nur Vanilla-Klassen, die in allen unterstuetzten Minecraft-Versionen gleich heissen.
 */
public final class Chatulator {
    public static final String MOD_ID = "chatulator";

    private Chatulator() {
    }

    /**
     * Wird aufgerufen, bevor eine Chat-Nachricht gesendet wird.
     *
     * @return {@code true}, wenn die Nachricht eine Rechnung war und NICHT gesendet werden soll.
     */
    public static boolean handleChatMessage(String message) {
        if (!CalculatorUtils.isMathExpression(message)) {
            return false;
        }

        String expression = message.trim();
        String result = CalculatorUtils.calculateExpression(expression);

        ChatOutput.show(
                Component.literal("[Chatulator] ").withStyle(ChatFormatting.AQUA)
                        .append(Component.literal(expression + " = " + result).withStyle(ChatFormatting.WHITE))
        );

        return true;
    }
}
