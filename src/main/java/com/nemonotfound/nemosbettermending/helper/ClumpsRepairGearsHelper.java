package com.nemonotfound.nemosbettermending.helper;

import com.blamejared.clumps.api.events.ClumpsEvents;
import com.blamejared.clumps.api.events.RepairEvent;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ClumpsRepairGearsHelper {

    public static void registerRepairEvent() {
        if (isClumpsLoaded()) {
            ClumpsEvents.REPAIR_EVENT.register(event -> {
                repairDamagedMendingGears(event);

                return null;
            });
        }
    }

    public static boolean isClumpsLoaded() {
        return FabricLoader.getInstance().isModLoaded("clumps");
    }

    private static void repairDamagedMendingGears(RepairEvent event) {
        final PlayerEntity player = event.getPlayer();
        final Inventory inventory = player.getInventory();

        for(int i = 0; i < inventory.size(); i++) {
            final ItemStack itemStack = inventory.getStack(i);

            if(RepairGearHelper.isRepairable(itemStack)) {
                event.setValue(RepairGearHelper.repairPlayerGear(itemStack, event.getValue()));
            }
        }
    }
}
