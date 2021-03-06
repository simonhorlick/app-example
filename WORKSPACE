# The Android SDK requires a path to be configured. Ensure this is correct for
# your system.
android_sdk_repository(
  name = "androidsdk",
  path = "/Users/simon/Library/Android/sdk",
  api_level = 23,
  build_tools_version = "23.0.3",
)

git_repository(
  name = "protobuf",
  remote = "https://github.com/google/protobuf.git",
  tag = "v3.0.0-beta-2",
)

maven_jar(
  name = "guava_maven",
  artifact = "com.google.guava:guava:19.0",
  sha1 = "6ce200f6b23222af3d8abb6b6459e6c44f4bb0e9",
)

maven_jar(
  name = "maven_protobuf_java",
  artifact = "com.google.protobuf:protobuf-java:3.0.0-beta-2",
)

maven_jar(
  name = "javax_annotation_maven",
  artifact = 'javax.annotation:javax.annotation-api:1.2',
)

maven_jar(
  name = "okhttp_maven",
  artifact = 'com.squareup.okhttp:okhttp:2.5.0',
)

maven_jar(
  name = "okio_maven",
  artifact = 'com.squareup.okio:okio:1.6.0',
)

maven_jar(
  name = "jsr305_maven",
  artifact = 'com.google.code.findbugs:jsr305:3.0.0',
)

maven_jar(
  name = "protobuf_javanano_maven",
  artifact = 'com.google.protobuf.nano:protobuf-javanano:3.0.0-alpha-5',
)

maven_jar(
  name = "grpc_all",
  artifact = "io.grpc:grpc-all:0.13.2",
)

maven_jar(
  name = "grpc_core_maven",
  artifact = "io.grpc:grpc-core:0.13.2",
)

maven_jar(
  name = "grpc_okhttp_maven",
  artifact = "io.grpc:grpc-okhttp:0.13.2",
)

maven_jar(
  name = "grpc_protobuf_maven",
  artifact = "io.grpc:grpc-protobuf:0.13.2",
)

maven_jar(
  name = "grpc_protobuf_nano_maven",
  artifact = "io.grpc:grpc-protobuf-nano:0.13.2",
)

maven_jar(
  name = "grpc_stub_maven",
  artifact = "io.grpc:grpc-stub:0.13.2",
)

maven_jar(
  name = "netty_maven",
  artifact = "io.netty:netty-all:4.1.0.CR7"
)

maven_jar(
  name = "logback_core_maven",
  artifact = "ch.qos.logback:logback-core:1.1.7",
  sha1 = "7873092d39ef741575ca91378a6a21c388363ac8",
)

maven_jar(
  name = "logback_classic_maven",
  artifact = "ch.qos.logback:logback-classic:1.1.7",
  sha1 = "9865cf6994f9ff13fce0bf93f2054ef6c65bb462",
)

maven_jar(
  name = "slf4j_android_maven",
  artifact = "org.slf4j:slf4j-android:1.7.21",
)

maven_jar(
  name = "slf4j_api_maven",
  artifact = "org.slf4j:slf4j-api:1.7.13",
  sha1 = "7fcf30c25b8f4a9379b9dad0d3f487b25272c026",
)

new_http_archive(
  name = "docker_debian",
  url = "https://codeload.github.com/tianon/docker-brew-debian/zip/04fb8b48a6fcf3d1831a3fc684adb648c8b4d876",
  build_file = "debian.BUILD",
  type = "zip",
  sha256 = "78bd26facf2b2e3a9cf1f0ad9b4da0b6ec906be2440ee30edb6e421442265088",
)

http_file(
  name = "openjdk_7_jre_headless",
  url = "http://security.debian.org/debian-security/pool/updates/main/o/openjdk-7/openjdk-7-jre-headless_7u101-2.6.6-1~deb8u1_amd64.deb",
  sha256 = "bff2978069efa9b18b10e7e555705d08d3efb956e690dc4f1042c2c815d1b370",
)

# Required by jvm runtime.
http_file(
  name = "glib2",
  url = "http://ftp.debian.org/debian/pool/main/g/glib2.0/libglib2.0-0_2.42.1-1+b1_amd64.deb",
  sha256 = "a4b30c84c0c050f23a986fbc576daa04b246ab816ec0fcb0b471d19aa2689a97",
)

# Required by jvm runtime.
http_file(
  name = "ffi",
  url = "http://ftp.debian.org/debian/pool/main/libf/libffi/libffi6_3.1-2+b2_amd64.deb",
  sha256 = "481af9931f3352a51a579511a20ff3d57068681d6c760513590200a71fe49a50",
)

http_file(
  name = "prometheus_binary",
  url = "https://github.com/prometheus/prometheus/releases/download/0.18.0/prometheus-0.18.0.linux-amd64.tar.gz",
  sha256 = "dfcdf8f77deb3ced5976a27795c80806aad596037347304b790ceae09dfb1bb9",
)

bind(
  name = "prometheus_client_java",
  actual = "//third_party/prometheus/client_java:simpleclient",
)

bind(
  name = "prometheus_exporter_java",
  actual = "//third_party/prometheus/client_java:simpleclient_common",
)

bind(
  name = "logback_core",
  actual = "@logback_core_maven//jar",
)

bind(
  name = "logback_classic",
  actual = "@logback_classic_maven//jar",
)

bind(
  name = "guava",
  actual = "@guava_maven//jar",
)

bind(
  name = "slf4j",
  actual = "@slf4j_api_maven//jar",
)

bind(
  name = "slf4j_android",
  actual = "@slf4j_android_maven//jar",
)

bind(
  name = "jsr305",
  actual = "//third_party/jsr305"
)

bind(
  name = "netty_all",
  actual = "@netty_maven//jar",
)

bind(
  name = "jsr305",
  actual = "//third_party/jsr305"
)

bind(
  name = "nanopb",
  actual = "//third_party/nanopb",
)

bind(
  name = "libcrypto",
  actual = "//third_party/boringssl:crypto",
)

bind(
  name = "libssl",
  actual = "//third_party/boringssl:ssl",
)

bind(
  name = "libcrypto_objc",
  actual = "//third_party/boringssl:crypto_objc",
)

bind(
  name = "libssl_objc",
  actual = "//third_party/boringssl:ssl_objc",
)

bind(
  name = "protobuf_java_lib",
  actual = "@protobuf//:protobuf_java",
)

bind(
  name = "protoc",
  actual = "@protobuf//:protoc"
)

bind(
  name = "protobuf_compiler",
  actual = "@protobuf//:protoc_lib",
)

bind(
  name = "protobuf_objc",
  actual = "@protobuf//:protobuf_objc"
)

bind(
  name = "grpc_protoc_plugin_objc",
  actual = "//third_party/grpc/upstream:grpc_objective_c_plugin"
)

bind(
  name = "proto_objc_rpc",
  actual = "//third_party/grpc/upstream:proto_objc_rpc",
)

bind(
  name = "grpc-java",
  actual = "//third_party/grpc-java"
)

bind(
  name = "grpc_java_plugin",
  actual = "//third_party/grpc-java:grpc_java_plugin"
)

bind(
  name = "grpc-netty",
  actual = "//third_party/grpc-java:grpc-netty"
)

