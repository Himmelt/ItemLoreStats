package com.github.supavitax.itemlorestats.Commands;

import com.github.supavitax.itemlorestats.GenerateFromFile;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import java.io.File;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Export_Com {

   Util_GetResponse util_GetResponse = new Util_GetResponse();
   Util_Colours util_Colours = new Util_Colours();
   GenerateFromFile generateFromFile = new GenerateFromFile();


   public void onExportCommand(CommandSender sender, String[] args) {
      if(args[0].equalsIgnoreCase("export")) {
         if(sender instanceof Player) {
            Player player = (Player)sender;
            if(!player.isOp() && !player.hasPermission("ils.admin")) {
               player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", (Entity)null, (Entity)null, "", ""));
            } else if(player.getItemInHand() != null) {
               if(player.getItemInHand().getType() != Material.AIR) {
                  if(args.length > 1) {
                     String newItemName = "";

                     for(int i = 0; i < args.length; ++i) {
                        if(i >= 2) {
                           newItemName = newItemName + " " + args[i];
                        } else {
                           newItemName = args[i];
                        }
                     }

                     if((new File(ItemLoreStats.plugin.getDataFolder() + File.separator + "SavedItems" + File.separator + newItemName + ".yml")).exists()) {
                        player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.ItemAlreadyExistsError", (Entity)null, (Entity)null, "", ""));
                     } else {
                        this.generateFromFile.exportWeapon(player, newItemName);
                     }
                  } else {
                     player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.IncludeItemNameError", (Entity)null, (Entity)null, "", ""));
                  }
               } else {
                  player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", (Entity)null, (Entity)null, "", ""));
               }
            } else {
               player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.NullItemInHandError", (Entity)null, (Entity)null, "", ""));
            }
         } else {
            System.out.println("[ILS]" + this.util_GetResponse.getResponse("ErrorMessages.IngameOnlyError", (Entity)null, (Entity)null, "", ""));
         }
      }

   }
}
