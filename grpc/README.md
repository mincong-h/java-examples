# gRPC

<https://www.grpc.io/>

gRPC is a modern open source high performance Remote Procedure Call (RPC) framework that can run in any environment. It can efficiently connect services in and across data centers with pluggable support for load balancing, tracing, health checking and authentication. It is also applicable in last mile of distributed computing to connect devices, mobile applications and browsers to backend services.

## Dependencies

### grpc-protobuf

```xml
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-protobuf</artifactId>
    <version>${grpc.version}</version>
</dependency>
```

API for gRPC over Protocol Buffers, including tools for serializing
and de-serializing protobuf messages.

### grpc-netty-shaded

```xml
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-netty-shaded</artifactId>
    <version>${grpc.version}</version>
</dependency>
```

Netty is an asynchronous event-driven network application framework
for rapid development of maintainable high performance protocol
servers & clients. Netty is a NIO client server framework which
enables quick and easy development of network applications such as
protocol servers and clients. It greatly simplifies and streamlines
network programming such as TCP and UDP socket server. <https://netty.io/>

For gRPC, its transport layer does the heavy lifting of putting and taking bytes off the wire. The interfaces to it are abstract just enough to allow plugging in of different implementations. Note the transport layer API is considered internal to gRPC and has weaker API guarantees than the core API under package io.grpc.

gRPC comes with three Transport implementations:

1. The Netty-based transport is the main transport implementation based on Netty. It is for both the client and the server.
2. The OkHttp-based transport is a lightweight transport based on OkHttp. It is mainly for use on Android and is for client only.
3. The in-process transport is for when a server is in the same process as the client. It is useful for testing, while also being safe for production use.

### grpc-stub

```xml
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-stub</artifactId>
    <version>${grpc.version}</version>
</dependency>
```

The Stub layer is what is exposed to most developers and provides type-safe
bindings to whatever datamodel/IDL/interface you are adapting. gRPC comes with
a [plugin](https://github.com/google/grpc-java/blob/master/compiler) to the
protocol-buffers compiler that generates Stub interfaces out of `.proto` files,
but bindings to other datamodel/IDL are easy and encouraged.

### annotations-api

```xml
<!-- necessary for Java 9+ -->
<dependency>
    <groupId>org.apache.tomcat</groupId>
    <artifactId>annotations-api</artifactId>
    <version>${tomcat.annotation.version}</version>
    <scope>provided</scope>
</dependency>
```

Used for Java annotations.