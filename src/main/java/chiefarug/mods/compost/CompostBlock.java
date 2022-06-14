package chiefarug.mods.compost;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import org.lwjgl.system.CallbackI;
import org.slf4j.Logger;

import java.util.Random;

import static chiefarug.mods.compost.Registry.COMPOST_BLOCK;

public class CompostBlock extends Block {


	public CompostBlock(Properties p_49795_) {
		super(p_49795_);
	}
	private static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos pos, Random random) {
		BlockPos above = pos.above();
		BlockState aboveState = serverLevel.getBlockState(above);

		//aboveState.randomTick(serverLevel, above, random);
		serverLevel.scheduleTick(pos, COMPOST_BLOCK.get(), 1);
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

		if (aboveState.isRandomlyTicking()) {
			LOGGER.debug("WE HAS TICKED! THE TRIMPS ARE WONNEING HARDER");
			double d0 = aboveState.getShape(serverLevel, abovePos).max(Direction.Axis.Y, 0.5D, 0.5D) + 0.03125D;
			double d1 = (double)0.13125F;
			double d2 = (double)0.7375F;
			double d3 = random.nextGaussian() * 0.02D;
			double d4 = random.nextGaussian() * 0.02D;
			double d5 = random.nextGaussian() * 0.02D;
			serverLevel.addParticle(ParticleTypes.COMPOSTER, (double)abovePos.getX() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), (double)abovePos.getY() + d0 + (double)random.nextFloat() * (1.0D - d0), (double)abovePos.getZ() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), d3, d4, d5);
			aboveState.randomTick(serverLevel, abovePos, random);
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
		return super.canSustainPlant(state, world, pos, facing, plantable);
	}
}
