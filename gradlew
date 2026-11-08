#!/bin/sh
#
# Murasame NeoForge 骨架工程的 gradlew 启动脚本
#
# 说明：本仓库里的 gradle/wrapper/gradle-wrapper.jar 是**空占位文件**（0 字节），
# 由作者手动补全。所以这个脚本会先检查 wrapper jar 是否有效：
#   - 有效  → 走标准 GradleWrapperMain
#   - 无效  → 回退到系统 PATH 里的 gradle（需要 Gradle 8.8+）
#
# 补全方式见 README.md「手动补全清单」。

APP_HOME=$(cd "$(dirname "$0")" && pwd)
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# 0 字节或不存在 → 视为占位文件
if [ -f "$WRAPPER_JAR" ] && [ -s "$WRAPPER_JAR" ]; then
    exec java -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
fi

echo "------------------------------------------------------------------" >&2
echo "[murasame] gradle/wrapper/gradle-wrapper.jar 缺失或为空占位文件。" >&2
echo "[murasame] 正在尝试回退到系统 gradle ..." >&2
echo "------------------------------------------------------------------" >&2

if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
fi

echo "[murasame] 没找到系统 gradle。" >&2
echo "[murasame] 请任选一种方式修复：" >&2
echo "  1) 用官方 MDK 的 gradle/wrapper/gradle-wrapper.jar 覆盖当前占位文件；" >&2
echo "  2) 装 Gradle 8.8+ 后直接执行 gradle build；" >&2
echo "  3) 在 IDE（IntelliJ IDEA）里用自带的 Gradle 导入本工程。" >&2
exit 1
