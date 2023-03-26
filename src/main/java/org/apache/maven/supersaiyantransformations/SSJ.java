package org.apache.maven.supersaiyantransformations;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public final class SSJ extends JavaPlugin {

    public String Prefix = Objects.requireNonNull(getConfig().getString("Prefix")).replace("&", "§");

    public List<String> ssjList = getConfig().getStringList("Transformation_List.SuperSaiyans");

    public List<String> kaiokenList = getConfig().getStringList("Transformation_List.Kaiokens");

    private SSJCommands ssjcommands;

    private SSJParticleSystem ssjparticlesystem;

    private SSJParticleEffects ssjparticleffects;

    private SSJListeners ssjlisteners;

    private SSJCooldowns ssjcooldowns;

    private SSJMethodChecks ssjmethodchecks;

    private Metrics metrics;

    @Override
    public void onEnable() {

        int pluginId = 18034;

        metrics = new Metrics(this, pluginId);

        SSJVersionCheck();

        regClasses();

        ssjonEnableRChecks();

        regListeners();

        regCommandClasses();

    }

    @Override
    public void onDisable() {

        saveDefaultConfig();

    }

    private void regCommandClasses() {

        Objects.requireNonNull(getCommand("ssj")).setExecutor(new SSJCommands(this));

        Objects.requireNonNull(getCommand("kaioken")).setExecutor(new SSJCommands(this));

    }

    private void regListeners() {

        SSJListeners ls = new SSJListeners(this);

        super.getServer().getPluginManager().registerEvents(ls, this);

    }

    private void regClasses() {

        ssjparticlesystem = new SSJParticleSystem(this);

        ssjparticleffects = new SSJParticleEffects(this);

        ssjcooldowns = new SSJCooldowns(this);

        ssjmethodchecks = new SSJMethodChecks(this);

    }

    private void SSJVersionCheck() {

        if (getConfig().getDouble("version") < Double.parseDouble(getDescription().getVersion())) {

            File configFile = new File(getDataFolder(), "config.yml");

            configFile.delete();

            saveDefaultConfig();

            reloadConfig();

            getLogger().warning(Prefix + "Config.yml has been updated!");

        }

    }

    private void ssjonEnableRChecks () {

        getConfig().options().copyDefaults(true);

        saveDefaultConfig();

        ssjcooldowns.cooldownTime = new HashMap<>();

        ssjparticlesystem.cooldownTimeK = new HashMap<>();

        ssjcooldowns.cooldownTask = new HashMap<>();

    }

    public SSJParticleSystem getPs() {

        return ssjparticlesystem;

    }

    public SSJParticleEffects getPe() {

        return ssjparticleffects;

    }

    public SSJCooldowns getCd() {

        return ssjcooldowns;

    }

    public SSJMethodChecks getSSJMethodChecks() {

        return ssjmethodchecks;

    }
}
