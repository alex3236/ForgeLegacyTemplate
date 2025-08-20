package com.example.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Comprehensive example demonstrating MixinExtras injection types.
 * This mixin demonstrates the most commonly used MixinExtras features
 * that are guaranteed to work with MixinExtras 0.2.0.
 */
@Mixin(GuiMainMenu.class)
public class MixinExtrasExamples {

    /**
     * Example: Wrap drawString method calls to modify text rendering
     * This is a safe and commonly used MixinExtras pattern
     */
    @WrapOperation(
        method = "drawScreen", 
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I")
    )
    private int wrapDrawString(net.minecraft.client.gui.FontRenderer fontRenderer, String text, int x, int y, int color, Operation<Integer> original) {
        // Example: Modify any text drawn on the main menu
        if (text != null && text.contains("Minecraft")) {
            text = text + " (Enhanced with MixinExtras!)";
        }
        return original.call(fontRenderer, text, x, y, color);
    }

    /**
     * Example: Wrap button creation to modify button properties
     * Demonstrates wrapping constructor calls
     */
    @WrapOperation(
        method = "initGui", 
        at = @At(value = "NEW", target = "net/minecraft/client/gui/GuiButton")
    )
    private net.minecraft.client.gui.GuiButton wrapButtonCreation(int buttonId, int x, int y, String buttonText, Operation<net.minecraft.client.gui.GuiButton> original) {
        // Example: Add a prefix to all button text on the main menu
        if (buttonText != null) {
            buttonText = ">>> " + buttonText;
        }
        return original.call(buttonId, x, y, buttonText);
    }
}