# Regex Argument Mod

This simple mod adds a regex argument to the Minecraft command system to let users run commands that match a regular expression. This is to be used in combination with the `regex-mod` build of my CommandAPI plugin.

## What this is

The Regex Argument Mod extends the existing Minecraft command system (Brigadier) by adding an additional argument for commands which accepts input that matches a provided regular expression. If a server were to also use this same argument and have additional commands that utilize this, it is possible to create custom commands with custom syntax (adhering to the limitations of regular expressions).

This is partially inspired by WorldEdit's material list (from the `//set` command) and Mojang/brigadier#96, as well as the many requests for the CommandAPI to support a "list" argument.

Using my [CommandAPI](https://github.com/JorelAli/CommandAPI) plugin, it's easy to create custom commands with custom arguments!

## Usage examples

- Hex color code argument
  ![Implementing a hex color code argument using a regex argument](./images/colormsg.gif)

  ```java
  new CommandAPICommand("colormsg")
      .withArguments(new RegexArgument("hexcolor", "^#?([a-f0-9]{6})$", "This is not a valid hex color!"))
      .withArguments(new GreedyStringArgument("message"))
      .executesPlayer((sender, args) -> {
          String hexColor = (String) args[0];
          String message = (String) args[1];
          sender.spigot().sendMessage(new ComponentBuilder(message).color(ChatColor.of(hexColor)).create());
      })
      .register();
  ```

- Password strength validation with a minimum of 8 characters, at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character (source: [Stackoverflow](https://stackoverflow.com/a/21456918/4779071))
  ![Implementing a client-side password strength checker using a regex argument](./images/password.gif)

  ```java
  String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
  new CommandAPICommand("setpassword")
      .withArguments(new RegexArgument("password", passwordRegex, "This password is not strong enough!"))
      .executes((sender, args) -> {
          String password = (String) args[0];
          // TODO: Do something with password here
          sender.sendMessage("Password set as " + password.chars().map(c -> '*')
              .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
              .toString());
      })
      .register();
  ```

- A list of materials, using the CommandAPI's suggestions API
  ![A list of materials using a regex argument and CommandAPI's suggestions](./images/materials.gif)
  ```java
  Argument materialArg = new RegexArgument("types", "[a-zA-Z0-9_]+(,[a-zA-Z0-9_]+)*").replaceSuggestions(ArgumentSuggestions.strings(info -> {
      // Make a modifiable list of all materials
      List<String> materialList = new ArrayList<>(Arrays.stream(Material.values()).map(Material::name).map(String::toLowerCase).toList());
      String[] currentMaterials = info.currentArg().split(",");
      
      if(info.currentArg().endsWith(",")) {
          // We're expecting a list of new materials to suggest. Suggest a new material
          // that is not currently in the list
          List<String> existingMaterials = Arrays.stream(currentMaterials).map(String::toLowerCase).toList();
          materialList.removeAll(existingMaterials);
          
          // Suggest <currentarg>,<material> for each material
          return materialList.stream().map(mat -> info.currentArg() + mat).toArray(String[]::new);
      } else {
          // We're expecting some auto-completion for the current material. Perform a search
          // of what we're currently typing
          
          // Remove the last argument and turn it into a string as the base for suggestions
          List<String> currentArgList = new ArrayList<>(Arrays.asList(currentMaterials));
          currentArgList.remove(currentArgList.size() - 1);
          String suggestionBase = currentArgList.isEmpty() ? "" : currentArgList.stream().collect(Collectors.joining(",")) + ",";
          
          return materialList.stream()
              .filter(mat -> mat.startsWith(currentMaterials[currentMaterials.length - 1].toLowerCase()))
              .map(mat -> suggestionBase + mat)
              .toArray(String[]::new);
      }
  }));

  new CommandAPICommand("materials")
      .withArguments(materialArg)
      .withArguments(new IntegerArgument("amount"))
      .executesPlayer((player, args) -> {
          String materials = (String) args[0];
          int amount = (int) args[1];
          for(String str : materials.split(",")) {
              player.getInventory().addItem(new ItemStack(Material.valueOf(str.toUpperCase()), amount));
          }
      })
      .register();
  ```

## Developing a plugin with the regex argument

Follow these instructions at your own risk. They do not guarantee good coding practices, but will get the job done.

- Download the CommandAPI core library file from [here](https://github.com/JorelAli/CommandAPI/actions/runs/2095198134)
- Unzip it and add the `.jar` file to your project's path. _(If using Maven, consider using the `system` dependency scope to point to the `.jar` file directly)_
- Create a normal command using the CommandAPI (you can find its documentation [here](https://commandapi.jorel.dev/)), using the `RegexArgument`

## Installing the regex argument mod

- Grab the latest mod from [here](https://github.com/JorelAli/MinecraftRegexArgumentMod/actions/runs/2095194157) under the **Artifacts** section
- Unzip the artifacts .jar and put `fabric-example-mod-1.0.0.jar` in your `mods/` folder

## Spigot server setup

- Add the CommandAPI plugin (available [here](https://github.com/JorelAli/CommandAPI/actions/runs/2095198134)) to your server's `plugins/` folder
- Add your plugin to the server's `plugins/` folder
- Start the server and enjoy!

## License

This project is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.
