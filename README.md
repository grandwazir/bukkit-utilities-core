BukkitUtilities - A reusable library for Bukkit.
====================================

BukkitUtilities is a library for the Minecraft wrapper [Bukkit](http://bukkit.org/) that enables rapid development of plugins by providing classes and interfaces for common tasks. At the moment it is used exclusively by all the plugins that I maintain or develop. The aim of the library is to reduce the amount of boiler plate code which is duplicated between plugins and provide a consistent approach to a number of challenges. 

## Features

- Localisation (Simple, MessageFormat and ChoiceFormat).
- Allow users to override and provide their own localisations.
- YAML configuration wrapper that handles setting default values.
- Command handling, including a CommandManager for nested commands.
- Database helper methods for common query tasks.
- ColourFormatter for replacing colour names with colour codes.
- TimeFormatter for converting long to human readable time and back again.
- Logger which provides for outputing debug information under specific circumstances.
- Simple implementations of all the interfaces in BukkitUtilities.

## Installation

### Getting BukkitUtilities

The best way to install BukkitUtilities is to use the [symbolic link](http://repository.james.richardson.name/symbolic/BukkitUtilities.jar) to the latest version. This link always points to the latest version of BukkitUtilities, so is safe to use in scripts or update plugins. Additionally you can to use the [RSS feed](http://dev.bukkit.org/server-mods/BukkitUtilities/files.rss) provided by BukkitDev as this also includes a version changelog.
    
Alternatively [older versions](http://repository.james.richardson.name/releases/name/richardson/james/bukkit/bukkitutilities/) are available as well, however they are not supported. If you are forced to use an older version for whatever reason, please let me know why by [opening a issue](https://github.com/grandwazir/BukkitUtilities/issues/new) on GitHub.
