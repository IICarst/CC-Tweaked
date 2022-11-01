package dan200.computercraft.shared.light;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LightBlock extends Block {
    public LightBlock( Properties properties ) {
        super( properties );
        System.out.println("block");
    }

    @Override
    public void stepOn( Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity ) {
        super.stepOn( pLevel, pPos, pState, pEntity );
        BlockEntity tileEntity = pLevel.getBlockEntity( pPos );
        if( tileEntity instanceof LightTile ) {
            ((LightTile) tileEntity).setColor( 0xff00ff00 );
        }
    }
}
