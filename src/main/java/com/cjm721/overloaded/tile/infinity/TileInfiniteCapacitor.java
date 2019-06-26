package com.cjm721.overloaded.tile.infinity;

import com.cjm721.overloaded.storage.energy.LongEnergyStorage;
import com.cjm721.overloaded.tile.ModTiles;
import com.cjm721.overloaded.util.IDataUpdate;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.overloaded.util.CapabilityHyperEnergy.HYPER_ENERGY_HANDLER;
import static net.minecraftforge.energy.CapabilityEnergy.ENERGY;

public class TileInfiniteCapacitor extends AbstractTileInfinityStorage implements IDataUpdate {

  @Nonnull private final LongEnergyStorage energyStorage;

  public TileInfiniteCapacitor() {
    super(ModTiles.infiniteCapacitor);
    energyStorage = new LongEnergyStorage(this);
  }

  @Override
  @Nonnull
  public CompoundNBT write(@Nonnull CompoundNBT compound) {
    super.write(compound);
    CompoundNBT energy = energyStorage.serializeNBT();
    compound.put("Energy", energy);
    return compound;
  }

  @Override
  public void read(@Nonnull CompoundNBT compound) {
    super.read(compound);
    energyStorage.deserializeNBT((CompoundNBT) compound.get("Energy"));
  }

  public LongEnergyStorage getStorage() {
    return energyStorage;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(
      @Nonnull Capability<T> capability, @Nullable Direction facing) {
    if (capability == ENERGY || capability == HYPER_ENERGY_HANDLER) {
      return LazyOptional.of(() -> energyStorage).cast();
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public void dataUpdated() {
    markDirty();
  }
}