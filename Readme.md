# BattleMath
![Android](https://img.shields.io/badge/platform-Android-green)
![Java](https://img.shields.io/badge/language-Java-orange)
![License](https://img.shields.io/badge/license-MIT-blue)

Android companion application developed in Java to support tabletop gaming sessions.

BattleMath provides digital tools to streamline tabletop gaming sessions by automating complex calculations, tracking match progress and improving the speed and accuracy of gameplay.

The application was designed and developed independently, including application architecture, business logic, Android UI development and source code management.

## Demo

![BattleMath Demo](screenshots/battlemath-demo.gif)

## Features

### Operation Management

- Track game turns and victory points.
- Configure attacker and defender teams.

### Weapon Simulation

- Fire predefined weapons with automatic dice rolls and damage calculation.
- Support different target types (Mechs and Vehicles).
- Automatically calculate hit locations, damage distribution and critical hits.
- Display damage results directly on the target interface.

### Custom Weapon Builder

- Create custom weapons by configuring:
  - Weapon size.
  - Grouping size.
  - Cluster behaviour.
- Automatically calculate damage distribution and critical results.

### Audio Feedback

- Weapon-specific sound effects.

### Settings

- Switch between Light and Dark theme.
- Enable or disable application sounds.
- Configure application preferences.

## Screenshots
Main application screens showing game management, automatic weapon calculation and custom weapon configuration.

<table>
  <tr>
    <td align="center" valign="top">
      <img src="screenshots/operation-management.png" width="240">
      <br>
      <strong>Operation Management</strong>
      <br>
      Manage teams, game turns and victory points during a game session.
    </td>
    <td align="center" valign="top">
      <img src="screenshots/weapon-calculation.png" width="240">
      <br>
      <strong>Weapon Calculation</strong>
      <br>
      Automatic weapon simulation with hit location, damage and critical hit calculation.
    </td>
    <td align="center" valign="top">
      <img src="screenshots/weapon-calculation-dark.png" width="240">
      <br>
      <strong>Weapon Calculation (Dark Theme)</strong>
      <br>
      Weapon simulation using the dark theme.
    </td>
  </tr>
  <tr>
    <td align="center" valign="top">
      <img src="screenshots/custom-weapon.png" width="240">
      <br>
      <strong>Custom Weapon</strong>
      <br>
      Create custom weapons by configuring parameters and calculating damage results.
    </td>
    <td align="center" valign="top">
      <img src="screenshots/settings.png" width="240">
      <br>
      <strong>Settings</strong>
      <br>
      Customize application preferences, appearance and sound effects.
    </td>
    <td></td>
  </tr>
</table>

## Technologies

- Java 8
- Android Studio
- Android SDK 34
- AndroidX
- Material Components
- View Binding
- ConstraintLayout
- ViewPager2
- Apache POI
- Git


## Architecture

The application was developed independently, covering:

- Application architecture design.
- Business logic implementation.
- Complex calculation algorithms.
- Android user interface development.
- Local data management and file handling.
- Theme management (Light / Dark mode).

## Build Requirements

- Android Studio
- JDK 8+
- Android SDK 34

Minimum Android version:

- Android 8.0 (API 26)

## User Experience

BattleMath provides a modern Material Design interface with:

- Light and Dark themes.
- Responsive layouts.
- Intuitive navigation.
- Immediate visual feedback during gameplay.

## Development Background

BattleMath was created as a personal project to improve Android development skills and gain practical experience in building a complete mobile application.

The project involved:
- Designing a complete mobile application from scratch.
- Implementing non-trivial game logic.
- Developing custom user interfaces.
- Managing source code using Git.

## Disclaimer

BattleMath is an unofficial fan-made project inspired by the BattleTech universe.
BattleTech and related trademarks are property of their respective owners.
Some assets included in this project (such as images, sounds or other media) may belong to their respective copyright holders and are not covered by this project's MIT License.
This project is not affiliated with, endorsed by, or sponsored by the owners of the BattleTech intellectual property.

## License

MIT License