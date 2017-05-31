package com.cjm721.overloaded.common.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;

import javax.annotation.Nonnull;

public class MultiToolConfig {

    @Config.Comment({"Max range Multi-Tool can edit blocks [Default: 128]"})
    public int reach = 128;

    @Config.Comment({"Cost that is added on to every place [Default: 100]"})
    public int placeBaseCost = 100;

    @Config.Comment({"Cost per meter away [Default: 10]"})
    public int costPerMeterAway = 10;

    @Config.Comment({"Cost that is added on to every block break [Default: 100]"})
    public int breakBaseCost = 100;

    @Config.Comment({"Multiples the Hardness Cost by this. [Default: 1]"})
    public int breakCostMultiplier = 1;
}
