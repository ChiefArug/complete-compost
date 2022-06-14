package chiefarug.mods.compost;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static chiefarug.mods.compost.Compost.C_TAB;
import static chiefarug.mods.compost.Compost.MODID;

public class Registry {

	private static final Logger LOGGER = LogUtils.getLogger();

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	public static final RegistryObject<Block> COMPOST_BLOCK = BLOCKS.register("compost", () -> new CompostBlock(BlockBehaviour.Properties.of(Material.DIRT).sound(SoundType.GRASS)));

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(C_TAB);

	public static final RegistryObject<Item> COMPOST_BLOCK_ITEM = ITEMS.register("compost", () -> new BlockItem(COMPOST_BLOCK.get(), ITEM_PROPERTIES));


	public static void init() {
	 LOGGER.info("HELLO FROM REGISTRY INIT");

	 //IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
	 ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	 BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}


}
