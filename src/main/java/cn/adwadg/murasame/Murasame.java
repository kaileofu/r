package cn.adwadg.murasame;

import cn.adwadg.murasame.registry.MurasameCreativeTabs;
import cn.adwadg.murasame.registry.MurasameItems;
import cn.adwadg.murasame.registry.MurasameSpecialEffects;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

/**
 * Murasame（丛雨丸）—— SlashBlade: Resharped 附属模组主类。
 *
 * <p>移植自 Forge 1.20.1 版本（cn.adwadg.murasame），目标平台
 * Minecraft 1.21.1 / NeoForge 21.1.x / Java 21。</p>
 *
 * <p>NeoForge 1.21.1 的主类写法要点：</p>
 * <ul>
 *   <li>构造器可以直接注入 {@link IEventBus} 和 {@link ModContainer}，
 *       不再需要 FMLJavaModLoadingContext.get()；</li>
 *   <li>DeferredRegister 统一注册到 modEventBus；</li>
 *   <li>配置用 {@code modContainer.registerConfig(...)}。</li>
 * </ul>
 */
@Mod(Murasame.MOD_ID)
public class Murasame {

    /** 模组 ID，必须与 neoforge.mods.toml 的 modId 一致 */
    public static final String MOD_ID = "murasame";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Murasame(IEventBus modEventBus, ModContainer modContainer) {
        // ---- 注册表 ----
        MurasameItems.ITEMS.register(modEventBus);
        MurasameSpecialEffects.SPECIAL_EFFECTS.register(modEventBus);
        MurasameCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // ---- 生命周期 ----
        modEventBus.addListener(this::commonSetup);

        // TODO(config)：需要配置文件时，补一个 config/Config.java 再打开下面这行
        // modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("[Murasame] 丛雨丸 NeoForge 1.21.1 骨架已加载");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // TODO(setup)：网络通道、能力（capability）、数据生成等初始化放这里
            LOGGER.info("[Murasame] common setup 完成");
        });
    }

    /** 便捷方法：murasame:xxx */
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
