# cstpb
Consistency(cst) Protocol Buffer

## 1. 介绍
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
用于比较2个proto文件，或者2个目录下proto内容的一致性
## 2. 使用方法
前提，***protoc*** 在***PATH***内，***protoc***下载地址：https://github.com/protocolbuffers/protobuf/releases/tag/v24.4

打包应用程序
```
gradlew distZip
在目录 build/distributions 下生成文件 cstpb.zip

```
将***cstpb.zip***解压到某一个目录
```
# bin/cstpb or bin\cstpb.bat
usage: cstpb
 -d,--diff          diff 2 proto file or 2 proto directory
 -h,--help          display help message.
 -l,--left <arg>    left proto file or proto directory
 -r,--right <arg>   right proto file or proto directory

example:
cstpb -d --left=src/test/resources/addressbook.proto --right=src/test/resources/addressbook-add-enum.proto

output:
enum tutorial.Person.PersonGender append at src/test/resources/addressbook-add-enum.proto
```
## 3. feature
### 3.1 message
#### 3.1.1 添加 message
```
message Person {
    ...
}
===>
message Person {

}
message NewMessage {
    // this message is added
}
```
#### 3.1.2 删除 message
#### 3.1.3 修改 message field
##### 3.1.3.1 修改 label
```
message Person {
    required int32 age = 1;
}
===>
message Person {
    optional int32 age = 1;
    // label required changed to optional
}
```
##### 3.1.3.2 修改 type
```
message Person {
    required int32 age = 1;
}
===>
message Person {
    required string age = 1;
    // type int32 changed to string

```
##### 3.1.3.3 修改 number
```
message Person {
    required int32 age = 1;
}
===>
message Person {
    required int32 age = 2;
}

```
##### 3.1.3.4 修改 default

```
message Person {
    required int32 age = 1;
}
===>
message Person {
    required int32 age = 1[default=10];
    // add default value
}

```
### 3.2 enum
#### 3.2.1 添加 enum
```
enum OldEnum {
    ...
}
=>
enum OldEnum {
    ...
}
enum NewEnum {
    ...
}
```

#### 3.2.2 删除 enum
#### 3.2.3 添加 enum value
```
enum Enum {
    A = 1;
    ...
}
=>
enum Enum {
    A = 1;
    B = 2;
    ...
}
```

#### 3.2.4 删除 enum value
#### 3.2.5 修改 enum value
```
enum Enum {
    A = 1;
    ...
}
=>
enum Enum {
    A = 2;
    ...
}
```
### 3.3 service
#### 3.3.1 添加 service
```
message GetSomethingRequest {

}

message GetSomethingResponse {

}

service FooService {
  rpc GetSomething(GetSomethingRequest) returns (GetSomethingResponse);
}
===>

message GetSomethingRequest {

}

message GetSomethingResponse {

}

service FooService {
  rpc GetSomething(GetSomethingRequest) returns (GetSomethingResponse);
}
servce BarService {
    ......
}

```
#### 3.3.2 删除 service
#### 3.3.3 添加 service method
```
message GetSomethingRequest {

}

message GetSomethingResponse {

}

service FooService {
  rpc GetSomething(GetSomethingRequest) returns (GetSomethingResponse);
}
===>

message GetSomethingRequest {

}

message GetSomethingResponse {

}

service FooService {
  rpc GetSomething(GetSomethingRequest) returns (GetSomethingResponse);
  rpc GetSomethingAdd(GetSomethingRequest) returns (GetSomethingResponse);
}

```
#### 3.3.4 删除 service method
#### 3.3.5 修改 service method
```
message GetSomethingRequest {

}

message GetSomethingResponse {

}

service FooService {
  rpc GetSomething(GetSomethingRequest) returns (GetSomethingResponse);
}
===>

message GetSomethingRequest {

}

message GetSomethingResponse {

}
message GetSomethingRequest2 {

} 

service FooService {
  rpc GetSomething(GetSomethingRequest2) returns (GetSomethingResponse);
  // change method input
}

```
## todo
