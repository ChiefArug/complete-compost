package chiefarug.mods.complete_compost;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("complete_compost")
public class CompleteCompost {
    public static final String MODID = "complete_compost";
    public static final CreativeModeTab C_TAB = new CreativeModeTab("complete_compost") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registry.COMPOST_BLOCK_ITEM.get());
        }
    };

    public CompleteCompost()
    {
        // Register all the stuff!
        Registry.init();
    }
}
