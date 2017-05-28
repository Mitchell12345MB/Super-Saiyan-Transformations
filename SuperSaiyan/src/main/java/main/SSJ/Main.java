package main.SSJ;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		getLogger().info("Super Saiyan Plugin has been DISABLED");
		saveDefaultConfig();
	}
	ArrayList<Player> cooldown = new ArrayList<Player>();
	int Cooldown = getConfig().getInt("Cooldown_Timer");
	String Prefix = getConfig().getString("Prefix").replace("&", "§");

	//A bunch of junk that was here previously and exists to handle what the player said
	//These comments as of v12 are only by Noorquacker. It's not hard to write two slashes and write a message...
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if ((sender instanceof Player)) {
			final Player player = (Player) sender;
			
			//Gets the list of SSJ transformations in config
			List<String> ssjList = getConfig().getStringList("Transformation_List.SuperSaiyans");
			List<String> kaiokenList = getConfig().getStringList("Transformation_List.Kaiokens");
			
			//Now we handle what the player said to us!
			//SSJ and Kaioken are pretty much identical, but Kaioken just has a timer.
			//At least it's more efficient than v11, with 3000+ lines of copypasta
			boolean cmdFound = false;
			if(args.length >= 1) {
			if((cmd.getName().equalsIgnoreCase("ssj"))) {
				//Special exception for /ssj base
				if(args[0].equalsIgnoreCase("base")) {
					cmdFound = true;
					player.removePotionEffect(PotionEffectType.FAST_DIGGING);
					player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.HEAL);
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.JUMP);
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.SATURATION);
					player.removePotionEffect(PotionEffectType.SPEED);
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.sendMessage(getConfig().getString("Base_Form.PrefixQuote").replace("&","§"));
					player.sendMessage(getConfig().getString("Base_Form.MiddleQuote").replace("&","§"));
					player.sendMessage(getConfig().getString("Base_Form.SuffixQuote").replace("&","§"));
				}
				//Loop through the list of known SSJ's to match
				for(int i = 0; i < ssjList.size(); i++) {
					String ssj = ssjList.get(i);
					if(args[0].equalsIgnoreCase(getConfig().getString(ssj + ".CommandName"))) {
						cmdFound = true;
						//If we're here, we have a match!
						//Check perms, we don't want everyone in the factions world going Super Saiyan God...
						if(player.hasPermission("ssj."+getConfig().getString(ssj + ".CommandName"))) {
							//Check if they must wait for more time
							if (this.cooldownTime.containsKey(player)) {
								player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for " + this.cooldownTime.get(player) + " seconds.");
								return true;
							}
							//Get a much happier name
							//String cmdlet = getConfig().getString(ssj + ".CommandName");
							//Remove potion effects...:(
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							player.removePotionEffect(PotionEffectType.JUMP);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.SATURATION);
							player.removePotionEffect(PotionEffectType.SPEED);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							//Send chat stuff
							if(getConfig().getBoolean(getConfig().getString(ssj + ".CommandName"))) {
								Bukkit.broadcastMessage(player.getDisplayName() + ": " + getConfig().getString(ssj+".PrefixQuote").replace("&", "§"));
								Bukkit.broadcastMessage(player.getDisplayName() + ": " + getConfig().getString(ssj+".MiddleQuote").replace("&", "§"));
								Bukkit.broadcastMessage(player.getDisplayName() + ": " + getConfig().getString(ssj+".SuffixQuote").replace("&", "§"));
							}
							else{
								player.sendMessage(getConfig().getString(ssj+".PrefixQuote").replace("&", "§"));
								player.sendMessage(getConfig().getString(ssj+".MiddleQuote").replace("&", "§"));
								player.sendMessage(getConfig().getString(ssj+".SuffixQuote").replace("&", "§"));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.DAMAGE_RESISTANCE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
										getConfig().getInt(ssj+".Traits.DAMAGE_RESISTANCE")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.FAST_DIGGING")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
										getConfig().getInt(ssj+".Traits.FAST_DIGGING")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.FIRE_RESISTANCE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
										getConfig().getInt(ssj+".Traits.FIRE_RESISTANCE")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.HEAL")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
										getConfig().getInt(ssj+".Traits.HEAL")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.INCREASE_DAMAGE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
										getConfig().getInt(ssj+".Traits.INCREASE_DAMAGE")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.JUMP")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
										getConfig().getInt(ssj+".Traits.JUMP")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.REGENERATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
										getConfig().getInt(ssj+".Traits.REGENERATION")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.SATURATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
										getConfig().getInt(ssj+".Traits.SATURATION")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.SPEED")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
										getConfig().getInt(ssj+".Traits.SPEED")));
							}
							if (getConfig().getBoolean(ssj+".Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
										getConfig().getInt(ssj+".Traits.HEALTH_BOOST")));
							}
							if (this.rand.nextInt(100) <= getConfig().getInt(ssj+".DamageOdds")) {
								player.setLastDamage(getConfig().getInt(ssj+".DamageTaken"));
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (getConfig().getBoolean(ssj+".Use_Traits?.HEALTH_BOOST")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
								}
								player.sendMessage(
										getConfig().getString(ssj+".DamageTakenQuote").replace("&", "§"));
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
							//Now do the PlayerParticles
							if(getServer().getPluginManager().isPluginEnabled("PlayerParticles")) {
								player.performCommand("pp effect " + getConfig().getString(ssj+".Particle"));
							}
							//Now do the timer stuff
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
							player.sendMessage(getConfig().getString("Prefix").replace("&", "§")+"You're now on a "+getConfig().getInt("Cooldown_Timer")+" second cooldown");
							return true;
						}
						else{player.sendMessage(ChatColor.DARK_RED+"You do not have permission to perform this command.");}
					}
				}
				//If some idiot makes /ssj list a transformation, let them go
				if(args[0].equalsIgnoreCase("list") && !cmdFound) {
					player.sendMessage(ChatColor.RED + "List of ssj transformations:");
					player.sendMessage(ChatColor.GREEN + "[Command] [ID] [Transformation type] [Boost]");
					player.sendMessage(ChatColor.RED + "----------------------------------------------");
					player.sendMessage(ChatColor.BLUE + "[ssj] [base] [Base Form] [null]");
					player.sendMessage(ChatColor.RED + "----------------------------------------------");
					for(int i = 0; i < ssjList.size(); i++) {
						String ssj = ssjList.get(i);
						if (player.hasPermission("ssj."+getConfig().getString(ssj+".CommandName"))) {
							player.sendMessage(ChatColor.BLUE + "[ssj] ["+getConfig().getString(ssj+".CommandName")+"] ["+getConfig().getString(ssj+".Desc")+"] ["+getConfig().getString(ssj+".DescBoost")+"]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt(ssj+".DamageOdds") + ChatColor.RED + "%");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt(ssj+".DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
					}
					//Yeah, I know, they're copied and pasted.
					//Better than V11.
					for(int i = 0; i < kaiokenList.size(); i++) {
						String ssj = kaiokenList.get(i);
						if (player.hasPermission("ssj."+getConfig().getString(ssj+".CommandName"))) {
							player.sendMessage(ChatColor.BLUE + "[kaioken] ["+getConfig().getString(ssj+".CommandName")+"] ["+getConfig().getString(ssj+".Desc")+"] ["+getConfig().getString(ssj+".DescBoost")+"]");
							player.sendMessage(
									ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
											+ getConfig().getInt(ssj+".DamageOdds") + ChatColor.RED + "%");
							player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
									+ ChatColor.GOLD + getConfig().getInt(ssj+".DamageTaken"));
							player.sendMessage(ChatColor.RED + "----------------------------------------------");
						}
					}
					cmdFound = true;
				}
				//If another idiot decides to add a new SSJ form runned by /ssj info, let the idiot go and disable normal /ssj info.
				if(args[0].equalsIgnoreCase("info") && !cmdFound) {
					player.sendMessage(ChatColor.RED + "Super Saiyan Transformations info:");
					player.sendMessage(ChatColor.BOLD + "Current version: " + ChatColor.RED + getDescription().getVersion());
					player.sendMessage(ChatColor.GREEN + "Plugin Developed by: " + ChatColor.RED + getDescription().getAuthors());
					player.sendMessage(ChatColor.GOLD + "If you have any questions about this plugin, here's a link!");
					player.sendMessage(ChatColor.RED + "http://bit.ly/2avikLF");
					cmdFound = true;
				}
			}
			else if(cmd.getName().equalsIgnoreCase("kaioken")) {
				for(int i = 0; i < kaiokenList.size(); i++) {
					String kaioken = kaiokenList.get(i);
					if(args[0].equalsIgnoreCase(getConfig().getString(kaioken + ".CommandName"))) {
						cmdFound = true;
						if(player.hasPermission("ssj."+getConfig().getString(kaioken + ".CommandName"))) {
							if (this.cooldownTime.containsKey(player)) {
								player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for " + this.cooldownTime.get(player) + " seconds.");
								return true;
							}
							//String cmdlet = getConfig().getString(kaioken + ".CommandName");
							player.removePotionEffect(PotionEffectType.FAST_DIGGING);
							player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
							player.removePotionEffect(PotionEffectType.HEAL);
							player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
							player.removePotionEffect(PotionEffectType.JUMP);
							player.removePotionEffect(PotionEffectType.REGENERATION);
							player.removePotionEffect(PotionEffectType.SATURATION);
							player.removePotionEffect(PotionEffectType.SPEED);
							player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
							if(getConfig().getBoolean(kaioken+".Broadcast")) {
								Bukkit.broadcastMessage(player.getDisplayName() + ": "+ getConfig().getString(kaioken+".PrefixQuote").replace("&", "§"));
								Bukkit.broadcastMessage(player.getDisplayName() + ": "+ getConfig().getString(kaioken+".MiddleQuote").replace("&", "§"));
								Bukkit.broadcastMessage(player.getDisplayName() + ": "+ getConfig().getString(kaioken+".SuffixQuote").replace("&", "§"));
							}
							else{
								player.sendMessage(getConfig().getString(kaioken+".PrefixQuote").replace("&", "§"));
								player.sendMessage(getConfig().getString(kaioken+".MiddleQuote").replace("&", "§"));
								player.sendMessage(getConfig().getString(kaioken+".SuffixQuote").replace("&", "§"));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.DAMAGE_RESISTANCE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100000000,
										getConfig().getInt(kaioken+".Traits.DAMAGE_RESISTANCE")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.FAST_DIGGING")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
										getConfig().getInt(kaioken+".Traits.FAST_DIGGING")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.FIRE_RESISTANCE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
										getConfig().getInt(kaioken+".Traits.FIRE_RESISTANCE")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.HEAL")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
										getConfig().getInt(kaioken+".Traits.HEAL")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.INCREASE_DAMAGE")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
										getConfig().getInt(kaioken+".Traits.INCREASE_DAMAGE")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.JUMP")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
										getConfig().getInt(kaioken+".Traits.JUMP")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.REGENERATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
										getConfig().getInt(kaioken+".Traits.REGENERATION")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.SATURATION")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
										getConfig().getInt(kaioken+".Traits.SATURATION")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.SPEED")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
										getConfig().getInt(kaioken+".Traits.SPEED")));
							}
							if (getConfig().getBoolean(kaioken+".Use_Traits?.HEALTH_BOOST")) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
										getConfig().getInt(kaioken+".Traits.HEALTH_BOOST")));
							}
							if (this.rand.nextInt(100) <= getConfig().getInt(kaioken+".DamageOdds")) {
								player.setLastDamage(getConfig().getInt(kaioken+".DamageTaken"));
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (getConfig().getBoolean(kaioken+".Use_Traits?.HEALTH_BOOST")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
								}
								player.sendMessage(
										getConfig().getString(kaioken+".DamageTakenQuote").replace("&", "§"));
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
							//Kaioken cooldown
							player.sendMessage(getConfig().getString("Prefix").replace("&", "§")+"You're now on a "+getConfig().getInt("Cooldown_Timer")+" second cooldown");
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
							}, getConfig().getInt(kaioken+".Kaioken_Cooldown") * 20L);
		
							return true;
						}
						else{player.sendMessage(ChatColor.DARK_RED+"You do not have permission to perform this command.");}
					}
				}
			}
			}
			if(!cmdFound || args.length==0) {
				player.sendMessage(ChatColor.RED+"Usage: /ssj [id]");
				player.sendMessage(ChatColor.RED+"Usage: /kaioken x[id]");
				player.sendMessage(ChatColor.RED+"Usage: /ssj list");
				player.sendMessage(ChatColor.RED+"Usage: /ssj info");
			}
		} else if ((args[0].equalsIgnoreCase("reload")) && (!(sender instanceof Player))) {
			saveDefaultConfig();
			reloadConfig();
			Bukkit.getLogger().info("[Super Saiyan PL] Successfully reloaded config!");
		}
		return false;
	}
}

//HA! Not even at 400 lines! Beat that Mitchell!
