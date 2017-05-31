package com.cjm721.overloaded.common.block.basic.container;

import com.cjm721.overloaded.common.OverloadedCreativeTabs;
import com.cjm721.overloaded.common.block.tile.infinity.TileInfiniteTank;
import com.cjm721.overloaded.common.config.OverloadedConfig;
import com.cjm721.overloaded.common.config.RecipeEnabledConfig;
import com.cjm721.overloaded.common.item.ModItems;
import com.cjm721.overloaded.common.storage.IHyperType;
import com.cjm721.overloaded.common.storage.LongFluidStack;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.Overloaded.MODID;
import static com.cjm721.overloaded.common.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;
import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class BlockInfiniteTank extends AbstractBlockInfiniteContainer implements ITileEntityProvider{

    public BlockInfiniteTank() {
        super(Material.GLASS);

        setRegistryName("infinite_tank");
        setUnlocalizedName("infinite_tank");

        setHardness(10);
        setLightOpacity(0);
        setCreativeTab(OverloadedCreativeTabs.TECH);
        register();
        GameRegistry.registerTileEntity(TileInfiniteTank.class, MODID + ":infinite_tank");
    }

    @Override
    public void registerRecipe() {
        if(OverloadedConfig.recipeEnabledConfig.infinityTank)
            GameRegistry.addRecipe(new ItemStack(this), "GDG", "DCD", "GDG", 'G', Blocks.IRON_BLOCK, 'D', Blocks.DIAMOND_BLOCK, 'C', ModItems.fluidCore);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel() {
        ModelResourceLocation location = new ModelResourceLocation(new ResourceLocation(MODID, "infinite_tank"), null);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, location);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileInfiniteTank();
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            ItemStack heldItem = playerIn.getHeldItem(hand);
            if(heldItem.isEmpty() && hand == EnumHand.MAIN_HAND) {
                LongFluidStack storedFluid = ((TileInfiniteTank) worldIn.getTileEntity(pos)).getStorage().getFluidStack();
                if(storedFluid == null || storedFluid.fluidStack == null) {
                    playerIn.sendStatusMessage(new TextComponentString("Fluid: EMPTY"), false);
                } else {
                    playerIn.sendStatusMessage(new TextComponentString(String.format("Fluid: %s Amount %,d", storedFluid.fluidStack.getLocalizedName(), storedFluid.amount)), false);
                }
            } else {
                TileEntity te = worldIn.getTileEntity(pos);
                if (te != null && te instanceof TileInfiniteTank) {
                    IFluidHandler handler = te.getCapability(FLUID_HANDLER_CAPABILITY, side);
                    FluidActionResult result = FluidUtil.interactWithFluidHandler(heldItem, handler,playerIn); // FluidUtil.interactWithFluidHandler(playerIn.getHeldItem(hand), te.getCapability(FLUID_HANDLER_CAPABILITY, facing), playerIn);
                    if(result.isSuccess())
                        playerIn.setHeldItem(hand, result.getResult());
                }
            }
        }
        return true;
    }

    @Override
    @Nullable
    protected IHyperType getHyperStack(IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);

        if(te != null && te instanceof TileInfiniteTank) {
            return te.getCapability(HYPER_FLUID_HANDLER, EnumFacing.UP).status();
        }
        return null;
    }
}
