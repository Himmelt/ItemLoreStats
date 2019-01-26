package com.github.supavitax.itemlorestats.Commands;

import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_GetResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Name_Com {

   Util_GetResponse util_GetResponse = new Util_GetResponse();
   Util_Colours util_Colours = new Util_Colours();


   public void onNameCommand(CommandSender sender, String[] args) {
      if(args[0].equalsIgnoreCase("name")) {
         if(sender instanceof Player) {
            Player player = (Player)sender;
            if(!player.isOp() && !player.hasPermission("ils.admin")) {
               player.sendMessage(this.util_GetResponse.getResponse("ErrorMessages.PermissionDeniedError", (Entity)null, (Entity)null, "", ""));
            } else if(player.getItemInHand() != null) {
               if(player.getItemInHand().getType() != Material.AIR) {
                  if(args.length > 1) {
                     String storeName = player.getItemInHand().getItemMeta().getDisplayName();
                     String newName = "";
                     ItemStack getItemInHand = new ItemStack(player.getItemInHand());
                     ItemMeta getItemInHandMeta = getItemInHand.getItemMeta();

                     for(int i = 1; i < args.length; ++i) {
                        if(i >= 2) {
                           newName = newName + " " + args[i];
                        } else {
                           newName = args[i];
                        }
                     }

                     getItemInHandMeta.setDisplayName(this.util_Colours.replaceTooltipColour(newName));
                     getItemInHand.setItemMeta(getItemInHandMeta);
                     player.sendMessage(ChatColor.LIGHT_PURPLE + "Changed the name of " + ChatColor.RESET + storeName + ChatColor.LIGHT_PURPLE + " to " + ChatColor.RESET + this.util_Colours.replaceTooltipColour(newName));
                     player.getInventory().setItemInHand(new ItemStack(getItemInHand));
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
