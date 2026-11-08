package cn.adwadg.murasame.registry;

import cn.adwadg.murasame.Murasame;
import cn.adwadg.murasame.specialeffects.BladeSoulEffect;
import cn.adwadg.murasame.specialeffects.MurasameBlessing;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 拔刀剑 SE（Special Effect，特殊效果）注册。
 *
 * <p>1.21.1（Resharped 2.x）的注册方式与 1.20.1 一致：</p>
 * <pre>
 * DeferredRegister.create(SpecialEffect.REGISTRY_KEY, MOD_ID)
 * </pre>
 * <p>{@code SpecialEffect.REGISTRY_KEY} 是前置模组暴露的
 * {@code ResourceKey<Registry<SpecialEffect>>}，不要自己造 key。</p>
 *
 * <p>SE 挂到刀上的方式是在 named_blades JSON 里写
 * {@code "properties": { "special_effects": ["murasame:murasame_blessing"] }}。</p>
 */
public final class MurasameSpecialEffects {

    public static final DeferredRegister<SpecialEffect> SPECIAL_EFFECTS =
            DeferredRegister.create(SpecialEffect.REGISTRY_KEY, Murasame.MOD_ID);

    /** 丛雨祝福：0 级即可生效，可复制、可移除 */
    public static final DeferredHolder<SpecialEffect, MurasameBlessing> MURASAME_BLESSING =
            SPECIAL_EFFECTS.register("murasame_blessing", MurasameBlessing::new);

    /** 刀魂：需要一定等级才生效 */
    public static final DeferredHolder<SpecialEffect, BladeSoulEffect> BLADE_SOUL =
            SPECIAL_EFFECTS.register("blade_soul", BladeSoulEffect::new);

    private MurasameSpecialEffects() {
    }
}
