package chiefarug.mods.complete_compost;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import static chiefarug.mods.complete_compost.Registry.CROP_ALLOWLIST;
import static chiefarug.mods.complete_compost.Registry.CROP_DENYLIST;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class CompostBlock extends BaseCompostBlock implements SimpleWaterloggedBlock {

	public CompostBlock(Properties p_49795_) {
		super(p_49795_);
		registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
	}

	@Override
	void tryTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
		if (state.is(CROP_DENYLIST)) return;

		if (state.getBlock() instanceof CropBlock|| state.is(CROP_ALLOWLIST)) {
			state.randomTick(serverLevel, pos, random);
		}
	}

	@Override
	protected Boolean isWaterlogged(BlockState state) {
		return state.getValue(WATERLOGGED);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(WATERLOGGED);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		BlockState bState = context.getLevel().getBlockState(pos);
		FluidState fState = bState.getFluidState();
		if (fState.is(Fluids.WATER)) {
			return defaultBlockState().setValue(WATERLOGGED, true);
		}
		return defaultBlockState();
	}

	//TODO: try fix particles appearing wonky on two of the sides
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (isWaterlogged(state)) {
			Direction direction = Direction.getRandom(random);
			BlockPos blockpos = pos.relative(direction);
			BlockState blockstate = level.getBlockState(blockpos);
			if (!state.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, direction.getOpposite())) {
				double x = pos.getX();
				double y = pos.getY();
				double z = pos.getZ();
				if (direction == Direction.DOWN) {
					y -= 0.05D;
					x += random.nextDouble();
					z += random.nextDouble();
				} else if (direction == Direction.UP) {
					if (random.nextDouble() < 0.9) return; //Show less particles on the top of the block
					y += 1.05D;
					x += random.nextDouble();
					z += random.nextDouble();
				} else {
					y += random.nextDouble() * 0.8D;
					if (direction.getAxis() == Direction.Axis.X) {
						z += random.nextDouble();
						if (direction == Direction.EAST) {
							++x;
						} else {
							x += 0.055D;
						}
					} else {
						x += random.nextDouble();
						if (direction == Direction.SOUTH) {
							++z;
						} else {
							z += 0.055D;
						}
					}
				}
				level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
