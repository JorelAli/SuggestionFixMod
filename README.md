# SuggestionFixMod

This Fabric mod fixes [MC-165562](https://bugs.mojang.com/browse/MC-165562).

## Supported versions:

- Minecraft 1.18.x

## What this is

[MC-165562](https://bugs.mojang.com/browse/MC-165562) is a bug which prevents users from seeing what command they're typing. When a user is typing a command, instead of suggesting the next argument, it instead displays a warning:

![Displaying a warning after /give @p diamond](./images/old.png)

The SuggestionFixMod is a Fabric mod that fixes this:

![Displaying [<count>] after /give @p diamond](./images/fixed.png)

## Installing the mod

- Grab the latest mod from [Releases](https://github.com/JorelAli/SuggestionFixMod/releases) and add it to your `mods/` folder

## Motivation

[MC-165562](https://bugs.mojang.com/browse/MC-165562) was opened on 13th November 2019. This project was created on 26th April 2022. It took less than a day for me to fix a bug which hasn't been fixed in over 2 years.

My [CommandAPI](https://commandapi.jorel.dev/) project frequently deals with commands and being able to see what commands you're typing has been a feature that I've been needing for the better most of a year. I was fed up, so fixed it myself.

## License

This project is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.
