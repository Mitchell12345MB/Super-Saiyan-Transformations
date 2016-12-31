package main.SSJ;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
	private HashMap<Player, Integer> cooldownTime;
	private HashMap<Player, BukkitRunnable> cooldownTask;
	Random rand = new Random();

	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		if (getConfig().getDouble("version") < Double.parseDouble(getDescription().getVersion())) {
			File configFile = new File(getDataFolder(), "config.yml");
			configFile.delete();
			saveDefaultConfig();
			reloadConfig();
			getLogger().warning("Config.yml has been updated!");
		}
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new Listeners(this), this);
		getLogger().info("Super Saiyan Plugin has been ENABLED " + getDescription().getVersion());
		this.cooldownTime = new HashMap<Player, Integer>();
		this.cooldownTask = new HashMap<Player, BukkitRunnable>();
	}

	public void onDisable() {
		getLogger().info("Super Saiyan Plugin has been DISBLED");
		saveDefaultConfig();
	}

	ArrayList<Player> cooldown = new ArrayList<Player>();
	int Cooldown = getConfig().getInt("Cooldown_Timer");
	String Prefix = getConfig().getString("Prefix").replace("&", "§");

	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if ((sender instanceof Player)) {
			final Player player = (Player) sender;
			if ((cmd.getName().equalsIgnoreCase("Potential")) && (player.hasPermission("ssj.potential"))) {
				if (args.length == 0) {
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /potential unleashed");
				} else if ((args[0].equalsIgnoreCase("unleashed")) && (player.hasPermission("ssj.potential"))) {
					if (this.cooldownTime.containsKey(player)) {
						player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
								+ this.cooldownTime.get(player) + " seconds.");
						return true;
					}
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					if (getConfig().getBoolean("Potential_Unleashed.Broadcast")) {
						Bukkit.broadcastMessage(player.getDisplayName() + ": "
								+ getConfig().getString("Potential_Unleashed.PrefixQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Potential_Unleashed.MiddleQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Potential_Unleashed.SuffixQuote").replace("&", "§"));
					} else if (!getConfig().getBoolean("Potential_Unleashed.Broadcast")) {
						player.sendMessage(getConfig().getString("Potential_Unleashed.PrefixQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Potential_Unleashed.MiddleQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Potential_Unleashed.SuffixQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.DAMAGE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.DAMAGE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.FAST_DIGGING")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.FAST_DIGGING")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.FIRE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.FIRE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.HEAL")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
								getConfig().getInt("Potential_Unleashed.Traits.HEAL")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.INCREASE_DAMAGE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.INCREASE_DAMAGE")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.JUMP")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.JUMP")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.REGENERATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
								getConfig().getInt("Potential_Unleashed.Traits.REGENERATION")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.SATURATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.SATURATION")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.SPEED")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.SPEED")));
					}
					if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.HEALTH_BOOST")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
								getConfig().getInt("Potential_Unleashed.Traits.HEALTH_BOOST")));
					}
					if (this.rand.nextInt(100) <= getConfig().getInt("Potential_Unleashed.DamageOdds")) {
						player.setLastDamage(getConfig().getInt("Potential_Unleashed.DamageTaken"));
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Potential_Unleashed.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
						}
						player.sendMessage(
								getConfig().getString("Potential_Unleashed.DamageTakenQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Lightning_Effect")) {
						player.getWorld().strikeLightning(player.getLocation());
					}
					if (getConfig().getBoolean("Explosion_Effect")) {
						player.getWorld().createExplosion(player.getLocation(),
								getConfig().getInt("Explosion_Radious"));
					}
					if (getConfig().getBoolean("Sound_Effect")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
					}
					player.performCommand("pp effect fireworksspark");

					player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
							+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
					this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
					this.cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Main.this.cooldownTime.put(player,
									Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
							if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
								Main.this.cooldownTime.remove(player);
								Main.this.cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
					return true;
				}
			}
			if (cmd.getName().equalsIgnoreCase("ssj")) {
				if (args.length == 0) {
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssj [ID]");
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssj list");
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssj info");
				} else {
					if ((args[0].equalsIgnoreCase("1")) && (player.hasPermission("ssj.1"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_1.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ": "
									+ getConfig().getString("Super_Saiyan_1.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_1.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_1.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Super_Saiyan_1.Broadcast")) {
							player.sendMessage(getConfig().getString("Super_Saiyan_1.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_1.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_1.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
									getConfig().getInt("Super_Saiyan_1.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
									getConfig().getInt("Super_Saiyan_1.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Super_Saiyan_1.Traits.HEALTH_BOOST")));
						}
						if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_1.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Super_Saiyan_1.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Saiyan_1.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
							}
							player.sendMessage(
									getConfig().getString("Super_Saiyan_1.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Lightning_Effect")) {
							player.getWorld().strikeLightning(player.getLocation());
						}
						if (getConfig().getBoolean("Explosion_Effect")) {
							player.getWorld().createExplosion(player.getLocation(),
									getConfig().getInt("Explosion_Radious"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						player.performCommand("pp effect flame");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("2")) && (player.hasPermission("ssj.2"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_2.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ": "
									+ getConfig().getString("Super_Saiyan_2.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_2.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_2.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Super_Saiyan_2.Broadcast")) {
							player.sendMessage(getConfig().getString("Super_Saiyan_2.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_2.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_2.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
									getConfig().getInt("Super_Saiyan_2.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 700,
									getConfig().getInt("Super_Saiyan_2.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Super_Saiyan_2.Traits.HEALTH_BOOST")));
						}
						if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_2.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Super_Saiyan_2.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Saiyan_2.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
							}
							player.sendMessage(
									getConfig().getString("Super_Saiyan_2.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Lightning_Effect")) {
							player.getWorld().strikeLightning(player.getLocation());
						}
						if (getConfig().getBoolean("Explosion_Effect")) {
							player.getWorld().createExplosion(player.getLocation(),
									getConfig().getInt("Explosion_Radious"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						player.performCommand("pp effect flame");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("3")) && (player.hasPermission("ssj.3"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_3.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ": "
									+ getConfig().getString("Super_Saiyan_3.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_3.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_3.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Super_Saiyan_3.Broadcast")) {
							player.sendMessage(getConfig().getString("Super_Saiyan_3.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_3.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_3.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
									getConfig().getInt("Super_Saiyan_3.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 800,
									getConfig().getInt("Super_Saiyan_3.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Super_Saiyan_3.Traits.HEALTH_BOOST")));
						}
						if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_3.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Super_Saiyan_3.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Saiyan_3.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
							}
							player.sendMessage(
									getConfig().getString("Super_Saiyan_3.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Lightning_Effect")) {
							player.getWorld().strikeLightning(player.getLocation());
						}
						if (getConfig().getBoolean("Explosion_Effect")) {
							player.getWorld().createExplosion(player.getLocation(),
									getConfig().getInt("Explosion_Radious"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						player.performCommand("pp effect flame");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("4")) && (player.hasPermission("ssj.4"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_4.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ": "
									+ getConfig().getString("Super_Saiyan_4.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_4.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_4.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Super_Saiyan_4.Broadcast")) {
							player.sendMessage(getConfig().getString("Super_Saiyan_4.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_4.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_4.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
									getConfig().getInt("Super_Saiyan_4.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 900,
									getConfig().getInt("Super_Saiyan_4.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Super_Saiyan_4.Traits.HEALTH_BOOST")));
						}
						if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_4.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Super_Saiyan_4.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Saiyan_4.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
							}
							player.sendMessage(
									getConfig().getString("Super_Saiyan_4.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Lightning_Effect")) {
							player.getWorld().strikeLightning(player.getLocation());
						}
						if (getConfig().getBoolean("Explosion_Effect")) {
							player.getWorld().createExplosion(player.getLocation(),
									getConfig().getInt("Explosion_Radious"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						player.performCommand("pp effect lava");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("5")) && (player.hasPermission("ssj.5"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_5.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ": "
									+ getConfig().getString("Super_Saiyan_5.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_5.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_5.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Super_Saiyan_5.Broadcast")) {
							player.sendMessage(getConfig().getString("Super_Saiyan_5.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_5.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_5.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
									getConfig().getInt("Super_Saiyan_5.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000,
									getConfig().getInt("Super_Saiyan_5.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Super_Saiyan_5.Traits.HEALTH_BOOST")));
						}
						if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_5.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Super_Saiyan_5.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Saiyan_5.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
							}
							player.sendMessage(
									getConfig().getString("Super_Saiyan_5.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Lightning_Effect")) {
							player.getWorld().strikeLightning(player.getLocation());
						}
						if (getConfig().getBoolean("Explosion_Effect")) {
							player.getWorld().createExplosion(player.getLocation(),
									getConfig().getInt("Explosion_Radious"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						player.performCommand("pp effect explode");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("rage")) && (player.hasPermission("ssj.rage"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_Rage.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ": "
									+ getConfig().getString("Super_Saiyan_Rage.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_Rage.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_Rage.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Super_Saiyan_Rage.Broadcast")) {
							player.sendMessage(getConfig().getString("Super_Saiyan_Rage.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_Rage.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_Rage.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
									getConfig().getInt("Super_Saiyan_Rage.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Super_Saiyan_Rage.Traits.HEALTH_BOOST")));
						}
						if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_Rage.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Super_Saiyan_Rage.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Saiyan_Rage.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
							}
							player.sendMessage(
									getConfig().getString("Super_Saiyan_Rage.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Lightning_Effect")) {
							player.getWorld().strikeLightning(player.getLocation());
						}
						if (getConfig().getBoolean("Explosion_Effect")) {
							player.getWorld().createExplosion(player.getLocation(),
									getConfig().getInt("Explosion_Radious"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						player.performCommand("pp effect magiccrit");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("god")) && (player.hasPermission("ssj.god"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_God.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Super_Saiyan_God.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_God.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Super_Saiyan_God.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Super_Saiyan_God.Broadcast")) {
							player.sendMessage(getConfig().getString("Super_Saiyan_God.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_God.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Super_Saiyan_God.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 60,
									getConfig().getInt("Super_Saiyan_God.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1100,
									getConfig().getInt("Super_Saiyan_God.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Super_Saiyan_God.Traits.HEALTH_BOOST")));
						}
						if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_God.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Super_Saiyan_God.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Saiyan_God.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
							}
							player.sendMessage(
									getConfig().getString("Super_Saiyan_God.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Lightning_Effect")) {
							player.getWorld().strikeLightning(player.getLocation());
						}
						if (getConfig().getBoolean("Explosion_Effect")) {
							player.getWorld().createExplosion(player.getLocation(),
									getConfig().getInt("Explosion_Radious"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						player.performCommand("pp effect reddust");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("reload")) && (player.hasPermission("ssj.reload"))) {
						saveDefaultConfig();
						reloadConfig();
						player.sendMessage(this.Prefix + ChatColor.GREEN + "Successfully reloaded config!");
					} else if (!player.hasPermission("ssj.reload")) {
						player.sendMessage(
								this.Prefix + ChatColor.RED + "You do not have permission to execute this command!");
					} else if (args[0].equalsIgnoreCase("list")) {
						player.sendMessage(ChatColor.RED + "List of ssj transformations:");
						player.sendMessage(ChatColor.GREEN + "[command] [ID] [Transformation type] [Boost]");
						player.sendMessage(ChatColor.RED + "----------------------------------------------");
						player.sendMessage(ChatColor.BLUE + "[ssj] [normal] [Base Form] [null]");
						player.sendMessage(ChatColor.RED + "----------------------------------------------");
						if (player.hasPermission("ssj.x1")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x1] [Kaioken transformation] [+100]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x1.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x1.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x2")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x2] [Kaioken transformation] [+200]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x2.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x2.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x3")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x3] [Kaioken transformation] [+300]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x3.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x3.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x4")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x4] [Kaioken transformation] [+400]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x4.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x4.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x20")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x20] [Kaioken transformation] [+2000]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x20.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x20.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x30")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x30] [Kaioken transformation] [+3000]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x30.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x30.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x40")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x40] [Kaioken transformation] [+4000]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x40.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x40.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x50")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x50] [Kaioken transformation] [+5000]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x50.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x50.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.x100")) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] [x100] [Kaioken transformation] [+10000]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_x100.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_x100.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.superkaioken")) {
							player.sendMessage(ChatColor.BLUE
									+ "[kaioken] [super kaioken] [Kaioken & Super Saiyan Transformation] [x60]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Kaioken.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Kaioken.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.ssbx10")) {
							player.sendMessage(ChatColor.BLUE
									+ "[kaioken] [ssb x10] [Kaioken & Super Saiyan Blue Transformation] [x360]");
							player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
									+ ChatColor.GOLD + getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.DamageOdds")
									+ ChatColor.RED + " %");
							player.sendMessage(
									ChatColor.RED + "Damage taken if transformed incorrectly: " + ChatColor.GOLD
											+ getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.potential")) {
							player.sendMessage(ChatColor.BLUE + "[potential] [unleashed] [Potential unleashed] [x140]");
							player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
									+ ChatColor.GOLD + getConfig().getInt("Potential_Unleashed.DamageOdds")
									+ ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Potential_Unleashed.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.false")) {
							player.sendMessage(ChatColor.BLUE + "[ssj] [false] [False Super Saiyan] [x40]");
							player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
									+ ChatColor.GOLD + getConfig().getInt("False_Super_Saiyan.DamageOdds")
									+ ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("False_Super_Saiyan.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.1")) {
							player.sendMessage(ChatColor.BLUE + "[ssj] [1] [Super Saiyan 1] [x50]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Saiyan_1.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_1.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.ultimate")) {
							player.sendMessage(ChatColor.BLUE + "[ssj] [Ultimate] [Super Saiyan 1 Ultimate] [x55]");
							player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
									+ ChatColor.GOLD + getConfig().getInt("Ultimate_Super_Saiyan.DamageOdds")
									+ ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Ultimate_Super_Saiyan.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.legendary")) {
							player.sendMessage(ChatColor.BLUE + "[legendary] [ssj] [Legendary Super Saiyan] [x70]");
							player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
									+ ChatColor.GOLD + getConfig().getInt("Legendary_Super_Saiyan_1.DamageOdds")
									+ ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Legendary_Super_Saiyan_1.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.2")) {
							player.sendMessage(ChatColor.BLUE + "[ssj] [2] [Super Saiyan 2] [x100]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Saiyan_2.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_2.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.3")) {
							player.sendMessage(ChatColor.BLUE + "[3] [Super Saiyan 3] [x150]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Saiyan_3.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_3.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.4")) {
							player.sendMessage(ChatColor.BLUE + "[4] [Super Saiyan 4] [x200]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Saiyan_4.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_4.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.5")) {
							player.sendMessage(ChatColor.BLUE + "[5] [Super Saiyan 5] [x250]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Saiyan_5.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_5.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.rage")) {
							player.sendMessage(ChatColor.BLUE + "[rage] [Super Saiyan Rage] [x275]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Saiyan_Rage.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_Rage.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.god")) {
							player.sendMessage(ChatColor.BLUE + "[ssj] [god] [Super Saiyan God] [x300]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt("Super_Saiyan_God.DamageOdds") + ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_God.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.ssb")) {
							player.sendMessage(ChatColor.BLUE + "[ssb] [1] [Super Saiyan Blue] [x350]");
							player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_Blue_1.DamageOdds")
									+ ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_Blue_1.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
						if (player.hasPermission("ssj.ssr")) {
							player.sendMessage(ChatColor.BLUE + "[ssr] [1] [Super Saiyan Rose] [x350]");
							player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_Rose_1.DamageOdds")
									+ ChatColor.RED + " %");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt("Super_Saiyan_Rose_1.DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						player.sendMessage(ChatColor.RED + "Super Saiyan Transformations info:");
						player.sendMessage(
								ChatColor.BOLD + "Current version: " + ChatColor.RED + getDescription().getVersion());
						player.sendMessage(ChatColor.GREEN + "Plugin Developed by: " + ChatColor.RED
								+ getDescription().getAuthors());
						player.sendMessage(
								ChatColor.GOLD + "If you have any questions about this plugin, here's a link!");
						player.sendMessage(ChatColor.RED + "http://bit.ly/2avikLF");
					}
				}
			}
			if (cmd.getName().equalsIgnoreCase("base")) {
				if (args.length == 0) {
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Usage: /base form");
				} else if (args[0].equalsIgnoreCase("form")) {
					if (this.cooldownTime.containsKey(player)) {
						player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
								+ this.cooldownTime.get(player) + " seconds.");
						return true;
					}
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					if (getConfig().getBoolean("Base_Form.Broadcast")) {
						Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
								+ getConfig().getString("Base_Form.PrefixQuote").replace("&", "§"));
						Bukkit.broadcastMessage(getConfig().getString("Base_Form.MiddleQuote").replace("&", "§"));
						Bukkit.broadcastMessage(getConfig().getString("Base_Form.SuffixQuote").replace("&", "§"));
					} else if (!getConfig().getBoolean("Base_Form.Broadcast")) {
						player.sendMessage(getConfig().getString("Base_Form.PrefixQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Base_Form.MiddleQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Base_Form.SuffixQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Sound_Effect")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
					}
					player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
							+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
					player.performCommand("pp effect none");
					this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
					this.cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Main.this.cooldownTime.put(player,
									Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
							if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
								Main.this.cooldownTime.remove(player);
								Main.this.cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
					return true;
				}
			}
			if ((cmd.getName().equalsIgnoreCase("false")) && (player.hasPermission("ssj.false"))) {
				if (args.length == 0) {
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct usage: /false ssj");
				} else if ((args[0].equalsIgnoreCase("ssj")) && (player.hasPermission("ssj.false"))) {
					if (this.cooldownTime.containsKey(player)) {
						player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
								+ this.cooldownTime.get(player) + " seconds.");
						return true;
					}
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					if (getConfig().getBoolean("False_Super_Saiyan.Broadcast")) {
						Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
								+ getConfig().getString("False_Super_Saiyan.PrefixQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("False_Super_Saiyan.MiddleQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("False_Super_Saiyan.SuffixQuote").replace("&", "§"));
					} else if (!getConfig().getBoolean("False_Super_Saiyan.Broadcast")) {
						player.sendMessage(getConfig().getString("False_Super_Saiyan.PrefixQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("False_Super_Saiyan.MiddleQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("False_Super_Saiyan.SuffixQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.DAMAGE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.DAMAGE_RESISTANCE")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.FAST_DIGGING")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.FAST_DIGGING")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.FIRE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.FIRE_RESISTANCE")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.HEAL")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 40,
								getConfig().getInt("False_Super_Saiyan.Traits.HEAL")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.INCREASE_DAMAGE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.INCREASE_DAMAGE")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.JUMP")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.JUMP")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.REGENERATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 400,
								getConfig().getInt("False_Super_Saiyan.Traits.REGENERATION")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.SATURATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.SATURATION")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.SPEED")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.SPEED")));
					}
					if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.HEALTH_BOOST")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
								getConfig().getInt("False_Super_Saiyan.Traits.HEALTH_BOOST")));
					}
					if (this.rand.nextInt(100) <= getConfig().getInt("False_Super_Saiyan.DamageOdds")) {
						player.setLastDamage(getConfig().getInt("False_Super_Saiyan.DamageTaken"));
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("False_Super_Saiyan.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
						}
						player.sendMessage(
								getConfig().getString("False_Super_Saiyan.DamageTakenQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Lightning_Effect")) {
						player.getWorld().strikeLightning(player.getLocation());
					}
					if (getConfig().getBoolean("Explosion_Effect")) {
						player.getWorld().createExplosion(player.getLocation(),
								getConfig().getInt("Explosion_Radious"));
					}
					if (getConfig().getBoolean("Sound_Effect")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
					}
					player.performCommand("pp effect flame");
					player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
							+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
					this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
					this.cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Main.this.cooldownTime.put(player,
									Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
							if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
								Main.this.cooldownTime.remove(player);
								Main.this.cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
					return true;
				}
			}
			if ((cmd.getName().equalsIgnoreCase("ultimate")) && (player.hasPermission("ssj.untimate"))) {
				if (args.length == 0) {
					player.sendMessage(ChatColor.RED + "Correct usage: /ultimate ssj");
				} else if ((args[0].equalsIgnoreCase("ssj")) && (player.hasPermission("ssj.ultimate"))) {
					if (this.cooldownTime.containsKey(player)) {
						player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
								+ this.cooldownTime.get(player) + " seconds.");
						return true;
					}
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Broadcast")) {
						Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
								+ getConfig().getString("Ultimate_Super_Saiyan.PrefixQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Ultimate_Super_Saiyan.MiddleQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Ultimate_Super_Saiyan.SuffixQuote").replace("&", "§"));
					} else if (!getConfig().getBoolean("Ultimate_Super_Saiyan.Broadcast")) {
						player.sendMessage(
								getConfig().getString("Ultimate_Super_Saiyan.PrefixQuote").replace("&", "§"));
						player.sendMessage(
								getConfig().getString("Ultimate_Super_Saiyan.MiddleQuote").replace("&", "§"));
						player.sendMessage(
								getConfig().getString("Ultimate_Super_Saiyan.SuffixQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.DAMAGE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.DAMAGE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.FAST_DIGGING")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.FAST_DIGGING")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.FIRE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.FIRE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.HEAL")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.HEAL")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.INCREASE_DAMAGE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.INCREASE_DAMAGE")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.JUMP")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.JUMP")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.REGENERATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.REGENERATION")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.SATURATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.SATURATION")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.SPEED")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.SPEED")));
					}
					if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.HEALTH_BOOST")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
								getConfig().getInt("Ultimate_Super_Saiyan.Traits.HEALTH_BOOST")));
					}
					if (this.rand.nextInt(100) <= getConfig().getInt("Ultimate_Super_Saiyan.DamageOdds")) {
						player.setLastDamage(getConfig().getInt("Ultimate_Super_Saiyan.DamageTaken"));
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Ultimate_Super_Saiyan.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
						}
						player.sendMessage(
								getConfig().getString("Ultimate_Super_Saiyan.DamageTakenQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Lightning_Effect")) {
						player.getWorld().strikeLightning(player.getLocation());
					}
					if (getConfig().getBoolean("Explosion_Effect")) {
						player.getWorld().createExplosion(player.getLocation(),
								getConfig().getInt("Explosion_Radious"));
					}
					if (getConfig().getBoolean("Sound_Effect")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
					}
					player.performCommand("pp effect flame");
					player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
							+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
					this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
					this.cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Main.this.cooldownTime.put(player,
									Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
							if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
								Main.this.cooldownTime.remove(player);
								Main.this.cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
					return true;
				}
			}
			if ((cmd.getName().equalsIgnoreCase("legendary")) && (player.hasPermission("ssj.legendary"))) {
				if (args.length == 0) {
					player.sendMessage(ChatColor.RED + "Correct usage: /legendary ssj");
				} else if ((args[0].equalsIgnoreCase("ssj")) && (player.hasPermission("ssj.legendary"))) {
					if (this.cooldownTime.containsKey(player)) {
						player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
								+ this.cooldownTime.get(player) + " seconds.");
						return true;
					}
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Broadcast")) {
						Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
								+ getConfig().getString("Legendary_Super_Saiyan_1.PrefixQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Legendary_Super_Saiyan_1.MiddleQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Legendary_Super_Saiyan_1.SuffixQuote").replace("&", "§"));
					} else if (!getConfig().getBoolean("Legendary_Super_Saiyan_1.Broadcast")) {
						player.sendMessage(
								getConfig().getString("Legendary_Super_Saiyan_1.PrefixQuote").replace("&", "§"));
						player.sendMessage(
								getConfig().getString("Legendary_Super_Saiyan_1.MiddleQuote").replace("&", "§"));
						player.sendMessage(
								getConfig().getString("Legendary_Super_Saiyan_1.SuffixQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.DAMAGE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.DAMAGE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.FAST_DIGGING")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.FAST_DIGGING")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.FIRE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.FIRE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.HEAL")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 60,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.HEAL")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.INCREASE_DAMAGE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.INCREASE_DAMAGE")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.JUMP")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.JUMP")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.REGENERATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 770,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.REGENERATION")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.SATURATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.SATURATION")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.SPEED")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.SPEED")));
					}
					if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.HEALTH_BOOST")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
								getConfig().getInt("Legendary_Super_Saiyan_1.Traits.HEALTH_BOOST")));
					}
					if (this.rand.nextInt(100) <= getConfig().getInt("Legendary_Super_Saiyan_1.DamageOdds")) {
						player.setLastDamage(getConfig().getInt("Legendary_Super_Saiyan_1.DamageTaken"));
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Legendary_Super_Saiyan_1.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
						}
						player.sendMessage(
								getConfig().getString("Legendary_Super_Saiyan_1.DamageTakenQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Lightning_Effect")) {
						player.getWorld().strikeLightning(player.getLocation());
					}
					if (getConfig().getBoolean("Explosion_Effect")) {
						player.getWorld().createExplosion(player.getLocation(),
								getConfig().getInt("Explosion_Radious"));
					}
					if (getConfig().getBoolean("Sound_Effect")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
					}
					player.performCommand("pp effect happ effectyvillager");
					player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
							+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
					this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
					this.cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Main.this.cooldownTime.put(player,
									Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
							if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
								Main.this.cooldownTime.remove(player);
								Main.this.cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
					return true;
				}
			}

			if ((cmd.getName().equalsIgnoreCase("ssr")) && (player.hasPermission("ssj.ssr"))) {

				if (args.length == 0) {
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssr [ID]");
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssj list");
				} else if ((args[0].equalsIgnoreCase("1")) && (player.hasPermission("ssj.ssr"))) {
					if (this.cooldownTime.containsKey(player)) {
						player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
								+ this.cooldownTime.get(player) + " seconds.");
						return true;
					}
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Broadcast")) {
						Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
								+ getConfig().getString("Super_Saiyan_Rose_1.PrefixQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Super_Saiyan_Rose_1.MiddleQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Super_Saiyan_Rose_1.SuffixQuote").replace("&", "§"));
					} else if (!getConfig().getBoolean("Super_Saiyan_Rose_1.Broadcast")) {
						player.sendMessage(getConfig().getString("Super_Saiyan_Rose_1.PrefixQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Super_Saiyan_Rose_1.MiddleQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Super_Saiyan_Rose_1.SuffixQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.DAMAGE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.DAMAGE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.FAST_DIGGING")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.FAST_DIGGING")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.FIRE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.FIRE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.HEAL")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 100,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.HEAL")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.INCREASE_DAMAGE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.INCREASE_DAMAGE")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.JUMP")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.JUMP")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.REGENERATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.REGENERATION")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.SATURATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.SATURATION")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.SPEED")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.SPEED")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.HEALTH_BOOST")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
								getConfig().getInt("Super_Saiyan_Rose_1.Traits.HEALTH_BOOST")));
					}
					if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_Rose_1.DamageOdds")) {
						player.setLastDamage(getConfig().getInt("Super_Saiyan_Rose_1.DamageTaken"));
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_Rose_1.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
						}
						player.sendMessage(
								getConfig().getString("Super_Saiyan_Rose_1.DamageTakenQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Lightning_Effect")) {
						player.getWorld().strikeLightning(player.getLocation());
					}
					if (getConfig().getBoolean("Explosion_Effect")) {
						player.getWorld().createExplosion(player.getLocation(),
								getConfig().getInt("Explosion_Radious"));
					}
					if (getConfig().getBoolean("Sound_Effect")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
					}
					player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
							+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
					player.performCommand("pp effect dragonbreath");
					this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
					this.cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Main.this.cooldownTime.put(player,
									Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
							if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
								Main.this.cooldownTime.remove(player);
								Main.this.cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
					return true;
				}
			}

			if ((cmd.getName().equalsIgnoreCase("ssb")) && (player.hasPermission("ssj.ssb"))) {
				if (args.length == 0) {
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssg [ID]");
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssj list");
				} else if ((args[0].equalsIgnoreCase("1")) && (player.hasPermission("ssj.ssb"))) {
					if (this.cooldownTime.containsKey(player)) {
						player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
								+ this.cooldownTime.get(player) + " seconds.");
						return true;
					}
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Broadcast")) {
						Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
								+ getConfig().getString("Super_Saiyan_Blue_1.PrefixQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Super_Saiyan_Blue_1.MiddleQuote").replace("&", "§"));
						Bukkit.broadcastMessage(
								getConfig().getString("Super_Saiyan_Blue_1.SuffixQuote").replace("&", "§"));
					} else if (!getConfig().getBoolean("Super_Saiyan_Blue_1.Broadcast")) {
						player.sendMessage(getConfig().getString("Super_Saiyan_Blue_1.PrefixQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Super_Saiyan_Blue_1.MiddleQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Super_Saiyan_Blue_1.SuffixQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.DAMAGE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.DAMAGE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.FAST_DIGGING")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.FAST_DIGGING")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.FIRE_RESISTANCE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.FIRE_RESISTANCE")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.HEAL")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 100,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.HEAL")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.INCREASE_DAMAGE")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.INCREASE_DAMAGE")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.JUMP")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.JUMP")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.REGENERATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.REGENERATION")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.SATURATION")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.SATURATION")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.SPEED")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.SPEED")));
					}
					if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.HEALTH_BOOST")) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
								getConfig().getInt("Super_Saiyan_Blue_1.Traits.HEALTH_BOOST")));
					}
					if (this.rand.nextInt(100) <= getConfig().getInt("Super_Saiyan_Blue_1.DamageOdds")) {
						player.setLastDamage(getConfig().getInt("Super_Saiyan_Blue_1.DamageTaken"));
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Super_Saiyan_Blue_1.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
						}
						player.sendMessage(
								getConfig().getString("Super_Saiyan_Blue_1.DamageTakenQuote").replace("&", "§"));
					}
					if (getConfig().getBoolean("Lightning_Effect")) {
						player.getWorld().strikeLightning(player.getLocation());
					}
					if (getConfig().getBoolean("Explosion_Effect")) {
						player.getWorld().createExplosion(player.getLocation(),
								getConfig().getInt("Explosion_Radious"));
					}
					if (getConfig().getBoolean("Sound_Effect")) {
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
					}
					player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
							+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
					player.performCommand("pp effect instantspell");
					this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
					this.cooldownTask.put(player, new BukkitRunnable() {
						public void run() {
							Main.this.cooldownTime.put(player,
									Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
							if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
								Main.this.cooldownTime.remove(player);
								Main.this.cooldownTask.remove(player);
								cancel();
							}
						}
					});
					((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
					return true;
				}
			}
			if (cmd.getName().equalsIgnoreCase("kaioken")) {
				if (args.length == 0) {
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /kaioken x[ID]");
					player.sendMessage(this.Prefix + ChatColor.RED + "Correct Ussage: /ssj list");
				} else {
					if ((args[0].equalsIgnoreCase("x1")) && (player.hasPermission("ssj.x1"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x1.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x1.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x1.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x1.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x1.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x1.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x1.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x1.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 1000000,
									getConfig().getInt("Kaioken_x1.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x1.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x1.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x1.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x1.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x1.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x1.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x2")) && (player.hasPermission("ssj.x2"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x2.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x2.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x2.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x2.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x2.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x2.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x2.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x2.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1000000,
									getConfig().getInt("Kaioken_x2.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000,
									getConfig().getInt("Kaioken_x2.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x2.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x2.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x2.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x2.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x2.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x2.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x2.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x3")) && (player.hasPermission("ssj.x3"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x3.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x3.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x3.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x3.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x3.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x3.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x3.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x3.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 10000000,
									getConfig().getInt("Kaioken_x3.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000000,
									getConfig().getInt("Kaioken_x3.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x3.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x3.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x3.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x3.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x3.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x3.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x3.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x4")) && (player.hasPermission("ssj.x4"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x4.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x4.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x4.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x4.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x4.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x4.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x4.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x4.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000000,
									getConfig().getInt("Kaioken_x4.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x4.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x4.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x4.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x4.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x4.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x4.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x4.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x20")) && (player.hasPermission("ssj.x20"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x20.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x20.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x20.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x20.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x20.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x20.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x20.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x20.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 10000000,
									getConfig().getInt("Kaioken_x20.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000,
									getConfig().getInt("Kaioken_x20.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x20.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x20.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x20.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x20.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x20.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x20.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x20.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x30")) && (player.hasPermission("ssj.x30"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x30.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x30.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x30.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x30.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x30.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x30.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x30.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x30.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1000000,
									getConfig().getInt("Kaioken_x30.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000,
									getConfig().getInt("Kaioken_x30.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x30.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x30.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x30.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x30.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x30.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x30.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x30.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x40")) && (player.hasPermission("ssj.x40"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x40.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x40.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x40.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x40.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x40.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x40.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x40.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x40.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 10000000,
									getConfig().getInt("Kaioken_x40.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x40.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x40.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x40.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x40.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x40.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x40.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x40.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x50")) && (player.hasPermission("ssj.x50"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x50.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x50.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x50.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(getConfig().getString("Kaioken_x50.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x50.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x50.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x50.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x50.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1000000,
									getConfig().getInt("Kaioken_x50.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000000,
									getConfig().getInt("Kaioken_x50.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x50.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x50.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x50.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x50.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x50.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x50.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(getConfig().getString("Kaioken_x50.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("x100")) && (player.hasPermission("ssj.x100"))) {
						if (this.cooldownTime.containsKey(player)) {
							player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
									+ this.cooldownTime.get(player) + " seconds.");
							return true;
						}
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.FAST_DIGGING);
						player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
						player.removePotionEffect(PotionEffectType.HEAL);
						player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
						player.removePotionEffect(PotionEffectType.JUMP);
						player.removePotionEffect(PotionEffectType.REGENERATION);
						player.removePotionEffect(PotionEffectType.SATURATION);
						player.removePotionEffect(PotionEffectType.SPEED);
						player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
						if (getConfig().getBoolean("Kaioken_x100.Broadcast")) {
							Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
									+ getConfig().getString("Kaioken_x100.PrefixQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Kaioken_x100.MiddleQuote").replace("&", "§"));
							Bukkit.broadcastMessage(
									getConfig().getString("Kaioken_x100.SuffixQuote").replace("&", "§"));
						} else if (!getConfig().getBoolean("Kaioken_x100.Broadcast")) {
							player.sendMessage(getConfig().getString("Kaioken_x100.PrefixQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x100.MiddleQuote").replace("&", "§"));
							player.sendMessage(getConfig().getString("Kaioken_x100.SuffixQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.DAMAGE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.DAMAGE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.FAST_DIGGING")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.FAST_DIGGING")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.FIRE_RESISTANCE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.FIRE_RESISTANCE")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.HEAL")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.HEAL")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.INCREASE_DAMAGE")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.INCREASE_DAMAGE")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.JUMP")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.JUMP")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.REGENERATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000000,
									getConfig().getInt("Kaioken_x100.Traits.REGENERATION")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.SATURATION")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.SATURATION")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.SPEED")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.SPEED")));
						}
						if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.HEALTH_BOOST")) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
									getConfig().getInt("Kaioken_x100.Traits.HEALTH_BOOST")));
						}
						getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							public void run() {
								player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (Main.this.getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}
								player.performCommand("pp effect none");
							}
						}, getConfig().getInt("Kaioken_x100.Kaioken_Cooldown") * 20L);
						if (this.rand.nextInt(100) <= getConfig().getInt("Kaioken_x100.DamageOdds")) {
							player.setLastDamage(getConfig().getInt("Kaioken_x100.DamageTaken"));
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_x100.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
										(int) (getConfig().getInt("Kaioken_x100.Kaioken_Cooldown") * 20L), 0));
							}
							player.sendMessage(
									getConfig().getString("Kaioken_x100.DamageTakenQuote").replace("&", "§"));
						}
						if (getConfig().getBoolean("Sound_Effect")) {
							player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
						}
						player.performCommand("pp effect reddust");
						player.sendMessage(this.Prefix + ChatColor.RED
								+ "The kaioken attack is vary limited in time, use it well!");
						player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
								+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
						this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
						this.cooldownTask.put(player, new BukkitRunnable() {
							public void run() {
								Main.this.cooldownTime.put(player,
										Integer.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
								if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
									Main.this.cooldownTime.remove(player);
									Main.this.cooldownTask.remove(player);
									cancel();
								}
							}
						});
						((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
						return true;
					}
					if ((args[0].equalsIgnoreCase("super")) && (player.hasPermission("ssj.superkaioken"))) {
						if (args.length == 1) {
							player.sendMessage(this.Prefix + ChatColor.RED + "Correct Usage: /kaioken super kaioken");
						} else if ((args[1].equalsIgnoreCase("kaioken"))
								&& (player.hasPermission("ssj.superkaioken"))) {
							if (this.cooldownTime.containsKey(player)) {
								player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
										+ this.cooldownTime.get(player) + " seconds.");
								return true;
							}
							player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							player.removePotionEffect(PotionEffectType.JUMP);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.SATURATION);
							player.removePotionEffect(PotionEffectType.SPEED);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Super_Kaioken.Broadcast")) {
								Bukkit.broadcastMessage(player.getDisplayName() + ChatColor.WHITE + ": "
										+ getConfig().getString("Super_Kaioken.PrefixQuote").replace("&", "§"));
								Bukkit.broadcastMessage(
										getConfig().getString("Super_Kaioken.MiddleQuote").replace("&", "§"));
								Bukkit.broadcastMessage(
										getConfig().getString("Super_Kaioken.SuffixQuote").replace("&", "§"));
							} else if (!getConfig().getBoolean("Super_Kaioken.Broadcast")) {
								player.sendMessage(
										getConfig().getString("Super_Kaioken.PrefixQuote").replace("&", "§"));
								player.sendMessage(
										getConfig().getString("Super_Kaioken.MiddleQuote").replace("&", "§"));
								player.sendMessage(
										getConfig().getString("Super_Kaioken.SuffixQuote").replace("&", "§"));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.DAMAGE_RESISTANCE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.DAMAGE_RESISTANCE")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.FAST_DIGGING")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.FAST_DIGGING")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.FIRE_RESISTANCE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.FIRE_RESISTANCE")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.HEAL")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
										getConfig().getInt("Super_Kaioken.Traits.HEAL")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.INCREASE_DAMAGE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.INCREASE_DAMAGE")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.JUMP")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.JUMP")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.REGENERATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 700,
										getConfig().getInt("Super_Kaioken.Traits.REGENERATION")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.SATURATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.SATURATION")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.SPEED")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.SPEED")));
							}
							if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
										getConfig().getInt("Super_Kaioken.Traits.HEALTH_BOOST")));
							}
							getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
								public void run() {
									player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.FAST_DIGGING);
									player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.HEAL);
									player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
									player.removePotionEffect(PotionEffectType.JUMP);
									player.removePotionEffect(PotionEffectType.REGENERATION);
									player.removePotionEffect(PotionEffectType.SATURATION);
									player.removePotionEffect(PotionEffectType.SPEED);
									player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
									if (Main.this.getConfig().getBoolean("Sound_Effect")) {
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT,
												1.0F, 2.0F);
									}
									player.performCommand("pp effect none");
								}
							}, getConfig().getInt("Super_Kaioken.Kaioken_Cooldown") * 20L);
							if (this.rand.nextInt(100) <= getConfig().getInt("Super_Kaioken.DamageOdds")) {
								player.setLastDamage(getConfig().getInt("Super_Kaioken.DamageTaken"));
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (getConfig().getBoolean("Super_Kaioken.Use_Traits?.HEALTH_BOOST")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
											(int) (getConfig().getInt("Super_Kaioken.Kaioken_Cooldown") * 20L), 0));
								}
								player.sendMessage(
										getConfig().getString("Super_Kaioken.DamageTakenQuote").replace("&", "§"));
							}
							if (getConfig().getBoolean("Lightning_Effect")) {
								player.getWorld().strikeLightning(player.getLocation());
							}
							if (getConfig().getBoolean("Explosion_Effect")) {
								player.getWorld().createExplosion(player.getLocation(),
										getConfig().getInt("Explosion_Radious"));
							}
							if (getConfig().getBoolean("Sound_Effect")) {
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
							}
							player.sendMessage(this.Prefix + ChatColor.RED
									+ "The kaioken attack is vary limited in time, use it well!");
							player.performCommand("pp effect flame");
							player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
									+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
							this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
							this.cooldownTask.put(player, new BukkitRunnable() {
								public void run() {
									Main.this.cooldownTime.put(player, Integer
											.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
									if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
										Main.this.cooldownTime.remove(player);
										Main.this.cooldownTask.remove(player);
										cancel();
									}
								}
							});
							((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
							return true;
						}
					} else if ((args[0].equalsIgnoreCase("ssb")) && (player.hasPermission("ssj.ssbx10"))) {
						if (args.length == 1) {
							player.sendMessage(this.Prefix + ChatColor.RED + "Correct Usage: /kaioken ssb x10");
						} else if ((args[1].equalsIgnoreCase("x10")) && (player.hasPermission("ssj.ssbx10"))) {
							if (this.cooldownTime.containsKey(player)) {
								player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
										+ this.cooldownTime.get(player) + " seconds.");
								return true;
							}
							player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							player.removePotionEffect(PotionEffectType.JUMP);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.SATURATION);
							player.removePotionEffect(PotionEffectType.SPEED);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Broadcast")) {
								Bukkit.broadcastMessage(player.getDisplayName() + ": " + getConfig()
										.getString("Kaioken_Super_Saiyan_Blue_1_x10.PrefixQuote").replace("&", "§"));
								Bukkit.broadcastMessage(getConfig()
										.getString("Kaioken_Super_Saiyan_Blue_1_x10.MiddleQuote").replace("&", "§"));
								Bukkit.broadcastMessage(getConfig()
										.getString("Kaioken_Super_Saiyan_Blue_1_x10.SuffixQuote").replace("&", "§"));
							} else if (!getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Broadcast")) {
								player.sendMessage(getConfig().getString("Kaioken_Super_Saiyan_Blue_1_x10.PrefixQuote")
										.replace("&", "§"));
								player.sendMessage(getConfig().getString("Kaioken_Super_Saiyan_Blue_1_x10.MiddleQuote")
										.replace("&", "§"));
								player.sendMessage(getConfig().getString("Kaioken_Super_Saiyan_Blue_1_x10.SuffixQuote")
										.replace("&", "§"));
							}
							if (getConfig()
									.getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.DAMAGE_RESISTANCE")) {
								player.addPotionEffect(
										new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000, getConfig()
												.getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.DAMAGE_RESISTANCE")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.FAST_DIGGING")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.FAST_DIGGING")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.FIRE_RESISTANCE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.FIRE_RESISTANCE")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.HEAL")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 10000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.HEAL")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.INCREASE_DAMAGE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.INCREASE_DAMAGE")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.JUMP")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.JUMP")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.REGENERATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.REGENERATION")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.SATURATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.SATURATION")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.SPEED")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.SPEED")));
							}
							if (getConfig().getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
										getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Traits.HEALTH_BOOST")));
							}
							getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
								public void run() {
									player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.FAST_DIGGING);
									player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.HEAL);
									player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
									player.removePotionEffect(PotionEffectType.JUMP);
									player.removePotionEffect(PotionEffectType.REGENERATION);
									player.removePotionEffect(PotionEffectType.SATURATION);
									player.removePotionEffect(PotionEffectType.SPEED);
									player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
									if (Main.this.getConfig().getBoolean("Sound_Effect")) {
										player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT,
												1.0F, 2.0F);
									}
									player.performCommand("pp effect none");
								}
							}, getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.Kaioken_Cooldown") * 20L);
							if (this.rand.nextInt(100) <= getConfig()
									.getInt("Kaioken_Super_Saiyan_Blue_1_x10.DamageOdds")) {
								player.setLastDamage(getConfig().getInt("Kaioken_Super_Saiyan_Blue_1_x10.DamageTaken"));
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (getConfig()
										.getBoolean("Kaioken_Super_Saiyan_Blue_1_x10.Use_Traits?.HEALTH_BOOST")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,
											(int) (getConfig()
													.getInt("Kaioken_Super_Saiyan_Blue_1_x10.Kaioken_Cooldown") * 20L),
											0));
								}
								player.sendMessage(
										getConfig().getString("Kaioken_Super_Saiyan_Blue_1_x10.DamageTakenQuote")
												.replace("&", "§"));
							}
							if (getConfig().getBoolean("Lightning_Effect")) {
								player.getWorld().strikeLightning(player.getLocation());
							}
							if (getConfig().getBoolean("Explosion_Effect")) {
								player.getWorld().createExplosion(player.getLocation(),
										getConfig().getInt("Explosion_Radious"));
							}
							if (getConfig().getBoolean("Sound_Effect")) {
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 2.0F);
							}
							player.performCommand("pp effect instantspell");
							player.sendMessage(this.Prefix + ChatColor.RED
									+ "The kaioken attack is vary limited in time, use it well!");
							player.sendMessage(this.Prefix + ChatColor.GREEN + "You're now on a " + ChatColor.RED
									+ this.Cooldown + ChatColor.GREEN + " second cooldown!");
							this.cooldownTime.put(player, Integer.valueOf(this.Cooldown));
							this.cooldownTask.put(player, new BukkitRunnable() {
								public void run() {
									Main.this.cooldownTime.put(player, Integer
											.valueOf(((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
									if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {
										Main.this.cooldownTime.remove(player);
										Main.this.cooldownTask.remove(player);
										cancel();
									}
								}
							});
							((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);
							return true;
						}
					}
				}
			}
		} else if ((args[0].equalsIgnoreCase("reload")) && (!(sender instanceof Player))) {
			saveDefaultConfig();
			reloadConfig();
			Bukkit.getLogger().info("[Super Saiyan PL]Successfully reloaded config!");
		}
		return false;
	}
}
