package com.irunatbullets.mods.disfabric;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = DisFabric.MOD_ID)
public class Configuration implements ConfigData {

    @Comment(value = "Bot Token; see https://discordpy.readthedocs.io/en/latest/discord.html")
    @ConfigEntry.Category(value = "Discord")
    public String botToken = "";

    @Comment(value = "Channel id in Discord")
    @ConfigEntry.Category(value = "Discord")
    public String channelId = "";

    @Comment(value = """
            Admins ids in Discord; see https://support.discord.com/hc/en-us/articles/206346498-Where-can-I-find-my-User-Server-Message-ID-
            If more than one, enclose each id in quotation marks separated by commas, like this:
            "adminsIds": [\s
            \t\t"000",
            \t\t"111",
            \t\t"222"
            \t]""")
    @ConfigEntry.Category(value = "Discord")
    public String[] adminsIds = {""};

}
