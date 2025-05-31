# 6b6tCore-Folia

**6b6tCore-Folia** is the all-in-one core plugin used on [6b6t.com](https://6b6t.com), a modern high-performance anarchy server built on [Folia](https://github.com/PaperMC/Folia) 1.20+. This plugin handles account security, gameplay tweaks, teleport utilities, and intentional exploits. No bloat, no fluff, no hand-holding.

> ⚠️ This plugin is built for anarchy. No official support, no updates unless we want to. Use at your own risk.
> ⚠️ Credits to BlBi for the original login plugin.

---

## 🔧 Key Features

- ✅ SQLite-based **/register** and **/login** system with optional Bedrock support via Floodgate.
- ✅ Text-based **captcha** to block bots/skids on first join. Codes include random letter prefixes.
- ✅ Crossplay-friendly login forms (optional).
- ✅ `/tpa`, `/tpaccept`, `/tphere` for basic teleportation without home/warp BS.
- ✅ Solo chest boat dupe with `/dupe` command explaining how to abuse it.
- ✅ `/worldstats` shows total world size and age — no reset ever.
- ✅ `/kill` and `/suicide` to insta-die, because walking into lava is slow.
- ✅ Admin commands under `/blbilogin` to reload config or set forced login spawn.
- ✅ Password reset for players or forced reset via console.
- ✅ Optional anti-32k damage patch and wither griefing tweaks (for balance, not safety).
- ✅ Built-in bot/spam protection (simple but effective).
- ✅ Hides `/pl` and `/plugins` from plugin-leakers and tryhards.
- ✅ Prevents unregistered users from moving, chatting, or cheating until they log in.
- ✅ Particle and chat reminders for unauthenticated players.

---

## 🛠️ Building

Clone and build with Gradle:

```bash
./gradlew build
```

JARs will be in `build/libs/`. Move:
- `blbiLogin-<version>.jar`
- `libs/blbiLibrary-1.0.4.jar`

...into your server's `/plugins` folder.

Requires **Folia 1.20.1+**. Optional Bedrock features need [Floodgate](https://github.com/GeyserMC/Floodgate).

---

## ⚙️ Configuration

First boot generates `config.yml` under `plugins/blbiLogin/`.

Key settings:
- `prefix`: Chat prefix.
- `language`: Set message language.
- `playerJoinAutoTeleportToSavedLocation`: Forces all new joins to spawn at a set location (`/blbilogin savelocation`).
- `useSqlite`: Leave true unless you're replacing storage.
- `disable32kDamage`: Cancels damage from OP weapons (Enchants > 1000).
- `noLoginPlayer*`: Full control over what unlogged players can/can’t do.
- `bedrock`: Bedrock/Floodgate login support and auto-login options.

`/captcha` is automatically whitelisted so new players can solve the captcha even when other commands are blocked.

Database is stored in `players.db`.

---

## 🧾 Commands

| Command | What it does |
|--------|---------------|
| `/login <pass>` | Login. Alias: `/l` |
| `/register <pass>` | Register. Alias: `/reg` |
| `/captcha <code>` | Solve the captcha |
| `/resetpassword <old> <new>` | Change password |
| `/kill`, `/suicide` | Instantly die |
| `/worldstats`, `/info` | View world age/size |
| `/tpa <player>` | Request teleport |
| `/tphere <player>` | Summon a player |
| `/tpaccept` | Accept a teleport request |
| `/dupe` | Shows how to do the chest boat dupe |
| `/blbilogin reload` | Reload config |
| `/blbilogin savelocation` | Set forced login spawn point |

Console can reset any player’s password:

```bash
/resetpassword <player> <newPassword>
```

---

## 🚀 Setup

1. Build the plugin and drop the JARs into your Folia server.
2. Start the server once to generate configs.
3. Tweak `plugins/blbiLogin/config.yml` as needed.
4. Restart server.

Players will see a captcha on first join and must register/login before doing anything. 

> Want something different? Fork it. Don’t like it? Don’t use it.
