package com.nemonotfound.nemosbettermending.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(net.minecraft.entity.ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {

	@ModifyVariable(method = "onPlayerCollision", at = @At("STORE"), ordinal = 0)
	private int init(int original, @Local PlayerEntity player) {
		int amount = original;
		if (amount > 0) {
			PlayerInventory inventory = player.getInventory();
			int inventorySize = inventory.size();

			for (int j = 0; j < inventorySize; j++) {
				ItemStack itemStack = inventory.getStack(j);
				String enchantments = itemStack.getEnchantments().toString();
				boolean hasMending = enchantments.contains("mending");

				if (hasMending) {
					amount = repairPlayerGears(itemStack, amount);
				}
			}
		}

		return amount;
	}

	@Unique
	private int repairPlayerGears(ItemStack itemStack, int amount) {
		if (itemStack.isDamaged()) {
			int repairAmount = Math.min(this.getMendingRepairAmount(amount), itemStack.getDamage());
			itemStack.setDamage(itemStack.getDamage() - repairAmount);
			int newAmount = amount - this.getMendingRepairCost(repairAmount);

			if (newAmount > 0) {
				return this.repairPlayerGears(itemStack, newAmount);
			}

			return 0;
		}

		return amount;
	}

	@Unique
	private int getMendingRepairCost(int repairAmount) {
		return repairAmount / 2;
	}

	@Unique
	private int getMendingRepairAmount(int experienceAmount) {
		return experienceAmount * 2;
	}
}