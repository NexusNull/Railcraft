/*
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info This code is the property of CovertJaguar and may only be used
 * with explicit written permission unless otherwise specified on the license page at
 * http://railcraft.info/wiki/info:license.
 */
package mods.railcraft.common.blocks.tracks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mods.railcraft.api.core.items.IToolCrowbar;
import mods.railcraft.api.tracks.ITrackPowered;
import mods.railcraft.common.carts.EntityLocomotive;
import mods.railcraft.common.carts.EntityLocomotive.LocoMode;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class TrackLocomotive extends TrackBaseRailcraft implements ITrackPowered {

    private boolean powered = false;
    private byte mode = 0;

    @Override
    public EnumTrack getTrackType() {
        return EnumTrack.LOCOMOTIVE;
    }

    @Override
    public IIcon getIcon() {
        if (isPowered()) {
            return getIcon(mode % LocoMode.VALUES.length);
        }
        return getIcon(LocoMode.VALUES.length + mode % LocoMode.VALUES.length);
    }

    @Override
    public boolean blockActivated(EntityPlayer player) {
        ItemStack current = player.getCurrentEquippedItem();
        if (current != null && current.getItem() instanceof IToolCrowbar) {
            IToolCrowbar crowbar = (IToolCrowbar) current.getItem();
            if (crowbar.canWhack(player, current, getX(), getY(), getZ())) {
                mode++;
                crowbar.onWhack(player, current, getX(), getY(), getZ());
                sendUpdateToClient();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onMinecartPass(EntityMinecart cart) {
        if (isPowered()) {
            if (cart instanceof EntityLocomotive) {
                ((EntityLocomotive) cart).setMode(LocoMode.VALUES[mode % LocoMode.VALUES.length]);
            }
        }
    }

    @Override
    public boolean isPowered() {
        return powered;
    }

    @Override
    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    @Override
    public int getPowerPropagation() {
        return 8;
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setBoolean("powered", powered);
        data.setByte("mode", mode);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        powered = data.getBoolean("powered");
        mode = data.getByte("mode");
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);

        data.writeBoolean(powered);
        data.writeByte(mode);
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);

        powered = data.readBoolean();
        mode = data.readByte();

        markBlockNeedsUpdate();
    }
}
