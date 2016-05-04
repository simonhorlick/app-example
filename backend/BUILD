load("@bazel_tools//tools/build_defs/docker:docker.bzl", "docker_build")

docker_build(
  name = "jre",
  base = "@docker_debian//:jessie",
  debs = [
    "@glib2//file", # for jvm network
    "@ffi//file", # also needed for jvm
    "@openjdk_7_jre_headless//file",
  ],
)

java_library(
  name = "server",
  srcs = glob(["java/**/*.java"]),
  deps = [ 
    "//proto:helloworld_java",
    "//external:guava",
    "//external:grpc-java",
    "//external:grpc-netty",
    "//external:prometheus_client_java",
    "//external:prometheus_exporter_java",
    "@logback_core//jar",
    "@logback_classic//jar",
    "@slf4j_api//jar",
  ],
)

#java_library(
#  name = "tests",
#  srcs = glob(["javatests/**/*.java"]),
#  deps = [
#    ":server",
#    "@junit4//jar",
#    "@hamcrest_core//jar",
#    "@mockito//jar",
#  ],
#)
#
#java_test(
#  name = "TestTransforms",
#  size = "small",
#  test_class = "analysis.TestTransforms",
#  runtime_deps = [":tests"],
#)

java_binary(
  name = "server_runner",
  main_class = "io.grpc.examples.helloworld.HelloWorldServer",
  jvm_flags = [
    "-Xms32m",
    "-Xmx128m",
    "-Dlogback.configurationFile=file:logback.xml",
  ],
  runtime_deps = [
    ":server",
  ]
)

#filegroup(
#  name = "content",
#  srcs = glob([
#    "www/**",
#  ]),
#)

docker_build(
  name = "backend",
  base = ":jre",
  ports = ["50051"],
  data_path = "/",
  files = [
    ":server_runner_deploy.jar",
  ],
  # FIXME(simon): Is there a better way of doing this?
  symlinks = {
    "/usr/bin/java": "/usr/lib/jvm/java-7-openjdk-amd64/bin/java",
  },
  entrypoint = ["java", "-jar", "backend/server_runner_deploy.jar"]
)

