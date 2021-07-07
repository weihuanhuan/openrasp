#!/bin/bash
##################################
#                                #
# 自动编译，并生成OpenRASP安装包 #   
#                                #
##################################

set -e

cd "$(dirname "$0")"

BASE_DIR="$(pwd)"
echo "base dir: $BASE_DIR"

RASP_VERSION="v1.3.5"
PLUGIN_ROOT="$BASE_DIR/plugins/official"
OUTPUT_ROOT="$BASE_DIR/rasp-$RASP_VERSION"
BASENAME=$(basename "$OUTPUT_ROOT")
rm -rf "$BASENAME"

rm -rf "$OUTPUT_ROOT" rasp-java-$RASP_VERSION.{zip,tar.gz}
mkdir -p "$OUTPUT_ROOT"/rasp/{plugins,conf} || exit 1

function log {
	echo "================= $1 ==================="
}

function buildRaspInstall {
	cd "$BASE_DIR/rasp-install/java"
	log "mvn clean package..."
	mvn clean package -DskipTests=true || exit 1 
	cp "$BASE_DIR/rasp-install/java/target/RaspInstall.jar" "$OUTPUT_ROOT" || exit 1
	rm -rf "$BASE_DIR/rasp-install/java/target"
}

# 编译Rasp
function buildRasp {
	cd "$BASE_DIR/agent/java" || exit 1
	log "mvn clean package"
	mvn clean package -DskipTests=true || exit 1
	cp "$BASE_DIR/agent/java/boot/target/rasp.jar" "$OUTPUT_ROOT/rasp" || exit 1
	cp "$BASE_DIR/agent/java/engine/target/rasp-engine.jar" "$OUTPUT_ROOT/rasp" || exit 1
}

function buildPlugin {
	cd "$PLUGIN_ROOT" || exit 1
	cp "$PLUGIN_ROOT/plugin.js" "$OUTPUT_ROOT/rasp/plugins/official.js" || exit 1
}

function copyConf {
	cp "$BASE_DIR/rasp-install/java/src/main/resources/openrasp.yml" "$OUTPUT_ROOT/rasp/conf/openrasp.yml" || exit 1
}

log "[1] build RaspInstall.jar" 
buildRaspInstall

log "[2] copy OpenRASP Plugin"
buildPlugin

log "[3] copy rasp.yaml"
copyConf

log "[5] build OpenRASP"
buildRasp

cd "$OUTPUT_ROOT/.."
target=rasp-java-$RASP_VERSION.tar.gz
tar -czvf $target "$BASENAME" || exit
#mv "$target" "$BASE_DIR" || exit
log "Created $target"

log "SUCCESS!"
