package cn.adwadg.murasame.registry;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.util.MurasameBlades;
import mods.flammpfeil.slashblade.SlashBladeCreativeGroup;
import mods.flammpfeil.slashblade.registry.SlashBladeItems;
import mods.flammpfeil.slashblade.registry.slashblade.SlashBladeDefinition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Comparator;

/**
 * 创造模式标签栏：「丛雨」。
 *
 * <p>1.21.1 的要点：</p>
 * <ul>
 *   <li>{@code DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID)}；</li>
 *   <li>用 {@code withTabsBefore(...)} 排在前置模组的拔刀剑页后面；</li>
 *   <li>填充内容时，{@code ItemDisplayParameters#holders()} 提供了
 *       {@code HolderLookup.Provider}，可以直接查 SlashBlade 的命名刀注册表，
 *       不需要（也不应该在）服务端代码里碰客户端渲染类。</li>
 * </ul>
 */
public final class MurasameCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Murasame.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MURASAME_TAB =
            CREATIVE_MODE_TABS.register("murasame_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.murasame.tab"))
                    // 排在拔刀剑页之后；若前置版本没有这个字段，删掉这一行即可
                    .withTabsBefore(SlashBladeCreativeGroup.SLASHBLADE_GROUP.getKey())
                    .icon(() -> new ItemStack(SlashBladeItems.SLASHBLADE.get()))
                    .displayItems((params, output) -> {
                        // 1) 所有 murasame 命名空间的命名刀（数据驱动，含丛雨丸）
                        params.holders().lookup(SlashBladeDefinition.REGISTRY_KEY).ifPresent(registry ->
                                registry.listElements()
                                        .filter(ref -> ref.key().location().getNamespace().equals(Murasame.MOD_ID))
                                        .sorted(Comparator.comparing((Holder.Reference<SlashBladeDefinition> ref) -> ref.key().location().toString()))
                                        .forEach(ref -> output.accept(ref.value().getBlade()))
                        );

                        // 2) 本模组自己注册的普通物品
                        output.accept(new ItemStack(MurasameItems.MURASAMEMARU.get()));

                        // 3) 想额外放一把「觉醒态」的刀，直接用工具方法取
                        // ItemStack awakened = MurasameBlades.getBlade(params.holders(),
                        //         MurasameBlades.MURASAMEMARU_AWAKENED);
                        // if (!awakened.isEmpty()) output.accept(awakened);
                    })
                    .build());

    private MurasameCreativeTabs() {
    }
}
