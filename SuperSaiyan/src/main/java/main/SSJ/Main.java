package main.SSJ;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin implements Listener {
	private HashMap<Player, Integer> cooldownTime;

	private HashMap<Player, Integer> cooldownTimeK;

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
		pm.registerEvents(this, (Plugin) this);
		pm.registerEvents(new Listeners(this), (Plugin) this);
		getLogger().info("Super Saiyan Plugin has been ENABLED " + getDescription().getVersion());
		this.cooldownTime = new HashMap<>();
		this.cooldownTimeK = new HashMap<>();
		this.cooldownTask = new HashMap<>();
	}

	public void onDisable() {
		getLogger().info("Super Saiyan Plugin has been DISABLED");
		saveDefaultConfig();
	}

	List<String> ssjList = getConfig().getStringList("Transformation_List.SuperSaiyans");

	List<String> kaiokenList = getConfig().getStringList("Transformation_List.Kaiokens");

	ArrayList<Player> cooldown = new ArrayList<>();

	String Prefix = getConfig().getString("Prefix").replace("&", "§");

	private Map<CommandSender, BukkitTask> tasks = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			final int pcount = getConfig().getInt("Particle_Count");
			final Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1.0F);
			boolean cmdFound = false;
			if (args.length >= 1)
				if (cmd.getName().equalsIgnoreCase("ssj")) {
					if (args[0].equalsIgnoreCase("base")) {
						BukkitTask t = this.tasks.get(sender);
						cmdFound = true;
						if (t != null)
							t.cancel();
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
						player.sendMessage(getConfig().getString("Base_Form.PrefixQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Base_Form.MiddleQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Base_Form.SuffixQuote").replace("&", "§"));
					}
					int i;
					for (i = 0; i < this.ssjList.size(); i++) {
						final String ssj = this.ssjList.get(i);
						if (args[0].equalsIgnoreCase(getConfig().getString(String.valueOf(ssj) + ".CommandName"))) {
							cmdFound = true;
							if (player.hasPermission(
									"ssj." + getConfig().getString(String.valueOf(ssj) + ".CommandName"))) {
								if (this.cooldownTime.containsKey(player)) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.RED
											+ "You must wait for " + this.cooldownTime.get(player) + " seconds.");
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
								if (getConfig()
										.getBoolean(getConfig().getString(String.valueOf(ssj) + ".CommandName"))) {
									Bukkit.broadcastMessage(String.valueOf(player.getDisplayName()) + ": " + getConfig()
											.getString(String.valueOf(ssj) + ".PrefixQuote").replace("&", "§"));
									Bukkit.broadcastMessage(String.valueOf(player.getDisplayName()) + ": " + getConfig()
											.getString(String.valueOf(ssj) + ".MiddleQuote").replace("&", "§"));
									Bukkit.broadcastMessage(String.valueOf(player.getDisplayName()) + ": " + getConfig()
											.getString(String.valueOf(ssj) + ".SuffixQuote").replace("&", "§"));
								} else {
									player.sendMessage(getConfig().getString(String.valueOf(ssj) + ".PrefixQuote")
											.replace("&", "§"));
									player.sendMessage(getConfig().getString(String.valueOf(ssj) + ".MiddleQuote")
											.replace("&", "§"));
									player.sendMessage(getConfig().getString(String.valueOf(ssj) + ".SuffixQuote")
											.replace("&", "§"));
								}
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.DAMAGE_RESISTANCE"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
											100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.DAMAGE_RESISTANCE")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.FAST_DIGGING"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.FAST_DIGGING")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.FIRE_RESISTANCE"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.FIRE_RESISTANCE")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.HEAL"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.HEAL")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.INCREASE_DAMAGE"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.INCREASE_DAMAGE")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.JUMP"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.JUMP")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.REGENERATION"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.REGENERATION")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.SATURATION"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.SATURATION")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.SPEED"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.SPEED")));
								if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.HEALTH_BOOST"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
											getConfig().getInt(String.valueOf(ssj) + ".Traits.HEALTH_BOOST")));
								if (this.rand.nextInt(100) <= getConfig().getInt(String.valueOf(ssj) + ".DamageOdds")) {
									player.setLastDamage(getConfig().getInt(String.valueOf(ssj) + ".DamageTaken"));
									player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.HEAL);
									player.removePotionEffect(PotionEffectType.REGENERATION);
									player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
									if (getConfig().getBoolean(String.valueOf(ssj) + ".Use_Traits?.HEALTH_BOOST"))
										player.addPotionEffect(
												new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
									player.sendMessage(getConfig().getString(String.valueOf(ssj) + ".DamageTakenQuote")
											.replace("&", "§"));
								}
								if (getConfig().getBoolean("Lightning_Effect"))
									player.getWorld().strikeLightning(player.getLocation());
								if (getConfig().getBoolean("Explosion_Effect"))
									player.getWorld().createExplosion(player.getLocation(),
											getConfig().getInt("Explosion_Radious"));
								if (getConfig().getBoolean("Sound_Effect"))
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								BukkitTask t = this.tasks.put(sender, (new BukkitRunnable() {
									public void run() {
										Location location = player.getLocation();
										World world = player.getWorld();
										String checkSsj = Main.this.getConfig()
												.getString(String.valueOf(ssj) + ".Particle");
										if (checkSsj.contains("redstone")) {
											world.spawnParticle(Particle.REDSTONE, location, pcount, 0.3D, 0.9D, 0.3D,
													0.05D, redstone);
										} else {
											world.spawnParticle(Particle.valueOf(Main.this.getConfig()
													.getString(String.valueOf(ssj) + ".Particle").toUpperCase()),
													location.getX(), location.getY(), location.getZ(), pcount, 0.3D,
													0.9D, 0.3D, 0.05D);
										}
									}
								}).runTaskTimerAsynchronously((Plugin) this, 0L, 0L));
								if (t != null)
									t.cancel();
								this.cooldownTime.put(player,
										Integer.valueOf(getConfig().getInt(String.valueOf(ssj) + ".Cooldown")));
								this.cooldownTask.put(player, new BukkitRunnable() {
									public void run() {
										Main.this.cooldownTime.put(player, Integer.valueOf(
												((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
										if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0
												|| Main.this.getConfig().getInt(String.valueOf(ssj) + ".Cooldown") == 0
												|| player.isOp()) {
											Main.this.cooldownTime.remove(player);
											Main.this.cooldownTask.remove(player);
											cancel();
										}
									}
								});
								((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer((Plugin) this, 20L, 20L);
								if (player.isOp()) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.DARK_GREEN
											+ "You are OP, cooldown doesn't apply to you!");
								} else if (getConfig().getInt(String.valueOf(ssj) + ".Cooldown") == 0) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.DARK_GREEN
											+ "Cooldown for this transformation is set to 0.");
								} else if (((Integer) this.cooldownTime.get(player)).intValue() == 0) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.DARK_GREEN
											+ "Cooldown is finished!");
								} else {
									player.sendMessage(String.valueOf(this.Prefix) + "You're now on a "
											+ getConfig().getInt(String.valueOf(ssj) + ".Cooldown")
											+ " second cooldown");
								}
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
						for (i = 0; i < this.ssjList.size(); i++) {
							final String ssj = this.ssjList.get(i);
							if (player.hasPermission(
									"ssj." + getConfig().getString(String.valueOf(ssj) + ".CommandName"))) {
								player.sendMessage(ChatColor.BLUE + "[ssj] ["
										+ getConfig().getString(String.valueOf(ssj) + ".CommandName") + "] ["
										+ getConfig().getString(String.valueOf(ssj) + ".Desc") + "] ["
										+ getConfig().getString(String.valueOf(ssj) + ".DescBoost") + "]");
								player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
										+ ChatColor.GOLD + getConfig().getInt(String.valueOf(ssj) + ".DamageOdds")
										+ ChatColor.RED + "%");
								player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
										+ ChatColor.GOLD + getConfig().getInt(String.valueOf(ssj) + ".DamageTaken"));
								player.sendMessage(ChatColor.RED + "----------------------------------------------");
							}
						}
						for (i = 0; i < this.kaiokenList.size(); i++) {
							final String kaioken = this.kaiokenList.get(i);
							if (player.hasPermission(
									"ssj." + getConfig().getString(String.valueOf(kaioken) + ".CommandName"))) {
								player.sendMessage(ChatColor.BLUE + "[kaioken] ["
										+ getConfig().getString(String.valueOf(kaioken) + ".CommandName") + "] ["
										+ getConfig().getString(String.valueOf(kaioken) + ".Desc") + "] ["
										+ getConfig().getString(String.valueOf(kaioken) + ".DescBoost") + "]");
								player.sendMessage(ChatColor.RED + "Chance of failing this transformation: "
										+ ChatColor.GOLD + getConfig().getInt(String.valueOf(kaioken) + ".DamageOdds")
										+ ChatColor.RED + "%");
								player.sendMessage(
										ChatColor.RED + "Damage taken if transformed incorrectly: " + ChatColor.GOLD
												+ getConfig().getInt(String.valueOf(kaioken) + ".DamageTaken"));
								player.sendMessage(ChatColor.RED + "----------------------------------------------");
							}
						}
						cmdFound = true;
					}
					if (args[0].equalsIgnoreCase("info") && !cmdFound) {
						player.sendMessage(ChatColor.RED + "Super Saiyan Transformations info:");
						player.sendMessage(
								ChatColor.BOLD + "Current version: " + ChatColor.RED + getDescription().getVersion());
						player.sendMessage(ChatColor.GREEN + "Plugin Developed by: " + ChatColor.RED
								+ getDescription().getAuthors());
						player.sendMessage(
								ChatColor.GOLD + "If you have any questions about this plugin, here's a link!");
						player.sendMessage(ChatColor.RED + "http://bit.ly/2avikLF");
						cmdFound = true;
					}
				} else if (cmd.getName().equalsIgnoreCase("kaioken")) {
					for (int i = 0; i < this.kaiokenList.size(); i++) {
						final String kaioken = this.kaiokenList.get(i);
						if (args[0].equalsIgnoreCase(getConfig().getString(String.valueOf(kaioken) + ".CommandName"))) {
							cmdFound = true;
							if (player.hasPermission(
									"ssj." + getConfig().getString(String.valueOf(kaioken) + ".CommandName"))) {
								if (this.cooldownTime.containsKey(player)) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.RED
											+ "You must wait for " + this.cooldownTime.get(player) + " seconds.");
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
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Broadcast")) {
									Bukkit.broadcastMessage(String.valueOf(player.getDisplayName()) + ": " + getConfig()
											.getString(String.valueOf(kaioken) + ".PrefixQuote").replace("&", "§"));
									Bukkit.broadcastMessage(String.valueOf(player.getDisplayName()) + ": " + getConfig()
											.getString(String.valueOf(kaioken) + ".MiddleQuote").replace("&", "§"));
									Bukkit.broadcastMessage(String.valueOf(player.getDisplayName()) + ": " + getConfig()
											.getString(String.valueOf(kaioken) + ".SuffixQuote").replace("&", "§"));
								} else {
									player.sendMessage(getConfig().getString(String.valueOf(kaioken) + ".PrefixQuote")
											.replace("&", "§"));
									player.sendMessage(getConfig().getString(String.valueOf(kaioken) + ".MiddleQuote")
											.replace("&", "§"));
									player.sendMessage(getConfig().getString(String.valueOf(kaioken) + ".SuffixQuote")
											.replace("&", "§"));
								}
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.DAMAGE_RESISTANCE"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
											100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.DAMAGE_RESISTANCE")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.FAST_DIGGING"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.FAST_DIGGING")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.FIRE_RESISTANCE"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.FIRE_RESISTANCE")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.HEAL"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.HEAL")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.INCREASE_DAMAGE"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.INCREASE_DAMAGE")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.JUMP"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.JUMP")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.REGENERATION"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.REGENERATION")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.SATURATION"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.SATURATION")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.SPEED"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.SPEED")));
								if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.HEALTH_BOOST"))
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
											getConfig().getInt(String.valueOf(kaioken) + ".Traits.HEALTH_BOOST")));
								if (this.rand.nextInt(100) <= getConfig()
										.getInt(String.valueOf(kaioken) + ".DamageOdds")) {
									player.setLastDamage(getConfig().getInt(String.valueOf(kaioken) + ".DamageTaken"));
									player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.HEAL);
									player.removePotionEffect(PotionEffectType.REGENERATION);
									player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
									if (getConfig().getBoolean(String.valueOf(kaioken) + ".Use_Traits?.HEALTH_BOOST"))
										player.addPotionEffect(
												new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
									player.sendMessage(
											getConfig().getString(String.valueOf(kaioken) + ".DamageTakenQuote")
													.replace("&", "§"));
								}
								if (getConfig().getBoolean("Lightning_Effect"))
									player.getWorld().strikeLightning(player.getLocation());
								if (getConfig().getBoolean("Explosion_Effect"))
									player.getWorld().createExplosion(player.getLocation(),
											getConfig().getInt("Explosion_Radious"));
								if (getConfig().getBoolean("Sound_Effect"))
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								this.cooldownTimeK.put(player, Integer.valueOf(
										getConfig().getInt(String.valueOf(kaioken) + ".Kaioken_Cooldown") * 20));
								BukkitTask t = this.tasks.put(sender, (new BukkitRunnable() {
									public void run() {
										Main.this.cooldownTimeK.put(player, Integer.valueOf(
												((Integer) Main.this.cooldownTimeK.get(player)).intValue() - 1));
										if (((Integer) Main.this.cooldownTimeK.get(player)).intValue() == 0
												|| Main.this.getConfig()
														.getInt(String.valueOf(kaioken) + ".Kaioken_Cooldown") == 0) {
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
											Main.this.cooldownTimeK.remove(player);
											cancel();
										}
										Location location = player.getLocation();
										World world = player.getWorld();
										String checkKaioken = Main.this.getConfig()
												.getString(String.valueOf(kaioken) + ".Particle");
										if (checkKaioken.contains("redstone")) {
											world.spawnParticle(Particle.REDSTONE, location, pcount, 0.3D, 0.9D, 0.3D,
													0.05D, redstone);
										} else {
											world.spawnParticle(Particle.valueOf(Main.this.getConfig()
													.getString(String.valueOf(kaioken) + ".Particle").toUpperCase()),
													location.getX(), location.getY(), location.getZ(), pcount, 0.3D,
													0.9D, 0.3D, 0.05D);
										}
									}
								}).runTaskTimer((Plugin) this, 0L, 0L));
								if (t != null)
									t.cancel();
								this.cooldownTime.put(player,
										Integer.valueOf(getConfig().getInt(String.valueOf(kaioken) + ".Cooldown")));
								this.cooldownTask.put(player, new BukkitRunnable() {
									public void run() {
										Main.this.cooldownTime.put(player, Integer.valueOf(
												((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));
										if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0
												|| Main.this.getConfig()
														.getInt(String.valueOf(kaioken) + ".Cooldown") == 0
												|| player.isOp()) {
											Main.this.cooldownTime.remove(player);
											Main.this.cooldownTask.remove(player);
											cancel();
										}
									}
								});
								((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer((Plugin) this, 20L, 20L);
								if (player.isOp()) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.DARK_GREEN
											+ "You are OP, cooldown doesn't apply to you!");
								} else if (getConfig().getInt(String.valueOf(kaioken) + ".Cooldown") == 0) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.DARK_GREEN
											+ "Cooldown for this transformation is set to 0.");
								} else if (((Integer) this.cooldownTime.get(player)).intValue() == 0) {
									player.sendMessage(String.valueOf(this.Prefix) + ChatColor.DARK_GREEN
											+ "Cooldown is finished!");
								} else {
									player.sendMessage(String.valueOf(this.Prefix) + "You're now on a "
											+ getConfig().getInt(String.valueOf(kaioken) + ".Cooldown")
											+ " second cooldown");
								}
							} else {
								player.sendMessage(String.valueOf(this.Prefix) + ChatColor.DARK_RED
										+ "You do not have permission to perform this command.");
							}
						}
					}
				}
			if (!cmdFound || args.length == 0) {
				player.sendMessage(ChatColor.RED + "Usage: /ssj [id]");
				player.sendMessage(ChatColor.RED + "Usage: /kaioken x[id]");
				player.sendMessage(ChatColor.RED + "Usage: /ssj list");
				player.sendMessage(ChatColor.RED + "Usage: /ssj info");
			}
		} else if (args[0].equalsIgnoreCase("reload") && !(sender instanceof Player)) {
			saveDefaultConfig();
			reloadConfig();
			Bukkit.getLogger().info("[Super Saiyan PL] Successfully reloaded config!");
		}
		return false;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		BukkitTask t = this.tasks.get(player);
		if (t != null)
			t.cancel();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity().getPlayer();
		BukkitTask t = this.tasks.get(player);
		if (t != null)
			t.cancel();
	}

	@EventHandler
	public void onPlayerTeleportTeleport(PlayerTeleportEvent event) {
		if (getConfig().getBoolean("Teleportation_Removal")) {
			Player player = event.getPlayer();
			if (event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN
					|| event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
				BukkitTask t = this.tasks.get(player);
				if (t != null)
					t.cancel();
			}
		}
	}
}
