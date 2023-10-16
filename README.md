# cstpb
Consistency(cst) Protocol Buffer

# 1. 介绍
[Protocol Buffers](https://protobuf.dev)是谷歌优秀的序列化框架，无论是跨语言还是序列化速度来说，都是一流的存在。
笔者在工作中也常常使用，但是有的时候会遇到一些一致性问题，场景如下：
在多人合作的项目中，经常会将部分协议分批次MR进来，导致了虽然文件不同，布局不同，但是内容其实还是相同的，典型的场景如下：

项目A:
```
# a.proto
message msg_1 {
 ...
}
message msg_2 {
...
}
# b.proto
message msg_3 {
...
}
```
项目B:
```
# c.proto
message msg_1 {
 ...
}
message msg_2 {
...
}
message msg_3 {
...
}
```
虽然他们文件的数量不一致，布局不一致，但是归根究底内容还是相同的。
所以从传统的MD5+SHA1是无法对两者进行比较的，所以笔者开发了这套基于内容的一致性比较工具。
