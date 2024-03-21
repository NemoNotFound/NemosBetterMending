package com.nemonotfound.nemosbettermending;

import com.nemonotfound.nemosbettermending.helper.ClumpsRepairGearsHelper;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NemosBetterMending implements ModInitializer {

	public static final String MOD_ID = "nemos-better-mending";
    public static final Logger log = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		log.info("Thank you for using Nemo's Better Mending!");

		ClumpsRepairGearsHelper.registerRepairEvent();
	}
}