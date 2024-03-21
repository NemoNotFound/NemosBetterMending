package com.nemonotfound.nemosbettermending.helper;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public class RepairGearHelper {

    public static int repairPlayerGear(ItemStack itemStack, int amount) {
        if (itemStack.isDamaged()) {
            int repairAmount = Math.min(getMendingRepairAmount(amount), itemStack.getDamage());
            itemStack.setDamage(itemStack.getDamage() - repairAmount);
            int newAmount = amount - getMendingRepairCost(repairAmount);

            return newAmount > 0 ? repairPlayerGear(itemStack, newAmount) : 0;
        }

        return amount;
    }

    public static boolean isRepairable(ItemStack itemStack) {
        return itemStack.isDamaged() && EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) > 0;
    }

    private static int getMendingRepairCost(int repairAmount) {
        return repairAmount / 2;
    }

    private static int getMendingRepairAmount(int experienceAmount) {
        return experienceAmount * 2;
    }
}
