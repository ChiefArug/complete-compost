package chiefarug.mods.complete_compost;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static chiefarug.mods.complete_compost.Registry.FARMlAND;

public abstract class BaseCompostBlock extends Block {

	public BaseCompostBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos pos, Random random) {
		BlockPos above = pos.above();
		BlockState aboveState = serverLevel.getBlockState(above);

		serverLevel.scheduleTick(pos, aboveState.getBlock(), 1);
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

		//Skip all the blocks in between
		while (aboveState.getBlock() instanceof BaseCompostBlock || aboveState.is(FARMlAND)) {
			abovePos = abovePos.above();
			aboveState = serverLevel.getBlockState(abovePos);
		}

		//We need to tick the top bamboo/sugarcane block, not the bottom one.
		if (aboveState.is(Registry.TALL_CROPS)) {
			while (aboveState.is(Registry.TALL_CROPS)) {
				abovePos = abovePos.above();
				aboveState = serverLevel.getBlockState(abovePos);
			}
			abovePos = abovePos.below();
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
			if(isWaterlogged(state)) return true;
			for (Direction face : Direction.Plane.HORIZONTAL) {
				BlockState blockState = world.getBlockState(pos.relative(face));
				FluidState fluidState = world.getFluidState(pos.relative(face));
				if (fluidState.is(net.minecraft.tags.FluidTags.WATER) || blockState.is(Blocks.FROSTED_ICE)) {
					return  true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
		return isWaterlogged(state);
	}

	protected Boolean isWaterlogged(BlockState state) {
		return false;
	}

	@Override
	public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
		if (isWaterlogged(state)) {
			return Registry.WATERLOGGED_COMPOST;
		}
		return Registry.DEFAULT_COMPOST;
	}

	//This means that blocks like sugar cane treat it as a water source, but also means it leaves behind water when you break it.

	//@SuppressWarnings("deprecation")
	//@Override
	//public FluidState getFluidState(BlockState state) {
	//	return isWaterlogged(state) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	//}
}
