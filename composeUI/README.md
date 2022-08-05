# Compose

* 官网地址： https://developer.android.google.cn/jetpack/compose/mental-model?hl=zh-cn
* 学习参考： https://www.jianshu.com/p/a86e86473421

## 状态管理

```kotlin
var isExpanded by remember { mutableStateOf(false) }
```
* remember 必须写在组合中，即 @Composable 注解的函数中
* 只有使用 MutableState 的值发生变更，才会导致重新渲染