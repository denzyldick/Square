# Square

A tile-based puzzle game built with [libGDX](https://libgdx.com/).

This project started as a way to learn Java and game development. It was originally created around 2012-2013 and has been modernized with a Gradle 8.5 build, libGDX 1.12, and Java 17. It is a great example of how a simple game can teach core Java concepts — OOP, event-driven programming, resource management, and state machines.

## Gameplay

Navigate a square through tiled maze levels, collecting all the golden dots without touching the walls. You have 3 lives per level.

**Controls:**
| Input | Action |
|-------|--------|
| Touch top-left corner | Move up-left |
| Touch top-right corner | Move up-right |
| Touch bottom-left corner | Move down-left |
| Touch bottom-right corner | Move down-right |
| `Q` key | Move up-left |
| `E` key | Move up-right |
| `A` key | Move down-left |
| `D` key | Move down-right |
| `||` button | Pause / back to menu |

## Features

- 12 handcrafted tile-map levels
- Keyboard and touch input
- Dark theme UI with animated backgrounds
- Sound effects and background music
- Volume toggle
- Level progression with unlock system

## Prerequisites

- **JDK 17** or newer
- No Android SDK required for the desktop build

## Build & Run

```bash
# Clone the repository
git clone https://github.com/denzyldick/Square.git
cd Square

# Build and run the desktop version
./gradlew desktop:run
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Project Structure

```
Square/
├── core/                    Shared game code (screens, actors, assets)
│   └── src/
│       └── com/denzyldick/square/
│           ├── screens/     Game screens (menu, gameplay, settings)
│           ├── actors/      Game entities (player square)
│           └── *.java       Shared utilities (Font, Skin, Sound, Animation)
├── desktop/                 Desktop launcher (LWJGL3)
├── android/                 Android launcher + assets
├── ios/                     iOS launcher (RoboVM)
├── build.gradle             Root build configuration
└── settings.gradle          Module declarations
```

## Tech Stack

- **Engine:** libGDX 1.12.1
- **Backend:** LWJGL3 (desktop)
- **Build:** Gradle 8.5
- **Language:** Java 17
- **Map Editor:** Tiled (`.tmx` files)
- **Fonts:** FreeType (runtime font generation)

## License

This project is licensed under the [MIT License](LICENSE).

The file `com/denzyldick/square/json/` contains a vendored copy of a JSON parsing library originally released under the Eclipse Public License 1.0.
