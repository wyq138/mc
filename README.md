# Minecraft Clone - Java + LWJGL

一比一还原《我的世界》，使用Java + LWJGL引擎开发，与原版相同的技术栈。

## 项目特性

### 核心特性
- ✅ Java + LWJGL 3.3.3（与原版相同）
- ✅ OpenGL 3.3渲染
- ✅ VBO/VAO优化渲染
- ✅ 区块（Chunk）系统
- ✅ 程序化地形生成
- ✅ 第一人称视角
- ✅ 物理和碰撞检测
- ✅ 方块放置和破坏

### 方块类型
- 空气、草地、泥土、石头
- 木头、树叶、沙子、水
- 圆石、木板、砖块

### 交付平台
1. **WebGL版本** - 通过GWT编译
2. **Windows EXE** - Maven打包
3. **Android APK** - 通过RoboVM或LibGDX

## 项目结构

```
src/main/java/com/minecraft/
├── Main.java                    # 主入口
├── engine/
│   ├── Window.java               # GLFW窗口
│   ├── Renderer.java             # 渲染器
│   └── Camera.java              # 摄像机
├── world/
│   └── World.java              # 世界管理
├── chunk/
│   ├── Chunk.java              # 区块
│   └── ChunkManager.java       # 区块管理器
├── block/
│   ├── Block.java             # 方块
│   └── BlockType.java        # 方块类型
├── player/
│   └── Player.java           # 玩家控制器
├── input/
│   └── Input.java           # 输入管理
├── shader/
│   └── Shader.java          # 着色器
└── terrain/
    └── TerrainGenerator.java  # 地形生成器
```

## 编译和运行

### 环境要求
- JDK 17+
- Maven 3.6+
- LWJGL 3.3.3

### 编译项目
```bash
mvn clean package
```

### 运行游戏
```bash
java -jar target/minecraft-clone-1.0.0.jar
```

### 生成EXE
```bash
mvn package
# target/minecraft-clone-1.0.0.jar 可直接运行
# 使用Launch4j或jpackage打包为EXE
```

### 生成APK
使用LibGDX或RoboVM将Java项目打包为APK

### 生成Web版本
使用GWT将Java编译为JavaScript

## 操作方式

- **WASD** - 移动
- **空格** - 跳跃
- **鼠标** - 视角控制
- **左键** - 破坏方块
- **右键** - 放置方块
- **1-9** - 切换物品栏

## 技术细节

### 渲染管线
1. 顶点着色器处理变换
2. 片段着色器处理光照
3. 使用纹理Atlas优化
4. 视锥体剔除

### 世界生成
- 简单噪声地形
- 动态区块加载
- 16x16x256区块大小
- 8区块渲染距离

### 物理系统
- AABB碰撞检测
- 重力模拟
- 地面检测
- 速度阻尼

## 后续开发

- [ ] 物品栏UI
- [ ] 方块交互完善
- [ ] 多人联机
- [ ] 世界保存（NBT格式）
- [ ] 更多方块类型
- [ ] 生物AI
- [ ] 红石系统
- [ ] 合成系统

## 许可证

本项目仅供学习参考使用。
