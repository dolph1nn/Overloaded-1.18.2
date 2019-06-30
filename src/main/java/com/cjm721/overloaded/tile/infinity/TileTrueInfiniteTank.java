package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.fluid.BigIntFluidStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperFluid.HYPER_FLUID_HANDLER;

public class TileTrueInfiniteTank extends AbstractTileHyperStorage implements IDataUpdate {

  private final BigIntFluidStorage fluidStorage;

  public TileTrueInfiniteTank() {
    super(ModTiles.trueInfiniteTank);
    fluidStorage = new BigIntFluidStorage(this);
  }

  @Override
  @Nonnull
  public CompoundNBT write(CompoundNBT compound) {
    return fluidStorage.write(super.write(compound));
  }

  @Override
  public void read(CompoundNBT compound) {
    super.read(compound);

    fluidStorage.read(compound);
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (cap == HYPER_FLUID_HANDLER) {
      return LazyOptional.of(() -> fluidStorage).cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}