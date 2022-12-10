package chiefarug.mods.complete_compost;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("complete_compost")
public class CompleteCompost {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "complete_compost";
    public static final ResourceLocation MODRL = new ResourceLocation(MODID, MODID);
    public static CreativeModeTab C_TAB;

    public CompleteCompost()
    {
        // Register all the stuff!
        Registry.init();
    }
}
