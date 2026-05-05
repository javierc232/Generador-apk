cmake_minimum_required(VERSION 3.10.2)
project("native-lib")

# Busca automáticamente cualquier archivo .cpp que subas
file(GLOB_RECURSIVE NATIVE_SOURCES *.cpp)

add_library(native-lib SHARED ${NATIVE_SOURCES})
find_library(log-lib log)
target_link_libraries(native-lib ${log-lib})
