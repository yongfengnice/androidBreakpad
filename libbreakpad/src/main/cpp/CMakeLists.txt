cmake_minimum_required(VERSION 3.10.2)

message("CMAKE_SOURCE_DIR====path:${CMAKE_SOURCE_DIR}")
project("libbreakpad" CXX C ASM)

set_property(SOURCE breakpad/common/linux/breakpad_getcontext.S PROPERTY LANGUAGE ASM)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Werror=implicit-function-declaration")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Werror=implicit-function-declaration")

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -ffunction-sections -fdata-sections -fvisibility=hidden -Oz")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -ffunction-sections -fdata-sections -fvisibility=hidden -Oz")

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -s")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -s")

add_library(
        breakpad-client
        SHARED
        native-lib.cpp
        breakpad/client/linux/crash_generation/crash_generation_client.cc
        breakpad/client/linux/dump_writer_common/thread_info.cc
        breakpad/client/linux/dump_writer_common/ucontext_reader.cc
        breakpad/client/linux/handler/exception_handler.cc
        breakpad/client/linux/handler/minidump_descriptor.cc
        breakpad/client/linux/log/log.cc
        breakpad/client/linux/microdump_writer/microdump_writer.cc
        breakpad/client/linux/minidump_writer/linux_dumper.cc
        breakpad/client/linux/minidump_writer/linux_ptrace_dumper.cc
        breakpad/client/linux/minidump_writer/minidump_writer.cc
        breakpad/client/minidump_file_writer.cc
        breakpad/common/convert_UTF.cc
        breakpad/common/md5.cc
        breakpad/common/string_conversion.cc
        breakpad/common/linux/breakpad_getcontext.S
        breakpad/common/linux/elfutils.cc
        breakpad/common/linux/file_id.cc
        breakpad/common/linux/guid_creator.cc
        breakpad/common/linux/linux_libc_support.cc
        breakpad/common/linux/memory_mapped_file.cc
        breakpad/common/linux/safe_readlink.cc
)

target_include_directories(breakpad-client PUBLIC
        breakpad
        breakpad/common/linux
        breakpad/common/android/include)

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}")

find_library(
        log-lib
        log)

target_link_libraries(
        breakpad-client
        ${log-lib})