# The Android SDK requires a path to be configured. Ensure this is correct for
# your system.
android_sdk_repository(
  name = "androidsdk",
  path = "/Users/simon/Library/Android/sdk",
  api_level = 23,
  build_tools_version = "23.0.3",
)

maven_jar(
  name = "guava",
  artifact = "com.google.guava:guava:19.0",
  sha1 = "6ce200f6b23222af3d8abb6b6459e6c44f4bb0e9",
)

maven_jar(
  name = "jsr305",
  artifact = "com.google.code.findbugs:jsr305:3.0.1",
)

git_repository(
  name = "protobuf",
  remote = "https://github.com/google/protobuf.git",
  tag = "v3.0.0-beta-2",
)

git_repository(
  name = "grpc",
  remote = "https://github.com/grpc/grpc.git",
  tag = "release-0_13_1",
)

# Protobuf compiler binary
bind(
  name = "protoc",
  actual = "@protobuf//:protoc"
)

# Library needed to build protobuf codegen plugin.
bind(
  name = "protobuf_compiler",
  actual = "@protobuf//:protoc_lib",
)

maven_jar(
  name = "maven_protobuf_java",
  artifact = "com.google.protobuf:protobuf-java:3.0.0-beta-2",
)

maven_jar(
  name = "grpc_all",
  artifact = "io.grpc:grpc-all:0.13.2",
)

maven_jar(
  name = "netty",
  artifact = "io.netty:netty-codec-http2:4.1.0.CR7"
)

maven_jar(
  name = "netty_epoll",
  artifact = "io.netty:netty-transport-native-epoll:4.1.0.CR7"
)

maven_jar(
  name = "netty_tcnative_maven",
  artifact = "io.netty:netty-tcnative:1.1.33.Fork15"
)

bind(
  name = "netty_tcnative",
  actual = "@netty_tcnative_maven//jar"
)

bind(
  name = "jsr305",
  actual = "//third_party/jsr305"
)

