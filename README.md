AntiBot
==========

The Ultimate Anti Spam protection plugin for Minecraft.  It trolls users who use login spam tools like **PWN4G3**.

![Screenshot](http://dev.bukkit.org/media/images/39/173/test.png)

Like this plugin and need a deathban type plugin?! Come try my latest: [http://dev.bukkit.org/server-mods/klaypexbans](http://dev.bukkit.org/server-mods/klaypexbans/)

Bukkit Thread: [http://forums.bukkit.org/threads/admn-sec-antibot-v2-3-the-ultimate-anti-spam-protection-for-minecraft-1-1-r6.63370/](http://forums.bukkit.org/threads/admn-sec-antibot-v2-3-the-ultimate-anti-spam-protection-for-minecraft-1-1-r6.63370/)

**About**
===========

This plugin uses special techniques to block spam bots from joining your Minecraft Server.  It blocks spammers by detecting how many players join per second (EX: 12 players join in 5 seconds).  If that statement is true, it will kick those users from your server and adds them to a autokill list.  

It has Chat Spam protection.  After a certain amount of chat messages are sent in less than x seconds, they're kicked from the server. 

It also has other options, such as Chat Flow, Country Bans, and delayed start!

**Chat Flow** solves one of the most possible scenarios out there, when the bots bypass anti-chat spam protection!  The name of this feature is practical to keeping your server chat from chat spam.  Read the following scenario to get a better understanding of how it works.

**Scenario - Before Chat Flow**
This guy's Bob.  He runs a popular PvP server.  He loves the incoming traffic towards his server.  But then, some angry guy finds your server and is angry for no apparent reason.  So he opens his tools and begins sending 20 players or "bots" to your server, the bots then bypass your spam protection by sending 1 message every 2 seconds.  The chat spam protection isn't doing anything and your server chat ends up getting spammed like crazy.

**Scenario - With Chat Flow**
Bob installed AntiBot and adjusted AntiBot's Chat Flow.   The angry guy came around and once again, loaded his "bots" into the game and began running the same technique.  But fortunately, AntiBot's chat flow kicked in and muted the entire server chat for 5 seconds (auto increments by 5 seconds each time chat flow detects spam). If Bob assigned //antibot.voice// to his staff, they would be able to talk regardless if the chat is muted or not.  Bob then manually bans the bots and the day has been saved!

I had no better way of explaining chat flow besides this scenario.

**Country Bans** allows you to ban (or whitelist) countries into your server.  This is perfect if you wish to make your country based minecraft server to only allow your country.

**Delayed Start** solves another problem, what if you restart the server with a bunch of players on, and basically when they all rejoin, AntiBot then detects bot spam and falsely kicks everybody.  This prevents that happening by disabling AntiBot for 60 seconds and re-enabling afterwards.

It also has a legacy option to turn on a fake whitelist to prevent other users from joining the server until the specified joins per second has ran out. 

The best part? This plugin is configurable to your own hands.  If my default settings don't work for your server, change them!

**WARNING:** Expect false positives if you install the plugin if your server gets over 20 connections a minute or is really populated.

**History**
===========

This plugin was **not** created after [AntiPwnage](http://dev.bukkit.org/server-mods/antipwnage) or [NoPwnage](http://dev.bukkit.org/server-mods/nopwnage), this project started around 5 months ago and was a private plugin for my Minecraft Server (freebuild.me).

[s]Unfortunately, that server has died and now I have no use for this plugin because I don't really own a server anymore.  Besides, coding is something I like to do nowadays.[/s]  I'm currently in the middle of restoring it.

**What I do/Requests**
===========

The first thing that came to mind when I was writing this plugin for release, I noticed that people will find that the commands portion of the plugin may not suffice their needs of a basic Anti Pwnage plugin.  If this is the case, feel free to request it in a ticket or post a comment about it on here (However, I may miss it.  So tickets is your best option.).  I will reply to your request ASAP and see if it's a possibility or not.  It will most likely be possible to add your idea to the plugin, so request it if you can!

Download
===========

Because BukkitDev requires approval. I've made a wget friendly URL you can use:
[https://github.com/downloads/SuperSpyTX/AntiBot/AntiBot.jar](https://github.com/downloads/SuperSpyTX/AntiBot/AntiBot.jar)

**NEW:** You can now download development builds from my fresh Jenkins Install (which may not last too long):
[http://ci.freebuild.me:8080/job/AntiBot/Development/](http://ci.freebuild.me:8080/job/AntiBot/Development/)

Commands/Permissions/Configuration
===========

You can find the Commands & Permissions [here](http://dev.bukkit.org/server-mods/antibot/pages/commands-permissions/).

You can find the Configuration explanation [here as well.](http://dev.bukkit.org/server-mods/antibot/pages/configuration/)

**NOTICE:** You don't have to edit your configuration.  If you reset your config, it should be good at it's defaults.

Help?  IRC Info
===========

You can find me on Rizon IRC on [#antibot@irc.esper.net](http://webchat.esper.net/?nick=&channels=antibot)

![](http://metrics.griefcraft.com/signature/antibot.png)