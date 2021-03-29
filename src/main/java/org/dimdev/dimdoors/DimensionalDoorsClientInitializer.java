package org.dimdev.dimdoors;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.dimdev.dimdoors.block.ModBlocks;
import org.dimdev.dimdoors.block.entity.ModBlockEntityTypes;
import org.dimdev.dimdoors.client.DetachedRiftBlockEntityRenderer;
import org.dimdev.dimdoors.client.EntranceRiftBlockEntityRenderer;
import org.dimdev.dimdoors.client.ModSkyRendering;
import org.dimdev.dimdoors.entity.ModEntityTypes;
import org.dimdev.dimdoors.entity.MonolithEntity;
import org.dimdev.dimdoors.entity.ai.MonolithAggroGoal;
import org.dimdev.dimdoors.fluid.ModFluids;
import org.dimdev.dimdoors.network.client.ExtendedClientPlayNetworkHandler;
import org.dimdev.dimdoors.particle.ModParticleTypes;
import org.dimdev.dimdoors.particle.client.MonolithParticle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class DimensionalDoorsClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntityTypes.initClient();
        ModSkyRendering.initClient();
		ModFluids.initClient();
//        ModBlockEntityTypes.initClient();
		BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntityTypes.ENTRANCE_RIFT, ctx -> new EntranceRiftBlockEntityRenderer());
		BlockEntityRendererRegistry.INSTANCE.register(ModBlockEntityTypes.DETACHED_RIFT, ctx -> new DetachedRiftBlockEntityRenderer());
        ModBlocks.initClient();
		ModParticleTypes.initClient();

		registerListeners();
    }

    private void registerListeners() {
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> ((ExtendedClientPlayNetworkHandler) handler).getDimDoorsPacketHandler().init());

		ClientPlayConnectionEvents.DISCONNECT.register(((handler, client) -> {
			((ExtendedClientPlayNetworkHandler) handler).getDimDoorsPacketHandler().unregister();
		}));
	}
}
