package dan200.computercraft.shared.turtle.core;

import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleCommand;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class TurtleRenameCommand implements ITurtleCommand
{
    private final String newName;
    private final int slot;

    public TurtleRenameCommand( String newName, int slot )
    {
        this.newName = newName;
        this.slot = slot;
    }

    @Nonnull
    @Override
    public TurtleCommandResult execute( @Nonnull ITurtleAccess turtle )
    {
        if( newName.equals( "" ) ) return TurtleCommandResult.failure( "Invalid name" );
        String actualNewName = newName.substring( 0, Math.min( 40, newName.length() ) );

        Container inventory = turtle.getInventory();
        ItemStack stack = inventory.getItem( this.slot );

        if( stack.isEmpty() ) return TurtleCommandResult.failure( "No item in slot" );


        Component newHoverName = new TextComponent( actualNewName );
        stack.setHoverName( newHoverName );


        return TurtleCommandResult.success();
    }
}
