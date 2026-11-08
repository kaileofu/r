package cn.adwadg.murasame.specialeffects;

import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;

/**
 * 丛雨祝福（murasame:murasame_blessing）。
 *
 * <p>1.21.1 的 {@link SpecialEffect} 只有两个可用构造：</p>
 * <ul>
 *   <li>{@code SpecialEffect(int requestLevel)}</li>
 *   <li>{@code SpecialEffect(int requestLevel, boolean isCopiable, boolean isRemovable)}</li>
 * </ul>
 *
 * <p>SE 本身<b>没有</b>每 tick 回调，具体效果要用事件监听实现
 * （见 {@code events/MurasameEvents} 的 TODO）。判断「玩家当前是否吃到了这个 SE」用：
 * {@code SpecialEffect.isEffective(ResourceLocation seId, Entity entity)}。</p>
 */
public class MurasameBlessing extends SpecialEffect {

    /** 生效所需等级：0 = 无条件生效 */
    public static final int REQUEST_LEVEL = 0;

    public MurasameBlessing() {
        super(REQUEST_LEVEL, true, true);
    }

    // TODO(SE 效果)：示例——持刀时给玩家缓慢回复
    // 在 events/MurasameEvents#onPlayerTick 里：
    //   if (SpecialEffect.isEffective(Murasame.id("murasame_blessing"), player)) { ... }
}
