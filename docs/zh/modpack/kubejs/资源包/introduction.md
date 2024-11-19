---
progress: 10
description: 
state: preliminary
---
# 基础知识
本文档作用于讲清`资源包`能使用的功能，从零开始讲述`各个文件的功能`，不会谈及任何绘画经验
::: v-warning 注意
本文采用`游戏版本1.20.1`作为教学版本，不同的版本略有出入，请自行辨别
:::

## 项目所使用的资源 {#FileStructure}
<font color=black>资源包</font>:<br>
[示例材质01](https://caiyun.139.com/m/i?2i3pdiZ2Cmgsi)(本编所使用文件参考)<br>
[1.20.1原版材质](https://caiyun.139.com/m/i?2i3pdiZ2EKN33)(1.20.1的原版材质，可作为修改时的参考)<br>
<font color=black>编码软件</font>:
[Visual Studio Code](https://code.visualstudio.com/Download)(较为专业且全面的编码软件,但有一定上手门槛,且对低配电脑不太友好)<br>
[Notepad--](https://gitee.com/cxasm/notepad--/releases)（Notepad++的国人复刻版本,对仅有修改资源包需求的用户`最推荐使用`）<br>
<font color=black>绘画软件</font>：<br>
[Photoshop](https://www.adobe.com/creativecloud/plans.html?plan=individual&filter=all&promoid=PYPVPZQK&mv=other)（强大且好用，但上手门槛较高）<br>
[aseprite](https://store.steampowered.com/app/431730/Aseprite/)（简单高效的像素绘图工具）<br>
!!是否有免费的方法？自行搜索哦!!

## 文件结构
首先创建一个文件夹，我这里以`示例材质`作为`资源包名称`

![Logo](/zyb/1.png =200x200)

可被识别的资源包应有三个必要文件

>[!INFO]分别为：
>assets&nbsp;&nbsp;&nbsp;<font color=green>**存放修改内容的区域**</font><br>
>pack.mcmeta&nbsp;&nbsp;&nbsp;<font color=green>**配置文件**</font><br>
>pack.png&nbsp;&nbsp;&nbsp;<font color=green>**封面图**</font><br>

### pack.png <font color=green>封面图</font>{#pack.png}
这是资源包在选择的界面的贴图,文件尺寸必须为`1:1`,即为一个正方形<br>
![Logo](/zyb/3.png =200x200)<br>
这是张像素为`512：512`的示例封面<br>
### pack.mcmeta<font color=green>配置文件</font>{#pack.mcmeta}
这是资源包的配置文件，通常会有以下内容：<br>
```js twoslash
{
  //资源包基础元信息
  "pack": {
    //最佳的支持版本，此处为22，即为1.20.4版本
    "pack_format": 22,
    //资源包支持的格式版本区间，此处为16到22，即为1.20.2到1.20.4版本
    "supported_formats": [16,22],
    //在资源包列表里要显示的资源包描述，可使用格式化代码,最多显示为2行
    "description":"\u00A76第一行文本示例（通常为补充说明）\n\u00A7e第一行文本示例（通常为作者信息）"
  }
}
```
- 在游戏中显示为：
![Logo](/zyb/4.png =600x200)
:::: details 补充
::: details [常用资源包版本](https://zh.minecraft.wiki/w/%E8%B5%84%E6%BA%90%E5%8C%85#%E4%B8%96%E7%95%8C%E6%8C%87%E5%AE%9A%E8%B5%84%E6%BA%90%E5%8C%85)<br>
| 编号 | 版本                                   |
|----|--------------------------------------|
| 1  | 1.6.1快照13w24a到1.8.9                  |
| 2  | 1.9快照15w31a到1.10.2                   |
| 3  | 1.11快照16w32a到1.12.2快照17w47b          |
| 6  | 1.16.2发布候选1.16.2-rc1到1.16.5          |
| 8  | 1.18快照21w39a到1.18.2                  |
| 9  | 1.19快照22w11a到1.19.2                  |
| 15 | 1.20快照23w17a到1.20.1                  |
| 16 | 	1.20.2快照23w31a                  |
| 22 | 1.20.3预发布版1.20.3-pre1到1.20.4快照23w51b |
| 32 | 1.20.5预发布版1.20.5-pre4到1.20.6         |
| 34 | 1.21快照24w21a到1.21.1                  |
| 42 | 1.21.2预发布版1.21.2-pre3到1.21.3         |


:::
::: details [格式化代码](https://zh.minecraft.wiki/w/%E6%A0%BC%E5%BC%8F%E5%8C%96%E4%BB%A3%E7%A0%81)<br>
>[!TIP]简单讲解：
>- 颜色字符：`§`,也可使用`\u00A7`代替`§`，在后面加上`字符`也是同样的效果
>- 换行字符：`\n`
>- 粗体字符：`§l`
>- 随机字符：`§k`
>- 下划字符：`§n`
>- 斜体字符：`§o`
>- 重置字符：`§r`
:::
::::
### assets<font color=green>资源文件夹</font>{#assets}
#### 介绍：
这是存放资源包的修改内容的文件夹，里面用于存放多个不同的命名空间目录<br>
#### 原理：
将本文件夹里的文件按照路径进行替换
#### 内容：
::: details 具体内容：
##### 纹理：
`作用`：定义许多纹理，用于修改游戏内各种方块、物品、实体、GUI的显示<br>
`存放位置`：assets/<命名空间>/textures<br>
##### 模型：
`作用`：修改方块和物品的形状和对应的纹理绑定<br>
`存放位置`：assets/<命名空间>/models<br>
##### 字体：
`作用`：修改已有的字体和添加自定义字体，修改各处文本的渲染<br>
`存放位置`：assets/<命名空间>/font<br>
##### 着色器：
`作用`：改变游戏渲染方式的一种方式，使用OpenGL着色器语言（GLSL）编写<br>
`存放位置`：assets/minecraft/shaders<br>
##### 声音：
`作用`：修改游戏内的声音和对应的声音事件定义<br>
`存放位置`：assets/<命名空间>/sounds<br>
##### 语言：
`作用`：可以添加或修改语言<br>
`存放位置`：assets/<命名空间>/lang<br>
##### 文本：
`作用`：控制了游戏的标题屏幕内的闪烁标语，终末之诗文本，制作人员名单显示完毕后出现的引言和Minecraft的制作人员名单<br>
`存放位置`：assets/minecraft/texts<br>
##### GPU警告列表：
`作用`：检查当前使用的渲染器情况<br>
`存放位置`：assets/minecraft/gpu_warnlist.json<br>
##### 地区合规性警告：
`作用`：根据游戏运行时间定时弹出弹窗<br>
`存放位置`：assets/minecraft/regional_compliancies.json<br>
:::
