package cn.adwadg.murasame.util;

import cn.adwadg.murasame.Murasame;
import mods.flammpfeil.slashblade.registry.slashblade.SlashBladeDefinition;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * 取丛雨丸（以及任意命名刀）的工具类。
 *
 * <p>命名刀不是普通物品，它的「真实内容」是一条 {@link SlashBladeDefinition}
 * 注册表数据。要拿到一把能用的刀，正确姿势是从注册表里查定义，再调用
 * {@link SlashBladeDefinition#getBlade()}——它会自动把 BladeState
 * （模型、贴图、攻击力、附魔、SE）全部塞进 ItemStack。</p>
 *
 * <p>用法：</p>
 * <pre>
 * ItemStack blade = MurasameBlades.getBlade(level.registryAccess(), MurasameBlades.MURASAMEMARU);
 * </pre>
 */
public final class MurasameBlades {

    /** data/murasame/slashblade/named_blades/murasamemaru.json */
    public static final ResourceLocation MURASAMEMARU = Murasame.id("murasamemaru");

    /** data/murasame/slashblade/named_blades/murasamemaru_awakened.json */
    public static final ResourceLocation MURASAMEMARU_AWAKENED = Murasame.id("murasamemaru_awakened");

    /**
     * 按 ID 取一把命名刀。
     *
     * @param registries 任何 {@code HolderLookup.Provider}，例如
     *                   {@code level.registryAccess()}、
     *                   {@code server.registryAccess()}、
     *                   {@code params.holders()}（创造标签里）
     * @param id         刀的 ID，例如 {@link #MURASAMEMARU}
     * @return 带完整 BladeState 的 ItemStack；找不到时返回 {@link ItemStack#EMPTY}
     */
    public static ItemStack getBlade(HolderLookup.Provider registries, ResourceLocation id) {
        if (registries == null || id == null) {
            return ItemStack.EMPTY;
        }
        ResourceKey<SlashBladeDefinition> key =
                ResourceKey.create(SlashBladeDefinition.REGISTRY_KEY, id);
        return registries.lookup(SlashBladeDefinition.REGISTRY_KEY)
                .flatMap(registry -> registry.get(key))
                .map(holder -> holder.value().getBlade())
                .orElse(ItemStack.EMPTY);
    }

    /** 判断一个 ItemStack 是不是本模组命名空间下的命名刀 */
    public static boolean isMurasameBlade(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        // TODO(判断方式)：1.21.1 里更稳的写法是读 BladeState 里的 bladeId。
        // 前置暴露的 API 若提供 ItemSlashBlade#getBladeId(ItemStack)，优先用它：
        //   ResourceLocation bladeId = ItemSlashBlade.getBladeId(stack);
        //   return bladeId != null && bladeId.getNamespace().equals(Murasame.MOD_ID);
        return stack.getItem().builtInRegistryHolder().key().location().getNamespace().equals(Murasame.MOD_ID);
    }

    private MurasameBlades() {
    }
}
