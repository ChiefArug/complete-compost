package chiefarug.mods.complete_compost;

import net.minecraft.core.registries.Registries;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

import static chiefarug.mods.complete_compost.CompleteCompost.MODID;
import static chiefarug.mods.complete_compost.CompleteCompost.MODRL;

@SuppressWarnings("unused")
public class Registry {

	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> MYSTICAL_COMPOST_BREAK = registerSound("block.mystical_compost.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> MYSTICAL_COMPOST_STEP = registerSound("block.mystical_compost.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> MYSTICAL_COMPOST_PLACE = MYSTICAL_COMPOST_BREAK;//registerSound("block.mystical_compost.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> MYSTICAL_COMPOST_HIT = MYSTICAL_COMPOST_BREAK;//registerSound("block.mystical_compost.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> MYSTICAL_COMPOST_FALL = MYSTICAL_COMPOST_BREAK;//registerSound("block.mystical_compost.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> WATERLOGGED_COMPOST_STEP = registerSound("block.compost.step_waterlogged");

	public static final SoundType MYSTICAL_COMPOST_SOUND = new DeferredSoundType(0.8F, 1, MYSTICAL_COMPOST_BREAK, MYSTICAL_COMPOST_STEP, MYSTICAL_COMPOST_PLACE, MYSTICAL_COMPOST_HIT, MYSTICAL_COMPOST_FALL);
	public static final SoundType WATERLOGGED_COMPOST = new DeferredSoundType(1, 1, () -> SoundEvents.GRAVEL_BREAK, WATERLOGGED_COMPOST_STEP, () -> SoundEvents.GRAVEL_PLACE, () -> SoundEvents.GRAVEL_HIT, () -> SoundEvents.GRAVEL_FALL);
	public static final SoundType DEFAULT_COMPOST = SoundType.GRAVEL;

	private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
	public static final DeferredBlock<CompostBlock> COMPOST_BLOCK = registerBlock("compost", CompostBlock::new, () ->
			BlockBehaviour.Properties.of()
					.sound(SoundType.GRAVEL)
					.strength(0.5f));
	public static final DeferredBlock<MysticalCompostBlock> MYSTICAL_COMPOST_BLOCK = registerBlock("mystical_compost", MysticalCompostBlock::new, () ->
			BlockBehaviour.Properties.of()
					.sound(MYSTICAL_COMPOST_SOUND)
					.strength(1f));
	public static final DeferredBlock<CompostBlock> LUMINANT_COMPOST_BLOCK = registerBlock("luminant_compost", CompostBlock::new, () -> BlockBehaviour.Properties.of()
			.sound(SoundType.GRAVEL)
			.strength(1.0F)
			.lightLevel(_s -> 15));

	private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
	private static final Item.Properties ITEM_PROPERTIES = new Item.Properties();

	public static final DeferredItem<BlockItem> COMPOST_BLOCK_ITEM = registerBlockItem(COMPOST_BLOCK);
	public static final DeferredItem<BlockItem> MYSTICAL_COMPOST_BLOCK_ITEM = registerBlockItem(MYSTICAL_COMPOST_BLOCK);
	public static final DeferredItem<BlockItem> LUMINANT_COMPOST_BLOCK_ITEM = registerBlockItem(LUMINANT_COMPOST_BLOCK);


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
	//Tall crops is for blocks that should have the topmost block in the stack be ticked rather than the bottom one (ie bamboo)
	//
	public static final TagKey<Block> CROP_ALLOWLIST = BlockTags.create(MODRL.withPath("crop_allowlist"));
	public static final TagKey<Block> CROP_DENYLIST = BlockTags.create(MODRL.withPath("crop_denylist"));
	public static final TagKey<Block> MYSTICAL_DENYLIST = BlockTags.create(MODRL.withPath("mystical_denylist"));
	public static final TagKey<Block> FARMlAND = BlockTags.create(MODRL.withPath("farmland"));
	public static final TagKey<Item> DIRT_DENYLIST = ItemTags.create(MODRL.withPath("dirt_denylist"));
	public static final TagKey<Block> TALL_CROPS = BlockTags.create(MODRL.withPath("tall_crops"));
	public static final TagKey<Block> PLANT_NEEDS_WATER = BlockTags.create(MODRL.withPath("plant_needs_water"));


	public static void init(IEventBus modBus) {
	 SOUNDS.register(modBus);
	 BLOCKS.register(modBus);
	 ITEMS.register(modBus);
	 modBus.addListener(Registry::registerTab);
	}

	private static <T extends Block> DeferredBlock<T> registerBlock(String id, Function<BlockBehaviour.Properties, T> c, Supplier<BlockBehaviour.Properties> p) {
		return BLOCKS.registerBlock(id, c, p);
	}
	private static DeferredItem<BlockItem> registerBlockItem(DeferredHolder<Block, ? extends Block> block) {
		return ITEMS.registerSimpleBlockItem(block);
	}
	private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String id) {
		return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(MODRL.withPath(id)));
	}


}
