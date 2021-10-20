package com.irunatbullets.mods.disfabric;

import com.irunatbullets.mods.disfabric.listeners.DiscordEventListener;
import kong.unirest.Unirest;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.security.auth.login.LoginException;
import java.util.Collections;

public class DisFabric implements DedicatedServerModInitializer {

    public static final String MOD_ID = "disfabric";
    public static Logger logger = LogManager.getLogger(MOD_ID);
    public static Configuration config;
    public static JDA jda;
    public static TextChannel textChannel;

    public static boolean stop = false;

    @Override
    public void onInitializeServer() {
        AutoConfig.register(Configuration.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Configuration.class).getConfig();
        try {
            DisFabric.jda = JDABuilder.createDefault(config.botToken).setHttpClient(new OkHttpClient.Builder()
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .build())
                .addEventListeners(new DiscordEventListener())
                .build();

            DisFabric.jda.awaitReady();
            DisFabric.textChannel = DisFabric.jda.getTextChannelById(config.channelId);
        } catch (LoginException ex) {
            jda = null;
            DisFabric.logger.error("Unable to login!", ex);
        } catch (InterruptedException ex) {
            jda = null;
            DisFabric.logger.error(ex);
        }
        if(jda != null) {
            ServerLifecycleEvents.SERVER_STOPPING.register((server) -> {
                stop = true;

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Unirest.shutDown();
                DisFabric.jda.shutdown();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
