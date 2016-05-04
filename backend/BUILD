load("@bazel_tools//tools/build_defs/docker:docker.bzl", "docker_build")

docker_build(
  name = "prometheus_base",
  base = "@docker_debian//:jessie",
  tars = [
    "@prometheus_binary//file",
  ],
)

docker_build(
  name = "prometheus",
  base = ":prometheus_base",
  files = [
    "prometheus.conf.yaml",
  ],
  entrypoint = [
    "/prometheus-0.18.0.linux-amd64/prometheus",
      "-storage.local.memory-chunks=1048576", # Default is 1048576
      "-config.file=/prometheus.conf.yaml",
  ],
)

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
    "//external:netty_all",
    "//external:logback_core",
    "//external:logback_classic",
    "//external:slf4j",
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
  ports = [
    "9090",   # metrics
    "50051",  # grpc
  ],
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

