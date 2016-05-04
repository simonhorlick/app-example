package(default_visibility = ["//visibility:public"])

licenses(["reciprocal"])  # Apache

java_library(
  name = 'simpleclient',
  srcs = glob([
    'upstream/simpleclient/src/main/java/**/*.java'
  ]),
)

java_library(
  name = 'simpleclient_common',
  srcs = glob([
    'upstream/simpleclient_common/src/main/java/**/*.java'
  ]),
  deps = [
    ":simpleclient",
  ]
)

