package com.cjm721.overloaded.block;

import net.minecraft.block.BlockState;

import javax.annotation.Nonnull;

public abstract class ModBlockTile extends ModBlock {
  protected ModBlockTile(@Nonnull Properties properties) {
    super(properties);
  }

  @Override
  public final boolean hasTileEntity(BlockState state) {
    return true;
  }
}
