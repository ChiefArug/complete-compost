package chiefarug.mods.compost;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import org.slf4j.Logger;

import java.util.Random;

import static chiefarug.mods.compost.Registry.FARMlAND;

public abstract class BaseCompostBlock extends Block {

	public BaseCompostBlock(Properties p_49795_) {
		super(p_49795_);
	}
	private static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos pos, Random random) {
		BlockPos above = pos.above();
		BlockState aboveState = serverLevel.getBlockState(above);

		serverLevel.scheduleTick(pos, aboveState.getBlock(), 1);
		LOGGER.debug("The trimps have wone");
	}

	@Override
	public boolean isRandomlyTicking(BlockState p_49921_) {
		return true;
	}

	@Override
	public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = serverLevel.getBlockState(abovePos);

		LOGGER.debug("WE HAS TICKED! THE TRIMPS ARE WONNEING HARDER");

		//Skip all the blocks in between
		while (aboveState.getBlock() instanceof BaseCompostBlock || aboveState.is(FARMlAND)){
			abovePos = abovePos.above();
			aboveState = serverLevel.getBlockState(abovePos);
		}

		if (aboveState.isRandomlyTicking()) {
			tryTick(aboveState, serverLevel, abovePos, random);
		}
	}

	abstract void tryTick(BlockState aboveState, ServerLevel serverLevel, BlockPos abovePos, Random random);

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
		return true;
	}
}
