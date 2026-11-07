# Chatulator – a calculator right in your chat

Type a math problem into chat and press Enter. Chatulator shows the result instead of sending the message, so your calculation never reaches the server or other players.

**Supported:** `+` `-` `*` `/` `%` `^`, parentheses, decimals and negative numbers
**Example:** `(64*9)/4` → `[Chatulator] (64*9)/4 = 144`

- Runs only on your client, so it works on any server
- Ordinary chat messages and commands are left alone
- Available for Fabric, Forge and NeoForge on 1.20.1, 1.21, 26.1, 26.2 and 26.3

## Requirements

| Minecraft | Java | Loaders |
|---|---|---|
| 1.20.1 | 17+ | Fabric, Forge, NeoForge |
| 1.21 / 1.21.1 | 21+ | Fabric, Forge, NeoForge |
| 26.1, 26.2, 26.3 | 25+ | Fabric, Forge, NeoForge |

The Fabric version requires the [Fabric API](https://modrinth.com/mod/fabric-api).

---

## Building from source

This is a single Gradle project that builds the mod for every supported Minecraft version and loader. You only need **JDK 25**. Each target is compiled to the right Java version automatically.

```
gradlew buildAll                         # everything -> dist/
gradlew buildAll -Ptargets=26.3          # a single Minecraft version
gradlew buildAll -Ploaders=fabric        # a single loader
gradlew :neoforge-26.3:build             # a single target
gradlew :fabric-26.3:runClient           # test in game
```

The first build downloads Minecraft and all loaders and takes about 10 minutes. Builds after that take seconds.

## Project layout

| Folder | Contents |
|---|---|
| `common/` | Code shared by every target (calculator and chat logic) |
| `platforms/fabric` | Fabric entrypoint and `fabric.mod.json` |
| `platforms/forge-eventbus6` | Forge 1.20.1 / 1.21 (old EventBus) and `mods.toml` |
| `platforms/forge-eventbus7` | Forge 26.x (EventBus 7) and `mods.toml` |
| `platforms/neoforge` | NeoForge entrypoint and `neoforge.mods.toml` |
| `platforms/chat-*` | Version-specific access to the chat window (the API changed in 26.1 and 26.2) |
| `versions/<mc>.properties` | Loader and API versions for each Minecraft version |
| `versions/<mc>/<loader>/` | Subproject; version-specific code can go in `src/main/java` if ever needed |

## Adding a new Minecraft version

1. Create `versions/<new>.properties` by copying an existing file and updating the versions.
2. Add the version to `targets` in `gradle.properties`.
3. Run `gradlew buildAll`. If it fails to compile, you may need a new `platforms/chat-*` variant.

## Notes

- **NeoForge 1.20.1** is a fork of Forge 47 and loads Forge mods directly, so `chatulator-neoforge-1.20.1` is a copy of the Forge 1.20.1 jar.
- The **1.21** jars are built against 1.21.1 and run on 1.21 and 1.21.1. The **26.1** jars are built against 26.1.2 and run on 26.1 through 26.1.2.
- To get decompiled Minecraft sources in your IDE, add `-Pdecompile_sources=true` (slower).
