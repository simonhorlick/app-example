# The final binary rule, which builds the APK and sets the application manifest,
# as well as any other resources needed by the application.
# The package for the R class for resources is normally inferred from the
# directory containing the BUILD file, but this BUILD file is not under a java
# directory, so we specify it manually.
android_binary(
  name = "app",
  custom_package = "com.google.bazel.example.android",
  manifest = "src/main/java/com/google/bazel/example/android/AndroidManifest.xml",
  resource_files = glob([
    "src/main/java/com/google/bazel/example/android/res/**"
  ]),
  deps = [
    ":activities",
    "@slf4j_android_maven//jar",
    "@okio_maven//jar",
  ],
)

# A library which compiles some Java sources and associated resources.
# Because it has resources, it requires a manifest.
android_library(
  name = "activities",
  srcs = glob([
    "src/main/java/com/google/bazel/example/android/activities/*.java"
  ]),
  custom_package = "com.google.bazel.example.android.activities",
  manifest = "src/main/java/com/google/bazel/example/android/activities/AndroidManifest.xml",
  resource_files = glob([
    "src/main/java/com/google/bazel/example/android/activities/res/**"
  ]),
  deps = [
    "//proto:helloworld_android",
    #"@grpc_all//jar",
    "@okhttp_maven//jar",
    "@grpc_okhttp_maven//jar",
    "@grpc_protobuf_maven//jar",
    "@grpc_stub_maven//jar",
    "@grpc_core_maven//jar",
    #"@maven_protobuf_java//jar",
    "@slf4j_api_maven//jar",
    #"//external:slf4j",
  ],
)

