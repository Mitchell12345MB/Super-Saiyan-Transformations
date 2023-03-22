package org.apache.maven.supersaiyantransformationsp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;

public class SSJCommands implements CommandExecutor {

    private final SSJ ssj;

    public SSJCommands(SSJ ssj) {

        this.ssj = ssj;

    }

    Random rand = new Random();

    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {

        if (sender instanceof final Player player) {

            boolean cmdFound = false;

            if (args.length >= 1)

                if (cmd.getName().equalsIgnoreCase("ssj")) {

                    if (args[0].equalsIgnoreCase("base")) {

                        cmdFound = true;

                        ssj.getPs().cancel(player);

                        ssj.getSSJMethodChecks().removeP(player);

                        player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString("Base_Form.PrefixQuote")).replace("&", "§"));

                        player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString("Base_Form.MiddleQuote")).replace("&", "§"));

                        player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString("Base_Form.SuffixQuote")).replace("&", "§"));

                    }

                    int i;

                    for (i = 0; i < ssj.ssjList.size(); i++) {

                        final String ssjs = ssj.ssjList.get(i);

                        int stime = ssj.getConfig().getInt(ssjs + ".Cooldown");

                        int spc = ssj.getConfig().getInt(ssjs + ".Particle.Count");

                        int spc2 = ssj.getConfig().getInt(ssjs + ".Particle2.Count");

                        String particle1 = ssj.getConfig().getString(ssjs + ".Particle.Type");

                        String particle2 = ssj.getConfig().getString(ssjs + ".Particle2.Type");

                        if (args[0].equalsIgnoreCase(ssj.getConfig().getString(ssjs + ".CommandName"))) {

                            cmdFound = true;

                            if (player.hasPermission(
                                    "ssj." + ssj.getConfig().getString(ssjs + ".CommandName"))) {

                                if (ssj.getCd().cooldownTime.containsKey(player)) {

                                    player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.RED
                                            + "You must wait for " + ssj.getCd().cooldownTime.get(player) + " seconds.");

                                    return true;

                                }

                                ssj.getSSJMethodChecks().removeP(player);

                                if (ssj.getConfig()
                                        .getBoolean(Objects.requireNonNull(ssj.getConfig().getString(ssjs + ".CommandName")))) {

                                    Bukkit.broadcastMessage(player.getDisplayName() + ": " + Objects.requireNonNull(ssj.getConfig()
                                            .getString(ssjs + ".PrefixQuote")).replace("&", "§"));

                                    Bukkit.broadcastMessage(player.getDisplayName() + ": " + Objects.requireNonNull(ssj.getConfig()
                                            .getString(ssjs + ".MiddleQuote")).replace("&", "§"));

                                    Bukkit.broadcastMessage(player.getDisplayName() + ": " + Objects.requireNonNull(ssj.getConfig()
                                            .getString(ssjs + ".SuffixQuote")).replace("&", "§"));

                                } else {

                                    player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString(ssjs + ".PrefixQuote"))
                                            .replace("&", "§"));

                                    player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString(ssjs + ".MiddleQuote"))
                                            .replace("&", "§"));

                                    player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString(ssjs + ".SuffixQuote"))
                                            .replace("&", "§"));

                                }

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.DAMAGE_RESISTANCE"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
                                            100000000,
                                            ssj.getConfig().getInt(ssjs + ".Traits.DAMAGE_RESISTANCE")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.FAST_DIGGING"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
                                            ssj.getConfig().getInt(ssjs + ".Traits.FAST_DIGGING")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.FIRE_RESISTANCE"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
                                            ssj.getConfig().getInt(ssjs + ".Traits.FIRE_RESISTANCE")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.HEAL"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
                                            ssj.getConfig().getInt(ssjs + ".Traits.HEAL")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.INCREASE_DAMAGE"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
                                            ssj.getConfig().getInt(ssjs + ".Traits.INCREASE_DAMAGE")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.JUMP"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
                                            ssj.getConfig().getInt(String.valueOf(ssjs) + ".Traits.JUMP")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.REGENERATION"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
                                            ssj.getConfig().getInt(String.valueOf(ssjs) + ".Traits.REGENERATION")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.SATURATION"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
                                            ssj.getConfig().getInt(String.valueOf(ssjs) + ".Traits.SATURATION")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.SPEED"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
                                            ssj.getConfig().getInt(ssjs + ".Traits.SPEED")));

                                if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.HEALTH_BOOST"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
                                            ssj.getConfig().getInt(ssjs + ".Traits.HEALTH_BOOST")));

                                if (this.rand.nextInt(100) <= ssj.getConfig().getInt(ssjs + ".DamageOdds")) {

                                    player.setLastDamage(ssj.getConfig().getInt(ssjs + ".DamageTaken"));

                                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);

                                    player.removePotionEffect(PotionEffectType.HEAL);

                                    player.removePotionEffect(PotionEffectType.REGENERATION);

                                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);

                                    if (ssj.getConfig().getBoolean(ssjs + ".Use_Traits?.HEALTH_BOOST"))

                                        player.addPotionEffect(
                                                new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));

                                    player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString(ssjs + ".DamageTakenQuote"))
                                            .replace("&", "§"));

                                }

                                if (ssj.getConfig().getBoolean("Lightning_Effect"))

                                    player.getWorld().strikeLightning(player.getLocation());

                                if (ssj.getConfig().getBoolean("Explosion_Effect"))

                                    player.getWorld().createExplosion(player.getLocation(),

                                            ssj.getConfig().getInt("Explosion_Radious"));

                                if (ssj.getConfig().getBoolean("Sound_Effect"))

                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
                                            2.0F);

                                ssj.getPs().ssjPs(sender, player, particle1, particle2, spc, spc2);

                                ssj.getCd().ssjCooldown(player, stime);

                                return true;
                            }

                            player.sendMessage(
                                    ChatColor.DARK_RED + "You do not have permission to perform this command.");

                        }

                    }

                    if (args[0].equalsIgnoreCase("list") && !cmdFound) {

                        player.sendMessage(ChatColor.RED + "List of ssj transformations:");

                        player.sendMessage(ChatColor.GREEN + "[Command] [ID] [Transformation type] [Boost]");

                        player.sendMessage(ChatColor.RED + "----------------------------------------------");

                        player.sendMessage(ChatColor.BLUE + "[ssj] [base] [Base Form] [null]");

                        player.sendMessage(ChatColor.RED + "----------------------------------------------");

                        for (i = 0; i < ssj.ssjList.size(); i++) {

                            final String ssjs = ssj.ssjList.get(i);

                            if (player.hasPermission(
                                    "ssj." + ssj.getConfig().getString(ssjs + ".CommandName"))) {

                                player.sendMessage(ChatColor.BLUE + "[ssj] ["
                                        + ssj.getConfig().getString(ssjs + ".CommandName") + "] ["
                                        + ssj.getConfig().getString(ssjs + ".Desc") + "] ["
                                        + ssj.getConfig().getString(ssjs + ".DescBoost") + "]");

                                player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
                                        + ChatColor.GOLD + ssj.getConfig().getInt(ssjs + ".DamageOdds")
                                        + ChatColor.RED + "%");

                                player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
                                        + ChatColor.GOLD + ssj.getConfig().getInt(ssjs + ".DamageTaken"));

                                player.sendMessage(ChatColor.RED + "----------------------------------------------");

                            }

                        }

                        for (i = 0; i < ssj.kaiokenList.size(); i++) {

                            final String kaioken = ssj.kaiokenList.get(i);

                            if (player.hasPermission(
                                    "ssj." + ssj.getConfig().getString(kaioken + ".CommandName"))) {

                                player.sendMessage(ChatColor.BLUE + "[kaioken] ["
                                        + ssj.getConfig().getString(kaioken + ".CommandName") + "] ["
                                        + ssj.getConfig().getString(kaioken + ".Desc") + "] ["
                                        + ssj.getConfig().getString(kaioken + ".DescBoost") + "]");

                                player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
                                        + ChatColor.GOLD + ssj.getConfig().getInt(kaioken + ".DamageOdds")
                                        + ChatColor.RED + "%");

                                player.sendMessage(
                                        ChatColor.RED + "Damage taken if transformed incorrectly: " + ChatColor.GOLD
                                                + ssj.getConfig().getInt(kaioken + ".DamageTaken"));

                                player.sendMessage(ChatColor.RED + "----------------------------------------------");

                            }

                        }

                        cmdFound = true;

                    }

                    if (args[0].equalsIgnoreCase("info") && !cmdFound) {

                        player.sendMessage(ChatColor.RED + "Super Saiyan Transformations info:");

                        player.sendMessage(
                                ChatColor.BOLD + "Current version: " + ChatColor.RED + ssj.getDescription().getVersion());

                        player.sendMessage(ChatColor.GREEN + "Plugin Developed by: " + ChatColor.RED
                                + ssj.getDescription().getAuthors());

                        player.sendMessage(
                                ChatColor.GOLD + "If you have any questions about this plugin, here's a link!");

                        player.sendMessage(ChatColor.RED + "http://bit.ly/2avikLF");

                        cmdFound = true;

                    }

                } else if (cmd.getName().equalsIgnoreCase("kaioken")) {

                    for (int i = 0; i < ssj.kaiokenList.size(); i++) {

                        final String kaioken = ssj.kaiokenList.get(i);

                        int ktime = ssj.getConfig().getInt(kaioken + ".Kaioken_Cooldown") * 20;

                        int ktime2 = ssj.getConfig().getInt(kaioken + ".Cooldown");

                        String particlek1 = ssj.getConfig().getString(kaioken + ".Particle.Type");

                        String particlek2 = ssj.getConfig()
                                .getString(kaioken + ".Particle2.Type");

                        int kpc = ssj.getConfig().getInt(kaioken + ".Particle.Count");

                        int kpc2 = ssj.getConfig().getInt(kaioken + ".Particle2.Count");

                        if (args[0].equalsIgnoreCase(ssj.getConfig().getString(kaioken + ".CommandName"))) {

                            cmdFound = true;

                            if (player.hasPermission(
                                    "ssj." + ssj.getConfig().getString(kaioken + ".CommandName"))) {

                                if (ssj.getCd().cooldownTime.containsKey(player)) {
                                    player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.RED
                                            + "You must wait for " + ssj.getCd().cooldownTime.get(player) + " seconds.");

                                    return true;

                                }

                                ssj.getSSJMethodChecks().removeP(player);

                                if (ssj.getConfig().getBoolean(kaioken + ".Broadcast")) {

                                    Bukkit.broadcastMessage(player.getDisplayName() + ": " + Objects.requireNonNull(ssj.getConfig()
                                            .getString(kaioken + ".PrefixQuote")).replace("&", "§"));

                                    Bukkit.broadcastMessage(player.getDisplayName() + ": " + Objects.requireNonNull(ssj.getConfig()
                                            .getString(kaioken + ".MiddleQuote")).replace("&", "§"));

                                    Bukkit.broadcastMessage(player.getDisplayName() + ": " + Objects.requireNonNull(ssj.getConfig()
                                            .getString(kaioken + ".SuffixQuote")).replace("&", "§"));

                                } else {

                                    player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString(kaioken + ".PrefixQuote"))
                                            .replace("&", "§"));

                                    player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString(kaioken + ".MiddleQuote"))
                                            .replace("&", "§"));

                                    player.sendMessage(Objects.requireNonNull(ssj.getConfig().getString(kaioken + ".SuffixQuote"))
                                            .replace("&", "§"));

                                }

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.DAMAGE_RESISTANCE"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
                                            100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.DAMAGE_RESISTANCE")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.FAST_DIGGING"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.FAST_DIGGING")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.FIRE_RESISTANCE"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.FIRE_RESISTANCE")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.HEAL"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
                                            ssj.getConfig().getInt(kaioken + ".Traits.HEAL")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.INCREASE_DAMAGE"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.INCREASE_DAMAGE")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.JUMP"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.JUMP")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.REGENERATION"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
                                            ssj.getConfig().getInt(kaioken + ".Traits.REGENERATION")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.SATURATION"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.SATURATION")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.SPEED"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.SPEED")));

                                if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.HEALTH_BOOST"))

                                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
                                            ssj.getConfig().getInt(kaioken + ".Traits.HEALTH_BOOST")));

                                if (this.rand.nextInt(100) <= ssj.getConfig().getInt(kaioken + ".DamageOdds")) {

                                    player.setLastDamage(ssj.getConfig().getInt(kaioken + ".DamageTaken"));

                                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);

                                    player.removePotionEffect(PotionEffectType.HEAL);

                                    player.removePotionEffect(PotionEffectType.REGENERATION);

                                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);

                                    if (ssj.getConfig().getBoolean(kaioken + ".Use_Traits?.HEALTH_BOOST"))

                                        player.addPotionEffect(
                                                new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));

                                    player.sendMessage(
                                            Objects.requireNonNull(ssj.getConfig().getString(kaioken + ".DamageTakenQuote"))
                                                    .replace("&", "§"));

                                }

                                if (ssj.getConfig().getBoolean("Lightning_Effect"))

                                    player.getWorld().strikeLightning(player.getLocation());

                                if (ssj.getConfig().getBoolean("Explosion_Effect"))

                                    player.getWorld().createExplosion(player.getLocation(),
                                            ssj.getConfig().getInt("Explosion_Radious"));

                                if (ssj.getConfig().getBoolean("Sound_Effect"))

                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
                                            2.0F);

                                ssj.getPs().kaiokenPs(sender, player, particlek1, particlek2, ktime, kpc, kpc2);

                                ssj.getCd().kaiokenCooldown(player, ktime2);

                            } else {

                                player.sendMessage(String.valueOf(ssj.Prefix) + ChatColor.DARK_RED
                                        + "You do not have permission to perform this command.");

                            }

                        }

                    }

                }

            if (!cmdFound) {

                player.sendMessage(ChatColor.RED + "Usage: /ssj [id]");

                player.sendMessage(ChatColor.RED + "Usage: /kaioken x[id]");

                player.sendMessage(ChatColor.RED + "Usage: /ssj list");

                player.sendMessage(ChatColor.RED + "Usage: /ssj info");

            }

        } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {

            ssj.saveDefaultConfig();

            ssj.reloadConfig();

            Bukkit.getLogger().info(ssj.Prefix + "Successfully reloaded config!");

        }

        return false;
    }

}
