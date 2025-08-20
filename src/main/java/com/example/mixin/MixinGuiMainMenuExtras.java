package com.example.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenuExtras {

    /**
     * Example usage of MixinExtras @WrapOperation to demonstrate the functionality.
     * This wraps the drawString method call to modify the text being drawn.
     */
    @WrapOperation(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    public int wrapDrawString(net.minecraft.client.gui.FontRenderer instance, String text, int x, int y, int color, Operation<Integer> original) {
        // Example: Add a prefix to any text being drawn on the main menu
        if (text != null && !text.isEmpty()) {
            text = "[MixinExtras] " + text;
        }
        return original.call(instance, text, x, y, color);
    }
}