package chiefarug.mods.complete_compost;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

import static chiefarug.mods.complete_compost.Registry.CROP_ALLOWLIST;
import static chiefarug.mods.complete_compost.Registry.CROP_DENYLIST;

public class CompostBlock extends BaseCompostBlock{

	public CompostBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@Override
	void tryTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
		if (state.is(CROP_DENYLIST)) return;

		if (state.getBlock() instanceof CropBlock c || state.is(CROP_ALLOWLIST)) {
			state.randomTick(serverLevel, pos, random);
		}
	}
}
