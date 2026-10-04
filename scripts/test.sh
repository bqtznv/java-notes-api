#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/.."

mkdir -p build
javac -d build src/Server.java src/Tests.java

java -cp build Tests
