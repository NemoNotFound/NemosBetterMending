package com.nemonotfound.nemosbettermending.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.nemonotfound.nemosbettermending.helper.ClumpsRepairGearsHelper;
import com.nemonotfound.nemosbettermending.helper.RepairGearHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	@ModifyVariable(method = "onPlayerCollision", at = @At("STORE"), ordinal = 0)
	private int init(int original, @Local(argsOnly = true) PlayerEntity player) {
		int amount = original;

		if (!ClumpsRepairGearsHelper.isClumpsLoaded() && amount > 0) {
			final Inventory inventory = player.getInventory();

			for (int i = 0; i < inventory.size(); i++) {
				final ItemStack itemStack = inventory.getStack(i);

				if (RepairGearHelper.isRepairable(itemStack)) {
					amount = RepairGearHelper.repairPlayerGear(itemStack, amount);
				}
			}
		}

		return amount;
	}
}