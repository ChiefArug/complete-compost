package chiefarug.mods.complete_compost;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@net.neoforged.fml.common.Mod("complete_compost")
public class CompleteCompost {

    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "complete_compost";
    public static final Identifier MODRL = Identifier.fromNamespaceAndPath(MODID, MODID);

    public CompleteCompost(IEventBus modBus) {
        // Register all the stuff!
        Registry.init(modBus);
    }
}
