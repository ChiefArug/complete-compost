package chiefarug.mods.complete_compost.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import static chiefarug.mods.complete_compost.CompleteCompost.MODID;
import static chiefarug.mods.complete_compost.Registry.*;

@JeiPlugin
public class CompleteCompostJEIPlugin implements IModPlugin {

	private static final TextComponent newLine = new TextComponent("\n");

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(MODID, MODID);
	}

	@Override
	public void registerRecipes(IRecipeRegistration reg) {
		addIngredientInfo(reg, COMPOST_BLOCK_ITEM,
				new TranslatableComponent("description.complete_compost.generic_compost"),
				newLine,
				new TranslatableComponent("description.complete_compost.waterloggable_compost"),
				newLine,
				new TranslatableComponent("description.complete_compost.compost.limitations"),
				newLine,
				new TranslatableComponent("description.complete_compost.compost.crafting")
		);
		addIngredientInfo(reg, MYSTICAL_COMPOST_BLOCK_ITEM,
				new TranslatableComponent("description.complete_compost.generic_compost"),
				newLine,
				new TranslatableComponent("description.complete_compost.waterloggable_compost"),
				newLine,
				new TranslatableComponent("description.complete_compost.mystical_compost.limitations")
		);
		addIngredientInfo(reg, LUMINANT_COMPOST_BLOCK_ITEM,
				new TranslatableComponent("description.complete_compost.generic_compost"),
				newLine,
				new TranslatableComponent("description.complete_compost.waterloggable_compost"),
				newLine,
				new TranslatableComponent("description.complete_compost.compost.limitations"),
				newLine,
				new TranslatableComponent("description.complete_compost.luminant_compost.it_glows")
		);
	}

	private void addIngredientInfo(IRecipeRegistration reg, RegistryObject<Item> item, Component... text) {
		reg.addIngredientInfo(new ItemStack(item.get()), VanillaTypes.ITEM_STACK,
				text
		);
	}
}
