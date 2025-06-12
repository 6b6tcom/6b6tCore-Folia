# 6b6tCore

6b6tCore is a plugin designed for Folia and Spigot based servers. It powers the core features of the 6b6t network, providing a multilingual login system, teleportation utilities and protections against illegal items.

## Features

- **Login and registration** backed by SQLite
- **Teleport requests** with `/tpa`, `/tphere` and `/tpaccept`
- **Vanish mode** for staff members
- **Anti-illegal item checks** to clean problematic items, including overstacked
  items and invalid enchantments
- **Chat logging and miscellaneous utilities**
- **Hourly promotional broadcast** advertising other servers
- **Internationalisation** with language files in `src/main/resources/languages`

## Building

The project uses Gradle and requires JDK 17 or later.

```bash
./gradlew build
```

The compiled plugin JAR will be located in `build/libs`.

## Anti-illegal system

The plugin ships with a lightweight item validator that removes or fixes
dangerous items as players interact with them. Checks include:

- Overstacked item stacks
- Extreme durability values
- Invalid attribute modifiers
- Enchantments above the allowed maximum
- Potions with extreme amplifier or duration
- Excessive book page counts

## Configuration

A default `config.yml` will be created on first launch. It contains options for language, login behaviour and other settings. Messages can be translated by editing the files in `src/main/resources/languages`.

## License

This project is licensed under the Apache License 2.0. See `LICENSE` for details.

Visit **6b6t.com** to see the plugin in action.
