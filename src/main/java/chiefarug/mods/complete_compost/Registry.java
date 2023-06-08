package chiefarug.mods.complete_compost;

import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static chiefarug.mods.complete_compost.CompleteCompost.MODID;
import static chiefarug.mods.complete_compost.CompleteCompost.MODRL;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class Registry {

	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
	public static final RegistryObject<SoundEvent> MYSTICAL_COMPOST_BREAK = registerSound("block.mystical_compost.place");
	public static final RegistryObject<SoundEvent> MYSTICAL_COMPOST_STEP = registerSound("block.mystical_compost.step");
	public static final RegistryObject<SoundEvent> MYSTICAL_COMPOST_PLACE = MYSTICAL_COMPOST_BREAK;//registerSound("block.mystical_compost.place");
	public static final RegistryObject<SoundEvent> MYSTICAL_COMPOST_HIT = MYSTICAL_COMPOST_BREAK;//registerSound("block.mystical_compost.place");
	public static final RegistryObject<SoundEvent> MYSTICAL_COMPOST_FALL = MYSTICAL_COMPOST_BREAK;//registerSound("block.mystical_compost.place");
	public static final RegistryObject<SoundEvent> WATERLOGGED_COMPOST_STEP = registerSound("block.compost.step_waterlogged");

	public static final SoundType MYSTICAL_COMPOST_SOUND = new ForgeSoundType(0.8F, 1, MYSTICAL_COMPOST_BREAK, MYSTICAL_COMPOST_STEP, MYSTICAL_COMPOST_PLACE, MYSTICAL_COMPOST_HIT, MYSTICAL_COMPOST_FALL);

	public static final SoundType WATERLOGGED_COMPOST = new ForgeSoundType(1, 1, () -> SoundEvents.GRAVEL_BREAK, WATERLOGGED_COMPOST_STEP, () -> SoundEvents.GRAVEL_PLACE, () -> SoundEvents.GRAVEL_HIT, () -> SoundEvents.GRAVEL_FALL);
	public static final SoundType DEFAULT_COMPOST = SoundType.GRAVEL;

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	public static final RegistryObject<Block> COMPOST_BLOCK = registerBlock("compost", () -> new CompostBlock(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(0.5f)));
	public static final RegistryObject<Block> MYSTICAL_COMPOST_BLOCK = registerBlock("mystical_compost", () -> new MysticalCompostBlock(BlockBehaviour.Properties.of().sound(MYSTICAL_COMPOST_SOUND).strength(1f)));
	public static final RegistryObject<Block> LUMINANT_COMPOST_BLOCK = registerBlock("luminant_compost", () -> new CompostBlock(BlockBehaviour.Properties.of().sound(SoundType.GRAVEL).strength(1.0F).lightLevel(_s -> 15)));

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final Item.Properties ITEM_PROPERTIES = new Item.Properties();

	public static final RegistryObject<Item> COMPOST_BLOCK_ITEM = registerBlockItem("compost", COMPOST_BLOCK);
	public static final RegistryObject<Item> MYSTICAL_COMPOST_BLOCK_ITEM = registerBlockItem("mystical_compost", MYSTICAL_COMPOST_BLOCK);
	public static final RegistryObject<Item> LUMINANT_COMPOST_BLOCK_ITEM = registerBlockItem("luminant_compost", LUMINANT_COMPOST_BLOCK);


	@SubscribeEvent
	static void registerTab(BuildCreativeModeTabContentsEvent event) {
		ResourceKey<CreativeModeTab> tab = event.getTabKey();
		if (tab == CreativeModeTabs.NATURAL_BLOCKS || tab == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(COMPOST_BLOCK_ITEM);
			event.accept(MYSTICAL_COMPOST_BLOCK_ITEM);
			event.accept(LUMINANT_COMPOST_BLOCK_ITEM);
		}
	}

	//Crop allowlist is for crops that do not extend CropBlock, but you still want to count as crops
	//Crop denylist is for crops that do extend CropBlock but do now want to count as crops. It overrides the whitelist if a block is in both.
	//Mystical denylist is for blocks that you do not want to allow mystical compost to tick
	//Farmland is for blocks that you want to allow the tick to pass through when going up.
	//Dirt denylist is for blocks that are tagged with the dirt tag that you do not want to be able to create compost, like moss. It is recommended any compostables be in this tag, otherwise issues happen.
	public static final TagKey<Block> CROP_ALLOWLIST = BlockTags.create(MODRL.withPath("crop_allowlist"));
	public static final TagKey<Block> CROP_DENYLIST = BlockTags.create(MODRL.withPath("crop_denylist"));
	public static final TagKey<Block> MYSTICAL_DENYLIST = BlockTags.create(MODRL.withPath("mystical_denylist"));
	public static final TagKey<Block> FARMlAND = BlockTags.create(MODRL.withPath("farmland"));
	public static final TagKey<Item> DIRT_DENYLIST = ItemTags.create(MODRL.withPath("dirt_denylist"));
	public static final TagKey<Block> TALL_CROPS = BlockTags.create(MODRL.withPath("tall_crops"));


	public static void init() {
	 IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
	 SOUNDS.register(bus);
	 BLOCKS.register(bus);
	 ITEMS.register(bus);
	}

	private static RegistryObject<Block> registerBlock(String id, Supplier<Block> s) {
		return BLOCKS.register(id, s);
	}
	private static RegistryObject<Item> registerBlockItem(String id, RegistryObject<Block> block) {
		return ITEMS.register(id, () -> new BlockItem(block.get(), ITEM_PROPERTIES));
	}
	private static RegistryObject<SoundEvent> registerSound(String id) {
		return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(MODRL.withPath(id)));
	}


}
