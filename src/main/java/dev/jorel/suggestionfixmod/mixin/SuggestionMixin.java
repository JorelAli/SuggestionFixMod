package dev.jorel.suggestionfixmod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.brigadier.ImmutableStringReader;

import net.minecraft.client.gui.screen.CommandSuggestor;

@Mixin(CommandSuggestor.class)
public class SuggestionMixin {
	@Redirect(method = "show()V", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/ImmutableStringReader;canRead()Z"))
	private boolean read(ImmutableStringReader reader) {
		return false;
	}
}
