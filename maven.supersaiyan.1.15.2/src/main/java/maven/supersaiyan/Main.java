package maven.supersaiyan;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import net.md_5.bungee.api.ChatColor;

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
		pm.registerEvents(this, this);
		pm.registerEvents(new Listeners(this), this);
		getLogger().info("Super Saiyan Plugin has been ENABLED " + getDescription().getVersion());

		this.cooldownTime = new HashMap<Player, Integer>();
		this.cooldownTimeK = new HashMap<Player, Integer>();
		this.cooldownTask = new HashMap<Player, BukkitRunnable>();

	}

	public void onDisable() {

		getLogger().info("Super Saiyan Plugin has been DISABLED");
		saveDefaultConfig();

	}

	List<String> ssjList = getConfig().getStringList("Transformation_List.SuperSaiyans");
	List<String> kaiokenList = getConfig().getStringList("Transformation_List.Kaiokens");

	ArrayList<Player> cooldown = new ArrayList<Player>();

	String Prefix = getConfig().getString("Prefix").replace("&", "§");

	private Map<CommandSender, BukkitTask> tasks = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {

		if ((sender instanceof Player)) {

			final Player player = (Player) sender;

			// Gets the list of SSJ transformations in config
			// List<String> ssjList =
			// getConfig().getStringList("Transformation_List.SuperSaiyans");
			// List<String> kaiokenList =
			// getConfig().getStringList("Transformation_List.Kaiokens");
			int pcount = getConfig().getInt("Particle_Count");

			Particle.DustOptions redstone = new Particle.DustOptions(Color.RED, 1);

			// Now we handle what the player said to us!
			// SSJ and Kaioken are pretty much identical, but Kaioken just has a timer.
			// At least it's more efficient than v11, with 3000+ lines of copypasta

			boolean cmdFound = false;

			if (args.length >= 1) {

				if ((cmd.getName().equalsIgnoreCase("ssj"))) {

					// Special exception for /ssj base

					if (args[0].equalsIgnoreCase("base")) {

						BukkitTask t = tasks.get(sender);
						cmdFound = true;

						if (t != null) {
							t.cancel();
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
						player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
						player.sendMessage(getConfig().getString("Base_Form.PrefixQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Base_Form.MiddleQuote").replace("&", "§"));
						player.sendMessage(getConfig().getString("Base_Form.SuffixQuote").replace("&", "§"));

					}

					// Loop through the list of known SSJ's to match

					for (int i = 0; i < ssjList.size(); i++) {

						String ssj = ssjList.get(i);

						if (args[0].equalsIgnoreCase(getConfig().getString(ssj + ".CommandName"))) {

							cmdFound = true;

							// If we're here, we have a match!
							// Check perms, we don't want everyone in the factions world going Super Saiyan
							// God...

							if (player.hasPermission("ssj." + getConfig().getString(ssj + ".CommandName"))) {

								// Check if they must wait for more time

								if (this.cooldownTime.containsKey(player)) {

									player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
											+ this.cooldownTime.get(player) + " seconds.");

									return true;

								}

								// Get a much happier name
								// String cmdlet = getConfig().getString(ssj + ".CommandName");
								// Remove potion effects...

								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);

								// Send chat stuff

								if (getConfig().getBoolean(getConfig().getString(ssj + ".CommandName"))) {

									Bukkit.broadcastMessage(player.getDisplayName() + ": "
											+ getConfig().getString(ssj + ".PrefixQuote").replace("&", "§"));
									Bukkit.broadcastMessage(player.getDisplayName() + ": "
											+ getConfig().getString(ssj + ".MiddleQuote").replace("&", "§"));
									Bukkit.broadcastMessage(player.getDisplayName() + ": "
											+ getConfig().getString(ssj + ".SuffixQuote").replace("&", "§"));

								} else {

									player.sendMessage(getConfig().getString(ssj + ".PrefixQuote").replace("&", "§"));
									player.sendMessage(getConfig().getString(ssj + ".MiddleQuote").replace("&", "§"));
									player.sendMessage(getConfig().getString(ssj + ".SuffixQuote").replace("&", "§"));

								}

								if (getConfig().getBoolean(ssj + ".Use_Traits?.DAMAGE_RESISTANCE")) {

									player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
											100000000, getConfig().getInt(ssj + ".Traits.DAMAGE_RESISTANCE")));

								}

								if (getConfig().getBoolean(ssj + ".Use_Traits?.FAST_DIGGING")) {

									player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
											getConfig().getInt(ssj + ".Traits.FAST_DIGGING")));

								}

								if (getConfig().getBoolean(ssj + ".Use_Traits?.FIRE_RESISTANCE")) {

									player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
											getConfig().getInt(ssj + ".Traits.FIRE_RESISTANCE")));

								}

								if (getConfig().getBoolean(ssj + ".Use_Traits?.HEAL")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
											getConfig().getInt(ssj + ".Traits.HEAL")));
								}
								if (getConfig().getBoolean(ssj + ".Use_Traits?.INCREASE_DAMAGE")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
											getConfig().getInt(ssj + ".Traits.INCREASE_DAMAGE")));
								}
								if (getConfig().getBoolean(ssj + ".Use_Traits?.JUMP")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
											getConfig().getInt(ssj + ".Traits.JUMP")));
								}
								if (getConfig().getBoolean(ssj + ".Use_Traits?.REGENERATION")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
											getConfig().getInt(ssj + ".Traits.REGENERATION")));
								}
								if (getConfig().getBoolean(ssj + ".Use_Traits?.SATURATION")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
											getConfig().getInt(ssj + ".Traits.SATURATION")));
								}
								if (getConfig().getBoolean(ssj + ".Use_Traits?.SPEED")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
											getConfig().getInt(ssj + ".Traits.SPEED")));
								}
								if (getConfig().getBoolean(ssj + ".Use_Traits?.HEALTH_BOOST")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
											getConfig().getInt(ssj + ".Traits.HEALTH_BOOST")));
								}
								if (this.rand.nextInt(100) <= getConfig().getInt(ssj + ".DamageOdds")) {
									player.setLastDamage(getConfig().getInt(ssj + ".DamageTaken"));
									player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.HEAL);
									player.removePotionEffect(PotionEffectType.REGENERATION);
									player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
									if (getConfig().getBoolean(ssj + ".Use_Traits?.HEALTH_BOOST")) {
										player.addPotionEffect(
												new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
									}
									player.sendMessage(
											getConfig().getString(ssj + ".DamageTakenQuote").replace("&", "§"));
								}
								if (getConfig().getBoolean("Lightning_Effect")) {
									player.getWorld().strikeLightning(player.getLocation());
								}
								if (getConfig().getBoolean("Explosion_Effect")) {
									player.getWorld().createExplosion(player.getLocation(),
											getConfig().getInt("Explosion_Radious"));
								}
								if (getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);
								}

								// particle system

								BukkitTask t = tasks.put(sender, new BukkitRunnable() {

									public void run() {

										Location location = player.getLocation();
										World world = player.getWorld();
										String checkSsj = getConfig().getString(ssj + ".Particle");

										if (checkSsj.contains("redstone")) {

											world.spawnParticle(Particle.REDSTONE, location, pcount, 0.3, 0.9, 0.3,
													0.05, redstone);

										} else {

											world.spawnParticle(
													Particle.valueOf(
															getConfig().getString(ssj + ".Particle").toUpperCase()),
													location.getX(), location.getY(), location.getZ(), pcount, 0.3, 0.9,
													0.3, 0.05);
										}
									}
								}.runTaskTimerAsynchronously(this, 0, 0));

								if (t != null)
									t.cancel();

								// Now do the timer stuff
								this.cooldownTime.put(player, Integer.valueOf(getConfig().getInt(ssj + ".Cooldown")));
								this.cooldownTask.put(player, new BukkitRunnable() {
									public void run() {

										Main.this.cooldownTime.put(player, Integer.valueOf(
												((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));

										if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0
												|| getConfig().getInt(ssj + ".Cooldown") == 0 || player.isOp()) {
											Main.this.cooldownTime.remove(player);
											Main.this.cooldownTask.remove(player);
											cancel();

										}
									}
								});

								((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);

								if (player.isOp()) {

									player.sendMessage(Prefix + ChatColor.DARK_GREEN
											+ "You are OP, cooldown doesn't apply to you!");

								} else if (getConfig().getInt(ssj + ".Cooldown") == 0) {

									player.sendMessage(Prefix + ChatColor.DARK_GREEN
											+ "Cooldown for this transformation is set to 0.");

								} else if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {

									player.sendMessage(Prefix + ChatColor.DARK_GREEN + "Cooldown is finished!");

								} else {

									player.sendMessage(Prefix + "You're now on a "
											+ getConfig().getInt(ssj + ".Cooldown") + " second cooldown");
								}

								return true;

							} else {

								player.sendMessage(Prefix + ChatColor.DARK_RED + "You do not have permission to perform this command.");

							}
						}
					}

					// If some idiot makes /ssj list a transformation, let them go
					if (args[0].equalsIgnoreCase("list") && !cmdFound) {
						player.sendMessage(ChatColor.RED + "List of ssj transformations:");
						player.sendMessage(ChatColor.GREEN + "[Command] [ID] [Transformation type] [Boost]");
						player.sendMessage(ChatColor.RED + "----------------------------------------------");
						player.sendMessage(ChatColor.BLUE + "[ssj] [base] [Base Form] [null]");
						player.sendMessage(ChatColor.RED + "----------------------------------------------");
						for (int i = 0; i < ssjList.size(); i++) {
							String ssj = ssjList.get(i);
							if (player.hasPermission("ssj." + getConfig().getString(ssj + ".CommandName"))) {
								player.sendMessage(
										ChatColor.BLUE + "[ssj] [" + getConfig().getString(ssj + ".CommandName") + "] ["
												+ getConfig().getString(ssj + ".Desc") + "] ["
												+ getConfig().getString(ssj + ".DescBoost") + "]");
								player.sendMessage(
										ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
												+ getConfig().getInt(ssj + ".DamageOdds") + ChatColor.RED + "%");
								player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
										+ ChatColor.GOLD + getConfig().getInt(ssj + ".DamageTaken"));
								player.sendMessage(ChatColor.RED + "----------------------------------------------");
							}
						}
						for (int i = 0; i < kaiokenList.size(); i++) {
							String kaioken = kaiokenList.get(i);
							if (player.hasPermission("ssj." + getConfig().getString(kaioken + ".CommandName"))) {
								player.sendMessage(
										ChatColor.BLUE + "[kaioken] [" + getConfig().getString(kaioken + ".CommandName")
												+ "] [" + getConfig().getString(kaioken + ".Desc") + "] ["
												+ getConfig().getString(kaioken + ".DescBoost") + "]");
								player.sendMessage(
										ChatColor.RED + "Chance of failing this transformation: " + ChatColor.GOLD
												+ getConfig().getInt(kaioken + ".DamageOdds") + ChatColor.RED + "%");
								player.sendMessage(ChatColor.RED + "Damage taken if transformed incorrectly: "
										+ ChatColor.GOLD + getConfig().getInt(kaioken + ".DamageTaken"));
								player.sendMessage(ChatColor.RED + "----------------------------------------------");
							}
						}
						cmdFound = true;
					}
					// If another idiot decides to add a new SSJ form runned by /ssj info, let the
					// idiot go and disable normal /ssj info.
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
					for (int i = 0; i < kaiokenList.size(); i++) {
						String kaioken = kaiokenList.get(i);

						if (args[0].equalsIgnoreCase(getConfig().getString(kaioken + ".CommandName"))) {
							cmdFound = true;
							if (player.hasPermission("ssj." + getConfig().getString(kaioken + ".CommandName"))) {
								if (this.cooldownTime.containsKey(player)) {
									player.sendMessage(this.Prefix + ChatColor.RED + "You must wait for "
											+ this.cooldownTime.get(player) + " seconds.");
									return true;
								}
								// String cmdlet = getConfig().getString(kaioken + ".CommandName");
								player.removePotionEffect(PotionEffectType.FAST_DIGGING);
								player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
								player.removePotionEffect(PotionEffectType.HEAL);
								player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
								player.removePotionEffect(PotionEffectType.JUMP);
								player.removePotionEffect(PotionEffectType.REGENERATION);
								player.removePotionEffect(PotionEffectType.SATURATION);
								player.removePotionEffect(PotionEffectType.SPEED);
								player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
								if (getConfig().getBoolean(kaioken + ".Broadcast")) {
									Bukkit.broadcastMessage(player.getDisplayName() + ": "
											+ getConfig().getString(kaioken + ".PrefixQuote").replace("&", "§"));
									Bukkit.broadcastMessage(player.getDisplayName() + ": "
											+ getConfig().getString(kaioken + ".MiddleQuote").replace("&", "§"));
									Bukkit.broadcastMessage(player.getDisplayName() + ": "
											+ getConfig().getString(kaioken + ".SuffixQuote").replace("&", "§"));
								} else {
									player.sendMessage(
											getConfig().getString(kaioken + ".PrefixQuote").replace("&", "§"));
									player.sendMessage(
											getConfig().getString(kaioken + ".MiddleQuote").replace("&", "§"));
									player.sendMessage(
											getConfig().getString(kaioken + ".SuffixQuote").replace("&", "§"));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.DAMAGE_RESISTANCE")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
											100000000, getConfig().getInt(kaioken + ".Traits.DAMAGE_RESISTANCE")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.FAST_DIGGING")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100000000,
											getConfig().getInt(kaioken + ".Traits.FAST_DIGGING")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.FIRE_RESISTANCE")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000000,
											getConfig().getInt(kaioken + ".Traits.FIRE_RESISTANCE")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.HEAL")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 50,
											getConfig().getInt(kaioken + ".Traits.HEAL")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.INCREASE_DAMAGE")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000000,
											getConfig().getInt(kaioken + ".Traits.INCREASE_DAMAGE")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.JUMP")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000000,
											getConfig().getInt(kaioken + ".Traits.JUMP")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.REGENERATION")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600,
											getConfig().getInt(kaioken + ".Traits.REGENERATION")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.SATURATION")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000000,
											getConfig().getInt(kaioken + ".Traits.SATURATION")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.SPEED")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000000,
											getConfig().getInt(kaioken + ".Traits.SPEED")));
								}
								if (getConfig().getBoolean(kaioken + ".Use_Traits?.HEALTH_BOOST")) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000,
											getConfig().getInt(kaioken + ".Traits.HEALTH_BOOST")));
								}
								if (this.rand.nextInt(100) <= getConfig().getInt(kaioken + ".DamageOdds")) {
									player.setLastDamage(getConfig().getInt(kaioken + ".DamageTaken"));
									player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
									player.removePotionEffect(PotionEffectType.HEAL);
									player.removePotionEffect(PotionEffectType.REGENERATION);
									player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
									if (getConfig().getBoolean(kaioken + ".Use_Traits?.HEALTH_BOOST")) {
										player.addPotionEffect(
												new PotionEffect(PotionEffectType.HEALTH_BOOST, 100000000, 0));
									}
									player.sendMessage(
											getConfig().getString(kaioken + ".DamageTakenQuote").replace("&", "§"));
								}
								if (getConfig().getBoolean("Lightning_Effect")) {
									player.getWorld().strikeLightning(player.getLocation());
								}
								if (getConfig().getBoolean("Explosion_Effect")) {
									player.getWorld().createExplosion(player.getLocation(),
											getConfig().getInt("Explosion_Radious"));
								}
								if (getConfig().getBoolean("Sound_Effect")) {
									player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F,
											2.0F);

								}

								this.cooldownTimeK.put(player,
										Integer.valueOf(getConfig().getInt(kaioken + ".Kaioken_Cooldown") * 20));

								BukkitTask t = tasks.put(sender, new BukkitRunnable() {

									public void run() {

										Main.this.cooldownTimeK.put(player, Integer.valueOf(
												((Integer) Main.this.cooldownTimeK.get(player)).intValue() - 1));

										if (((Integer) Main.this.cooldownTimeK.get(player)).intValue() == 0
												|| getConfig().getInt(kaioken + ".Kaioken_Cooldown") == 0) {
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
										String checkKaioken = getConfig().getString(kaioken + ".Particle");

										if (checkKaioken.contains("redstone")) {

											world.spawnParticle(Particle.REDSTONE, location, pcount, 0.3, 0.9, 0.3,
													0.05, redstone);

										} else {

											world.spawnParticle(
													Particle.valueOf(
															getConfig().getString(kaioken + ".Particle").toUpperCase()),
													location.getX(), location.getY(), location.getZ(), pcount, 0.3, 0.9,
													0.3, 0.05);
										}

									}
								}.runTaskTimer(this, 0, 0));

								if (t != null)
									t.cancel();

								// cooldownshit

								this.cooldownTime.put(player,
										Integer.valueOf(getConfig().getInt(kaioken + ".Cooldown")));
								this.cooldownTask.put(player, new BukkitRunnable() {
									public void run() {

										Main.this.cooldownTime.put(player, Integer.valueOf(
												((Integer) Main.this.cooldownTime.get(player)).intValue() - 1));

										if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0
												|| getConfig().getInt(kaioken + ".Cooldown") == 0 || player.isOp()) {
											Main.this.cooldownTime.remove(player);
											Main.this.cooldownTask.remove(player);
											cancel();

										}
									}
								});

								((BukkitRunnable) this.cooldownTask.get(player)).runTaskTimer(this, 20L, 20L);

								if (player.isOp()) {

									player.sendMessage(Prefix + ChatColor.DARK_GREEN
											+ "You are OP, cooldown doesn't apply to you!");

								} else if (getConfig().getInt(kaioken + ".Cooldown") == 0) {

									player.sendMessage(Prefix + ChatColor.DARK_GREEN
											+ "Cooldown for this transformation is set to 0.");

								} else if (((Integer) Main.this.cooldownTime.get(player)).intValue() == 0) {

									player.sendMessage(Prefix + ChatColor.DARK_GREEN + "Cooldown is finished!");

								} else {

									player.sendMessage(Prefix + "You're now on a "
											+ getConfig().getInt(kaioken + ".Cooldown") + " second cooldown");
								}

							} else {

								player.sendMessage(Prefix + ChatColor.DARK_RED
										+ "You do not have permission to perform this command.");

							}
						}
					}
				}
			}

			if (!cmdFound || args.length == 0) {

				player.sendMessage(Prefix + ChatColor.RED + "Usage: /ssj [id]");
				player.sendMessage(Prefix + ChatColor.RED + "Usage: /kaioken x[id]");
				player.sendMessage(Prefix + ChatColor.RED + "Usage: /ssj list");
				player.sendMessage(Prefix + ChatColor.RED + "Usage: /ssj info");

			}

		} else if ((args[0].equalsIgnoreCase("reload")) && (!(sender instanceof Player))) {

			saveDefaultConfig();
			reloadConfig();
			Bukkit.getLogger().info(Prefix + "Successfully reloaded config!");

		}

		return false;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = (Player) event.getPlayer();
		BukkitTask t = tasks.get(player);
		if (t != null) {
			t.cancel();
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity().getPlayer();
		BukkitTask t = tasks.get(player);
		if (t != null) {
			t.cancel();
		}
	}

	@EventHandler
	public void onPlayerTeleportTeleport(PlayerTeleportEvent event) {
		if (getConfig().getBoolean("Teleportation_Removal")) {
			Player player = event.getPlayer();
			if ((event.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN)
					|| (event.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND)) {
				BukkitTask t = tasks.get(player);
				if (t != null) {
					t.cancel();
				}
			}
		}
	}

}
