package cn.adwadg.murasame.registry;

import cn.adwadg.murasame.Murasame;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 丛雨丸物品注册。
 *
 * <h2>先读这段，再决定要不要用本类</h2>
 * <p>SlashBlade 的「命名刀」在 1.21.1 是<b>数据驱动</b>的：只要在</p>
 * <pre>src/main/resources/data/murasame/slashblade/named_blades/murasamemaru.json</pre>
 * <p>里写好定义，前置模组会在启动时把它注册成一把带 BladeState 的刀，
 * 物品本体复用 {@code slashblade:slashblade}。也就是说——<b>纯数据驱动的情况下，
 * 这里一个物品都不用注册</b>。</p>
 *
 * <p>但如果你想要一个「独立物品 ID」的丛雨丸（例如想给它单独写配方、
 * 单独挂 tooltip、或者想让 JSON 里 {@code "item": "murasame:murasamemaru"}），
 * 就需要在本类注册一个 {@link ItemSlashBlade} 子类实例。</p>
 *
 * <p><b>注意：</b>1.20.1 → 1.21.1 之间 SlashBlade 的 BladeState 从 Forge Capability
 * 改成了 NeoForge 的 capability / 数据组件方案。自定义 ItemSlashBlade 能不能直接拿到
 * BladeState，取决于前置版本；如果进游戏发现刀是「空刀」（没有模型、没有耐久），
 * 说明还需要在 {@code RegisterCapabilitiesEvent} 里补注册，见下方 TODO。</p>
 */
public final class MurasameItems {

    /** 本模组的物品注册表 */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Murasame.MOD_ID);

    /**
     * 丛雨丸（独立物品版本）。
     *
     * <p>如果你只用数据驱动（推荐），把下面两行删掉即可，
     * 同时把 named_blades JSON 里的 {@code "item"} 改回 {@code "slashblade:slashblade"}。</p>
     *
     * <p>TODO(BladeState)：若进游戏是空刀，需要在
     * {@code RegisterCapabilitiesEvent} 里为 {@code ITEMS} 注册 SlashBlade 的
     * BladeState capability（1.21.1 的注册事件类在
     * {@code net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent}）。</p>
     */
    public static final DeferredItem<ItemSlashBlade> MURASAMEMARU =
            ITEMS.registerItem(
                    "murasamemaru",
                    props -> new ItemSlashBlade(Tiers.DIAMOND, 4, -2.4F, props),
                    new Item.Properties()   // 拔刀剑靠 BladeState 的耐久，不需要 stacksTo
            );

    private MurasameItems() {
    }
}
