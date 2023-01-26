package chiefarug.mods.complete_compost.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import static chiefarug.mods.complete_compost.CompleteCompost.MODRL;
import static chiefarug.mods.complete_compost.Registry.COMPOST_BLOCK_ITEM;
import static chiefarug.mods.complete_compost.Registry.LUMINANT_COMPOST_BLOCK_ITEM;
import static chiefarug.mods.complete_compost.Registry.MYSTICAL_COMPOST_BLOCK_ITEM;

@JeiPlugin
public class CompleteCompostJEIPlugin implements IModPlugin {

	private static final Component newLine = Component.literal("\n");

	@Override
	public ResourceLocation getPluginUid() {
		return MODRL;
	}

	@Override
	public void registerRecipes(IRecipeRegistration reg) {
		addIngredientInfo(reg, COMPOST_BLOCK_ITEM,
				Component.translatable("description.complete_compost.generic_compost"),
				newLine,
				Component.translatable("description.complete_compost.waterloggable_compost"),
				newLine,
				Component.translatable("description.complete_compost.compost.limitations"),
				newLine,
				Component.translatable("description.complete_compost.compost.crafting")
		);
		addIngredientInfo(reg, MYSTICAL_COMPOST_BLOCK_ITEM,
				Component.translatable("description.complete_compost.generic_compost"),
				newLine,
				Component.translatable("description.complete_compost.waterloggable_compost"),
				newLine,
				Component.translatable("description.complete_compost.mystical_compost.limitations")
		);
		addIngredientInfo(reg, LUMINANT_COMPOST_BLOCK_ITEM,
				Component.translatable("description.complete_compost.generic_compost"),
				newLine,
				Component.translatable("description.complete_compost.waterloggable_compost"),
				newLine,
				Component.translatable("description.complete_compost.compost.limitations"),
				newLine,
				Component.translatable("description.complete_compost.luminant_compost.it_glows")
		);
	}

	private void addIngredientInfo(IRecipeRegistration reg, RegistryObject<Item> item, Component... text) {
		reg.addIngredientInfo(new ItemStack(item.get()), VanillaTypes.ITEM_STACK,
				text
		);
	}
}
