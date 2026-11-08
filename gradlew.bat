@rem
@rem Murasame NeoForge 骨架工程的 gradlew.bat
@rem gradle/wrapper/gradle-wrapper.jar 是空占位文件，本脚本会自动回退到系统 gradle。
@rem
@if "%DEBUG%"=="" @echo off
setlocal

set APP_HOME=%~dp0
set WRAPPER_JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar

for %%A in ("%WRAPPER_JAR%") do set WRAPPER_SIZE=%%~zA
if "%WRAPPER_SIZE%"=="" set WRAPPER_SIZE=0

if %WRAPPER_SIZE% GTR 0 (
    java -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
    goto end
)

echo ----------------------------------------------------------------
echo [murasame] gradle-wrapper.jar 缺失或为空占位文件，尝试回退到系统 gradle ...
echo ----------------------------------------------------------------
where gradle >nul 2>nul
if errorlevel 1 (
    echo [murasame] 没找到系统 gradle。
    echo   1^) 用官方 MDK 的 gradle-wrapper.jar 覆盖占位文件；
    echo   2^) 安装 Gradle 8.8+ 后执行 gradle build；
    echo   3^) 用 IntelliJ IDEA 自带的 Gradle 导入本工程。
    exit /b 1
)
gradle %*

:end
endlocal
