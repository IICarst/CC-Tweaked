package dan200.computercraft.shared.turtle.core;

import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleCommand;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import javax.annotation.Nonnull;
import java.util.List;

public class TurtleCheckSmeltableCommand implements ITurtleCommand {

    private final int slot;

    public TurtleCheckSmeltableCommand( int slot )
    {
        this.slot = slot;
    }

    @Nonnull
    @Override
    public TurtleCommandResult execute(@Nonnull ITurtleAccess turtle )
    {
        if(this.slot > 15 || this.slot < 0) return TurtleCommandResult.failure("invalid slot");

        Container inventory = turtle.getInventory();
        ItemStack stack = inventory.getItem( this.slot );

        List<SmeltingRecipe> recipes = turtle.getLevel().getRecipeManager().getAllRecipesFor(RecipeType.SMELTING);
        for (SmeltingRecipe recipe: recipes)
        {
            //recipe.ingredient.itemStacks
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            for(Ingredient ingredient: ingredients)
            {
                for(ItemStack ingredientStack: ingredient.getItems())
                {
                    if(ingredientStack.getItem().equals(stack.getItem()))
                    {
                        return TurtleCommandResult.success(new Object[]{true});
                    }
                }
            }
        }
        return TurtleCommandResult.success(new Object[]{false});
    }
}
