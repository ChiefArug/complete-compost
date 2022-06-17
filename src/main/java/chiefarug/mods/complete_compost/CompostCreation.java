package chiefarug.mods.complete_compost;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="compost")
public class CompostCreation {
	@SubscribeEvent
	static void listen(PlayerInteractEvent.RightClickBlock event) {
		Level level = event.getWorld();
		BlockPos pos = event.getPos();
		BlockState state = level.getBlockState(pos);
		ItemStack item = event.getItemStack();

		if (state.is(Blocks.COMPOSTER) && item.is(ItemTags.DIRT) && !item.is(Registry.DIRT_DENYLIST)) {
			int fillLevel = state.getValue(ComposterBlock.LEVEL);

			if (fillLevel > ComposterBlock.MIN_LEVEL) {
				Player player = event.getPlayer();
				int newFillLevel = Math.min(fillLevel,ComposterBlock.MAX_LEVEL) - 1;

				if (!player.getAbilities().instabuild) {
					item.shrink(1);
				}

				level.setBlock(pos, state.setValue(ComposterBlock.LEVEL, newFillLevel), 3);
				Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(Registry.COMPOST_BLOCK_ITEM.get()));
				player.swing(event.getHand());
				level.playSound(null, pos, SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 0.8F, 0.9F);
				event.setCanceled(true);
			}
		}
	}
}
