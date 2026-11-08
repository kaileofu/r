# Murasame — NeoForge 1.21.1 移植骨架

丛雨丸（Murasame）的 **SlashBlade: Resharped 附属模组**，从 Forge 1.20.1 移植到
**Minecraft 1.21.1 / NeoForge 21.1.x / Java 21** 的工程骨架。

- 原始 Forge 1.20.1 源码：<https://github.com/seliaYYDS/Slashblade-Murasame>（包名 `cn.adwadg.murasame`）
- 前置模组：SlashBlade: Resharped（modid `slashblade`，包名 `mods.flammpfeil.slashblade`）
  - [CurseForge](https://www.curseforge.com/minecraft/mc-mods/slashblade-resharped) ｜ [Modrinth](https://modrinth.com/mod/slashblade-resharped)
  - 本工程按 **2.1.9-1.21.1**（Modrinth version id `SA6IKdsb`）对齐

> ⚠️ 这是一个**能编译、能进游戏看到物品和 SE** 的骨架，不是 100% 功能完整的移植。
> 原版的刀魂觉醒、嵌刀石方块、丛雨实体、按键、网络包、成就/世界生成等模块
> **没有**搬过来，README 末尾给了待办清单。所有不确定的 API 都在代码里用
> `TODO(...)` 标了出来。

---

## 1. 目录结构

```
murasame-neoforge/
├── build.gradle                      # ModDevGradle 2.0.78 + Java 21
├── settings.gradle                   # 插件仓库 + foojay（自动下 JDK 21）
├── gradle.properties                 # 版本号集中管理
├── gradlew / gradlew.bat             # 带降级逻辑的启动脚本
├── gradle/wrapper/
│   ├── gradle-wrapper.properties     # Gradle 8.8
│   └── gradle-wrapper.jar            # ⚠️ 0 字节占位，需手动替换
├── .github/workflows/build.yml       # CI（用 setup-gradle，不依赖 wrapper jar）
├── .gitignore
└── src/main/
    ├── java/cn/adwadg/murasame/
    │   ├── Murasame.java                        # 主类入口
    │   ├── registry/
    │   │   ├── MurasameItems.java               # 丛雨丸物品注册
    │   │   ├── MurasameSpecialEffects.java      # SE 注册
    │   │   └── MurasameCreativeTabs.java        # 创造标签「丛雨」
    │   ├── specialeffects/
    │   │   ├── MurasameBlessing.java            # 丛雨祝福 SE
    │   │   └── BladeSoulEffect.java             # 刀魂 SE
    │   └── util/MurasameBlades.java             # 取命名刀的工具方法
    └── resources/
        ├── META-INF/neoforge.mods.toml          # 声明 slashblade 前置依赖
        ├── pack.mcmeta
        ├── assets/murasame/
        │   ├── lang/{zh_cn,en_us}.json
        │   └── models/murasame/README.txt       # 模型贴图占位说明
        └── data/murasame/slashblade/named_blades/
            ├── murasamemaru.json                # 名刀「丛雨丸」
            └── murasamemaru_awakened.json       # 觉醒态
```

---

## 2. 必须手动补全的文件

| 文件 | 为什么 | 怎么补 |
| --- | --- | --- |
| `gradle/wrapper/gradle-wrapper.jar` | 骨架里是 **0 字节占位**（无法凭空生成二进制） | 下载任意 NeoForge 1.21.1 MDK，把它里面的 `gradle/wrapper/gradle-wrapper.jar` 拷过来覆盖；或者执行 `gradle wrapper --gradle-version 8.8` 让系统 Gradle 重新生成 |
| `assets/murasame/models/murasame/*.obj`、`*.png` | 3D 模型和贴图是二进制，本骨架不提供 | 从原 1.20.1 丛雨丸 jar 的 `assets/murasame/models/murasame/` 里原样拷出（OBJ/PNG 格式 1.21.1 没变） |
| `assets/murasame/textures/logo.png`（可选） | mod 列表图标 | 任意 128×128 PNG |
| `config/Config.java`（可选） | 主类里 `registerConfig` 那行是注释状态 | 需要配置项时自己补，再把 `Murasame.java` 里那行取消注释 |

> `gradlew` 已经做了兜底：检测到 wrapper jar 为空/缺失时，会自动回退到系统 `gradle`
> （需要 Gradle 8.8+）。所以**即使不补 wrapper jar，只要本机装了 Gradle 也能构建**。

---

## 3. 版本矩阵

| 项 | 值 |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.249（`gradle.properties` 可改） |
| Java | 21 |
| Gradle | 8.8 |
| ModDevGradle | `net.neoforged.moddev` 2.0.78 |
| SlashBlade: Resharped | ≥ 2.0.3（工程依赖 2.1.9-1.21.1） |

依赖坐标（`build.gradle` 里已配好）：

```groovy
// Modrinth（默认）
implementation "maven.modrinth:8T53F0sy:SA6IKdsb"          // 2.1.9-1.21.1
// CurseForge（备选，注释状态）
// implementation "curse.maven:slashblade-resharped-1022428:8083106"  // 2.0.3-1.21.1
```

升级前置版本时只需要换 version id，查询：
`https://api.modrinth.com/v2/project/slashblade-resharped/version`

---

## 4. 构建

```bash
# 1) 补好 gradle-wrapper.jar 之后
./gradlew build

# 2) 或者直接用系统 Gradle
gradle build

# 产物
build/libs/murasame-1.0.0-neoforge-1.21.1.jar

# 开发环境跑游戏
./gradlew runClient
```

CI 用 `gradle/actions/setup-gradle` 提供 Gradle 8.8，所以 GitHub Actions 上
**不补 wrapper jar 也能过**（见 `.github/workflows/build.yml`）。

---

## 5. 1.20.1 → 1.21.1 的关键 API 对照

下面这些是我对着 **SlashBlade: Resharped 2.1.9-1.21.1** 的实际类名核对过的，
不是猜的：

| 用途 | Forge 1.20.1 | NeoForge 1.21.1（本工程用的） |
| --- | --- | --- |
| 物品注册表类 | `mods.flammpfeil.slashblade.init.SBItems` | `mods.flammpfeil.slashblade.registry.SlashBladeItems`（`SLASHBLADE`、`PROUDSOUL`… 都是 `DeferredHolder<Item, ? extends Item>`） |
| SE 基类 | `...registry.specialeffects.SpecialEffect` | 同路径；构造仍是 `SpecialEffect(int requestLevel)` / `(int, boolean isCopiable, boolean isRemovable)`，注册键是 `SpecialEffect.REGISTRY_KEY` |
| SE 注册表 | 自己 `DeferredRegister.create(SpecialEffect.REGISTRY_KEY, id)` | 同左；前置自己的实现见 `...registry.SpecialEffectsRegistry.SPECIAL_EFFECT` |
| 命名刀定义 | `SlashBladeDefinition` | 同路径 `...registry.slashblade.SlashBladeDefinition`，字段 `item / name / render / properties / enchantments / creativeGroup`，`getBlade()` 返回带 BladeState 的 ItemStack |
| 拔刀剑物品 | `ItemSlashBlade` | 构造 `ItemSlashBlade(Tier, int attackDamage, float attackSpeed, Item.Properties)` |
| 创造标签前置 | `SlashBladeCreativeGroup.SLASHBLADE_GROUP.getId()` | `SlashBladeCreativeGroup.SLASHBLADE_GROUP.getKey()`（`ResourceKey<CreativeModeTab>`） |
| 主类构造 | `FMLJavaModLoadingContext.get().getModEventBus()` | 直接注入 `IEventBus` + `ModContainer` |
| 依赖声明 | `mods.toml` + `mandatory=true` | `neoforge.mods.toml` + `type="required"` |
| ResourceLocation | `new ResourceLocation(ns, path)` | `ResourceLocation.fromNamespaceAndPath(ns, path)`（构造器已私有） |
| 配置注册 | `ModLoadingContext.get().registerConfig(...)` | `modContainer.registerConfig(...)` |
| 客户端渲染注册 | `FMLClientSetupEvent` 里 `EntityRenderers.register` | `EntityRenderersEvent.RegisterRenderers`（本骨架未涉及） |

---

## 6. 丛雨丸是怎么「出现」的

1.21.1 里命名刀是**纯数据**的，链路是：

```
data/murasame/slashblade/named_blades/murasamemaru.json
        ↓ 启动时被前置模组解析成 SlashBladeDefinition
        ↓ 注册进 Registry<SlashBladeDefinition>（key = murasame:murasamemaru）
创造标签 params.holders().lookup(...) → definition.getBlade() → 带 BladeState 的 ItemStack
```

所以：

- JSON 里 `"item"` 写 `murasame:murasamemaru` → 用本模组注册的独立物品；
  写 `slashblade:slashblade` → 复用前置的原版拔刀剑物品（推荐，最省事）。
- 刀的模型/贴图路径是 `murasame:models/murasame/murasamemaru.obj`，
  实际文件在 `assets/murasame/models/murasame/murasamemaru.obj`。
- SE 挂在 `"properties": { "special_effects": ["murasame:murasame_blessing"] }`，
  ID 必须和 `MurasameSpecialEffects` 里注册的名字一致。

---

## 7. 待办清单（原版功能还没搬的部分）

- [ ] **刀魂觉醒**：原 `MurasameEvolutionTracker` + `PlayerSoulHandler`。
      建议 1.21.1 用 `AttachmentType<Integer>` 存击杀数，别手写 NBT 管理器。
- [ ] **SE 实际效果**：`SpecialEffect` 没有 tick 回调，效果要写在事件里
      （`LivingHurtEvent` / `PlayerTickEvent` + `SpecialEffect.isEffective(id, entity)`）。
- [ ] **嵌刀石方块**：`EmbeddedBladeStoneBlock` + BlockEntity + Renderer（BER）。
- [ ] **丛雨实体**：`EntityMurasameSoul` + 模型/渲染器/属性注册。
- [ ] **按键**：`RegisterKeyMappingsEvent`（客户端专用，注意 `@EventBusSubscriber` 的 `value = Dist.CLIENT`）。
- [ ] **网络包**：NeoForge 1.21.1 用 `PayloadRegistrar`（`RegisterPayloadHandlersEvent`），
      不是旧的 `SimpleChannel`。
- [ ] **成就 / 世界生成**：数据生成器 `DataGenerator` + `runData`（配置已写好）。
- [ ] **配方**：`data/murasame/recipe/*.json`（1.21 起目录从 `recipes` 改名为 `recipe`）。
- [ ] **JEI 兼容**：`compileOnly` 打开 JEI 依赖后写 plugin。
- [ ] **`MurasameItems` 的 BladeState**：若自定义物品进游戏是「空刀」，
      需在 `RegisterCapabilitiesEvent` 里注册 BladeState capability。

---

## 8. 许可

MIT（沿用原 Forge 1.20.1 版本）。SlashBlade 原作为 Ferne，Resharped 由
SyameimaruZheng 维护，均为 MIT。
