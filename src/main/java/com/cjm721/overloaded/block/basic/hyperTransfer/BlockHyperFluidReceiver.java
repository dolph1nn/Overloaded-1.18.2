package com.cjm721.overloaded.block.basic.hyperTransfer;

import com.cjm721.overloaded.Overloaded;
import com.cjm721.overloaded.block.basic.hyperTransfer.base.AbstractBlockHyperReceiver;
import com.cjm721.overloaded.block.tile.hyperTransfer.TileHyperFluidReceiver;
import com.cjm721.overloaded.client.render.dynamic.ImageUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static com.cjm721.overloaded.Overloaded.MODID;

public class BlockHyperFluidReceiver extends AbstractBlockHyperReceiver {

    public BlockHyperFluidReceiver() {
        super(getDefaultProperties());
    }

    @Override
    public void baseInit() {
        setRegistryName("hyper_fluid_receiver");
//        setTranslationKey("hyper_fluid_receiver");

//        GameRegistry.registerTileEntity(TileHyperFluidReceiver.class, MODID + ":hyper_fluid_receiver");
    }

    @Override
    @Nonnull
    protected String getType() {
        return "Fluid";
    }

    @Override
    @Nonnull
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileHyperFluidReceiver();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerModel() {
        super.registerModel();

        ImageUtil.registerDynamicTexture(
                new ResourceLocation(MODID, "textures/blocks/hyper_fluid_receiver.png"),
                Overloaded.cachedConfig.textureResolutions.blockResolution);
    }
}
