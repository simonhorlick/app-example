load("@bazel_tools//tools/build_defs/docker:docker.bzl", "docker_build")

# Extract .xz files
genrule(
    name = "jessie_tar",
    srcs = ["docker-brew-debian-04fb8b48a6fcf3d1831a3fc684adb648c8b4d876/jessie/rootfs.tar.xz"],
    outs = ["jessie_tar.tar"],
    cmd = "cat $< | xzcat >$@",
)

docker_build(
    name = "jessie",
    tars = [":jessie_tar"],
    visibility = ["//visibility:public"],
)
