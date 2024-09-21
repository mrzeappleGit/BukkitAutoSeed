# AutoSeed Plugin

## Overview
**AutoSeed** is a Minecraft plugin that automatically plants seeds for players within a configurable radius when they interact with the environment. The plugin also allows players to toggle the feature on or off for themselves and adjust the auto-seeding radius.

## Features
- Automatically plants seeds in a configurable radius around players.
- Toggle auto-seeding on or off for individual players.
- Customize the radius of auto-seeding.
- Simple and easy to configure.

## Installation

1. Download the latest version of the plugin jar file.
2. Place the plugin file in the `plugins` folder of your Minecraft server.
3. Restart or reload the server.

## Commands

| Command                   | Description                                               | Permission             |
| ------------------------- | --------------------------------------------------------- | ---------------------- |
| `/autoseedreload`          | Reloads the plugin configuration.                         | `autoseed.reload`      |
| `/autoseedtoggle`          | Toggles auto-seeding on or off for the player executing the command. | `autoseed.toggle`      |
| `/autoseedradius <radius>` | Sets the auto-seeding radius.                             | `autoseed.radius`      |

## Permissions
The plugin uses the following permissions to control access to commands:

- `autoseed.reload` – Grants permission to reload the plugin configuration.
- `autoseed.toggle` – Allows players to toggle auto-seeding on or off for themselves.
- `autoseed.radius` – Grants permission to set the auto-seeding radius.

## Configuration

You can configure various options by modifying the `config.yml` file:

```yaml
# The radius in blocks around the player in which seeds will be automatically planted
radius: 5

# List of players for whom the auto-seeding is enabled
enabled_players:
  - "Player1"
  - "Player2"
