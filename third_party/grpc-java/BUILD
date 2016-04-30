package(default_visibility = ["//visibility:public"])

licenses(["unencumbered"])  # BSD

filegroup(
    name = "license",
    srcs = ["LICENSE"],
)

cc_binary(
    name = "plugin",
    srcs = [
        "compiler/src/java_plugin/cpp/java_generator.cpp",
        "compiler/src/java_plugin/cpp/java_generator.h",
        "compiler/src/java_plugin/cpp/java_plugin.cpp",
    ],
    deps = ["//external:protobuf_compiler"],
)
