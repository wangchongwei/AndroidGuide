## 计算机网络


参考地址：https://www.jianshu.com/p/45d27f3e1196




## 1、计算机网络结构

### 1.1 简介

* 定义
  计算机网络各层 + 其协议的集合

* 作用
  定义该计算机网络的所能完成的功能

### 1.2 结构

计算机网络体系结构分为三种：
* OSI体系结构
* TCP/IP体系结构
* 五层体系结构

> * OSI体系结构：概念清楚 & 理念完整，但复杂 & 不实用 
> * TCP / IP体系结构：含了一系列构成互联网基础的网络协议，是Internet的核心协议 & 被广泛应用于局域网 和 广域网 
> * 五层体系结构：融合了OSI 与 TCP / IP的体系结构，目的是为了学习 & 讲解计算机原理 


<table>
  <tr>
    <th>OSI体系结构(7层)</th>
    <th>TCP/IP协议体系(4层)</th>
    <th>五层体系结构(5层)</th>
  </tr>
  <tr>
    <td>7.应用层</td>
    <td rowspan="3">4.应用层<br/>(HTTP)</td>
    <td rowspan="3">5.应用层</td>
  </tr>
  <tr>
    <td>6.表示层</td>
  </tr>
  <tr>
    <td>5.会话层</td>
  </tr>
  <tr>
    <td>4.传输层</td>
    <td>3.运输层<br/>(TCP、UDP)</td>
    <td>4.运输层</td>
  </tr>
  <tr>
    <td>3.网络层</td>
    <td>2. 网际层<br/>(IP)</td>
    <td>3.网络层</td>
  </tr>
  <tr>
    <td>2.链路层</td>
    <td rowspan="2">1.网络接口层</td>
    <td>2.链路层</td>
  </tr>
  <tr>
    <td>1.物理层</td>
    <td>1.物理层</td>
  </tr>
</table>



> 低三层为通信子网，负责数据传输 
> 高三层为资源子网，相当于计算机系统，完成数据处理； 
> 传输层承上启下 




#### TCP/IP体系结构详细介绍

由于 TCP / IP体系结构较为广泛，故主要讲解

<table>
  <tr>
    <th>层级</th>
    <th>作用</th>
    <th>传输单位</th>
    <th>功能</th>
    <th>具体协议</th>
  </tr>
  <tr>
    <td>1、网络接口层</td>
    <td>负责与链路(传输媒介)的数据运输工作</td>
    <td>帧</td>
    <td>* 组帧、差错控制、流量控制和运输管理</td>
    <td>* EIA-232C、CCITT的X.21<br/>
      * SDLC、HDLC、PPP、STP、帧中继
    </td>
  </tr>
  
  <tr>
    <td>2、网际层</td>
    <td>为不同主机提供通信服务：网络层的分组数据从源端传到目的端</td>
    <td>数据报</td>
    <td>* 封装数据成分组/包、路由选择
      <br/>* 流量控制、拥塞控制、差错控制 & 网际互连
    </td>
    <td>* IP协议、ARP协议、RARP协议、ICMP协议、IGMP协议、IPX、OSPF</td>
  </tr>
  
  <tr>
    <td>3、运输层</td>
    <td>为不同主机进程间提供通信服务</td>
    <td>报文段TCP、用户数据报UDP</td>
    <td>为端到端的连接提供可靠的传输服务、流量控制、差错控制、数据传输管理服务</td>
    <td>TCP协议、UDP协议</td>
  </tr>
  
  <tr>
    <td>4、应用层</td>
    <td>定义应用进程间通信  & 交互的规则</td>
    <td>/</td>
    <td>/</td>
    <td>
      HTTP协议
      <br/>
      DNS协议
      <br/>
      SMTP协议
      <br/>
      POP协议
      <br/>
      FTP协议
      <br/>
      SMB协议
      <br/>
      Telnet协议
      <br/>
      SSH协议
    </td>
  </tr>
  
</table>


## 2、TCP协议
Transmission Control Protocol，即 传输控制协议
> * 属于 传输层通信协议
> * 基于TCP的应用层协议有HTTP、SMTP、FTP、Telnet 和 POP3

关于TCP具体信息，可以查看 [TCP协议](tcp.md)


## 3、UDP协议

### 3.1 定义
User Datagram Protocol，即 用户数据报协议

> * 属于 传输层通信协议
> * 基于UDP的应用层协议有 TFTP、SNMP 与 DNS

### 3.2 特定
无连接的、不可靠的、面向报文、无拥塞控制，具体介绍如下：

|   特定     |        描述         |
| ----- | -----|
| 无连接 | 使用UDP传输数据前，不需要建立UDP连接 |
| 不可靠 | UDP数据包传输后，不管数据接收包是否接收到 |
| 面向报文 | 数据以数据报文的形式传输 |
| 无拥塞控制 | 由于是不可靠传输，即不考虑是否接收到数据，所以也就不需要拥塞控制|

### 3.3 优缺点

* 优点：速度快
* 缺点：数据容易丢失

### 3.4 应用场景
要求通信速度高
> * 域名转换：DNS协议
> * 文件传输：FTP协议
> * 网络管理：SNMP协议
> * 远程文件服务器：NFS协议



### 3.5 TCP & UDP 的区别

<table>
  <tr>
    <th rowspan="2">类型</th>
    <th colspan="3">特点</th>
    <th colspan="2">性能</th>
    <th rowspan="2">应用场景</th>
    <th rowspan="2">首部字节</th>
  </tr>
  <tr>
    <th>是否面向连接</th>
    <th>传输可靠性</th>
    <th>传输形式</th>
    <th>传输效率</th>
    <th>所需资源</th>
  </tr>
  <tr>
    <td>TCP</td>
    <td>面向连接</td>
    <td>可靠</td>
    <td>字节流</td>
    <td>慢</td>
    <td>多</td>
    <td>要求通信数据可靠</td>
    <td>20-60</td>
  </tr>
  <tr>
    <td>UDP</td>
    <td>无连接</td>
    <td>不可靠</td>
    <td>数据报文段</td>
    <td>快</td>
    <td>少</td>
    <td>要求通信速度快</td>
    <td>8字节，由4个字段组成</td>
  </tr>
</table>


## 4、HTTP协议

## 5、Socket


## 6、Other

> 打开一个网页，整个过程会使用哪些协议

