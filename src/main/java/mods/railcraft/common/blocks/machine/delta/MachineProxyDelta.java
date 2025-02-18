/*
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info This code is the property of CovertJaguar and may only be used
 * with explicit written permission unless otherwise specified on the license page at
 * http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.common.blocks.machine.delta;

import java.util.List;

import mods.railcraft.common.blocks.machine.IEnumMachine;
import mods.railcraft.common.blocks.machine.IMachineProxy;

import net.minecraft.client.renderer.texture.IIconRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class MachineProxyDelta implements IMachineProxy {

    @Override
    public IEnumMachine getMachine(int meta) {
        return EnumMachineDelta.fromId(meta);
    }

    @Override
    public List<? extends IEnumMachine> getCreativeList() {
        return EnumMachineDelta.getCreativeList();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        EnumMachineDelta.registerIcons(iconRegister);
    }
}
