模型 / 贴图放这里（二进制资源，骨架里不提供）
================================================================

需要的文件（路径相对于本目录，命名空间 murasame）：

  models/murasame/murasamemaru.obj
  models/murasame/murasamemaru.png
  models/murasame/murasamemaru_awakened.obj
  models/murasame/murasamemaru_awakened.png

来源：
  1) 从原 Forge 1.20.1 丛雨丸的 jar 里解出
     assets/murasame/models/murasame/*.obj / *.png，原样拷进来即可
     （OBJ + PNG 格式在 1.21.1 没有变化，不需要转换）；
  2) 或者从 SlashBlade: Refabricated Addons 的 jar 里取
     assets/slashblade_fabric_addons/slashblade_addon/model/murasama/*。

注意：
  - 本目录下必须保留这个 README，否则 git 不会提交空目录；
  - 如果暂时没有模型，进游戏刀会显示成紫黑方块，属于正常现象，
    不影响编译和 SE 逻辑调试。
