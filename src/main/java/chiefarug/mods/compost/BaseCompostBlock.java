package chiefarug.mods.compost;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Random;

import static chiefarug.mods.compost.Registry.FARMlAND;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public abstract class BaseCompostBlock extends Block implements SimpleWaterloggedBlock {

	public BaseCompostBlock(Properties p_49795_) {
		super(p_49795_);
		registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
	}

	private static final Logger LOGGER = LogUtils.getLogger();

	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("deprecation")
	@Override
	public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
		BlockPos abovePos = pos.above();
		BlockState aboveState = serverLevel.getBlockState(abovePos);

		LOGGER.debug("WE HAS TICKED! THE TRIMPS ARE WONNEING HARDER");

		//Skip all the blocks in between
		while (aboveState.getBlock() instanceof BaseCompostBlock || aboveState.is(FARMlAND)) {
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
		PlantType type = plantable.getPlantType(world, pos.relative(facing));

		//PlantTypes suck. Thank you, have a nice day.
		if (PlantType.BEACH.equals(type)) {
			if (state.getValue(WATERLOGGED)) {
				return true;
			}
			boolean hasWater = false;
			for (Direction face : Direction.Plane.HORIZONTAL) {
				BlockState blockState = world.getBlockState(pos.relative(face));
				FluidState fluidState = world.getFluidState(pos.relative(face));
				hasWater = blockState.is(Blocks.FROSTED_ICE);
				hasWater |= fluidState.is(net.minecraft.tags.FluidTags.WATER);
				if (hasWater)
					break; //No point continuing.
			}
			return hasWater;
		}
		return true;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(WATERLOGGED);
	}

	@Override
	public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getValue(WATERLOGGED);
	}

	@Override
	public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
		if (state.getValue(WATERLOGGED)) {
			return Registry.WATERLOGGED_COMPOST;
		}
		return Registry.DEFAULT_COMPOST;
	}
}
