package mods.railcraft.common.plugins.buildcraft.triggers;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import buildcraft.api.statements.IStatementParameter;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class TriggerHasCart extends Trigger {

    @Override
    public boolean isTriggerActive(ForgeDirection side, TileEntity tile, IStatementParameter[] parameter) {
        if (tile instanceof IHasCart) {
            return ((IHasCart) tile).hasMinecart();
        }
        return false;
    }
}
