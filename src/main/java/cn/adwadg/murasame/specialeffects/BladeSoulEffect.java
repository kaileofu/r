package cn.adwadg.murasame.specialeffects;

import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;

/**
 * 刀魂（murasame:blade_soul）。
 *
 * <p>对应 1.20.1 版本里的 BladeSoulEffect：击杀累计到一定数量后觉醒，
 * 觉醒后的丛雨丸换成 awakened 模型 + 挂上本 SE。</p>
 *
 * <p>1.20.1 里这段逻辑写在 {@code PlayerSoulHandler} / {@code MurasameEvolutionTracker}，
 * 1.21.1 建议改成：</p>
 * <ol>
 *   <li>用 attachment（{@code AttachmentType<Integer>}）存击杀计数，别再手写 NBT 管理器；</li>
 *   <li>在 {@code LivingDeathEvent} 里累加；</li>
 *   <li>达到阈值时把刀的 BladeState 模型/贴图换成 awakened，并 addSpecialEffect。</li>
 * </ol>
 */
public class BladeSoulEffect extends SpecialEffect {

    /** 需要拔刀剑等级（魂量等级）达到 10 */
    public static final int REQUEST_LEVEL = 10;

    public BladeSoulEffect() {
        super(REQUEST_LEVEL, false, false);
    }

    // TODO(觉醒逻辑)：见类注释，配合 events 包里的监听器实现
}
