package chiefarug.mods.complete_compost;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import static chiefarug.mods.complete_compost.Registry.MYSTICAL_DENYLIST;

public class MysticalCompostBlock extends CompostBlock {

	public MysticalCompostBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@Override
	void tryTick(BlockState aboveState, ServerLevel serverLevel, BlockPos abovePos, RandomSource random) {
		if (aboveState.is(MYSTICAL_DENYLIST)) return;

		aboveState.randomTick(serverLevel, abovePos, random);
	}
}
