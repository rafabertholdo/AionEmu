/*     */ package admincommands;
/*     */ 
/*     */ import com.aionemu.commons.utils.AEInfos;
/*     */ import com.aionemu.gameserver.ShutdownHook;
/*     */ import com.aionemu.gameserver.configs.administration.AdminConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.utils.VersionningService;
/*     */ import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AESystem
/*     */   extends AdminCommand
/*     */ {
/*     */   public AESystem() {
/*  45 */     super("sys");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeCommand(Player admin, String[] params) {
/*  51 */     if (admin.getAccessLevel() < AdminConfig.COMMAND_SYSTEM) {
/*     */       
/*  53 */       PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");
/*     */       
/*     */       return;
/*     */     } 
/*  57 */     if (params == null || params.length < 1) {
/*     */       
/*  59 */       PacketSendUtility.sendMessage(admin, "Usage: //sys info | //sys memory | //sys gc | //sys restart <countdown time> <announce delay> | //sys shutdown <countdown time> <announce delay>");
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     if (params[0].equals("info")) {
/*     */ 
/*     */       
/*  66 */       PacketSendUtility.sendMessage(admin, "System Informations at: " + AEInfos.getRealTime().toString());
/*     */ 
/*     */       
/*  69 */       for (String line : VersionningService.getFullVersionInfo()) {
/*  70 */         PacketSendUtility.sendMessage(admin, line);
/*     */       }
/*     */       
/*  73 */       for (String line : AEInfos.getOSInfo()) {
/*  74 */         PacketSendUtility.sendMessage(admin, line);
/*     */       }
/*     */       
/*  77 */       for (String line : AEInfos.getCPUInfo()) {
/*  78 */         PacketSendUtility.sendMessage(admin, line);
/*     */       }
/*     */       
/*  81 */       for (String line : AEInfos.getJREInfo()) {
/*  82 */         PacketSendUtility.sendMessage(admin, line);
/*     */       }
/*     */       
/*  85 */       for (String line : AEInfos.getJVMInfo()) {
/*  86 */         PacketSendUtility.sendMessage(admin, line);
/*     */       }
/*     */     }
/*  89 */     else if (params[0].equals("memory")) {
/*     */ 
/*     */       
/*  92 */       for (String line : AEInfos.getMemoryInfo()) {
/*  93 */         PacketSendUtility.sendMessage(admin, line);
/*     */       }
/*     */     }
/*  96 */     else if (params[0].equals("gc")) {
/*     */       
/*  98 */       long time = System.currentTimeMillis();
/*  99 */       PacketSendUtility.sendMessage(admin, "RAM Used (Before): " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
/*     */       
/* 101 */       System.gc();
/* 102 */       PacketSendUtility.sendMessage(admin, "RAM Used (After): " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
/*     */       
/* 104 */       System.runFinalization();
/* 105 */       PacketSendUtility.sendMessage(admin, "RAM Used (Final): " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
/*     */       
/* 107 */       PacketSendUtility.sendMessage(admin, "Garbage Collection and Finalization finished in: " + (System.currentTimeMillis() - time) + " milliseconds...");
/*     */     
/*     */     }
/* 110 */     else if (params[0].equals("shutdown")) {
/*     */       
/*     */       try
/*     */       {
/* 114 */         int val = Integer.parseInt(params[1]);
/* 115 */         int announceInterval = Integer.parseInt(params[2]);
/* 116 */         ShutdownHook.getInstance().doShutdown(val, announceInterval, ShutdownHook.ShutdownMode.SHUTDOWN);
/* 117 */         PacketSendUtility.sendMessage(admin, "Server will shutdown in " + val + " seconds.");
/*     */       }
/* 119 */       catch (ArrayIndexOutOfBoundsException e)
/*     */       {
/* 121 */         PacketSendUtility.sendMessage(admin, "Numbers only!");
/*     */       }
/* 123 */       catch (NumberFormatException e)
/*     */       {
/* 125 */         PacketSendUtility.sendMessage(admin, "Numbers only!");
/*     */       }
/*     */     
/* 128 */     } else if (params[0].equals("restart")) {
/*     */       
/*     */       try
/*     */       {
/* 132 */         int val = Integer.parseInt(params[1]);
/* 133 */         int announceInterval = Integer.parseInt(params[2]);
/* 134 */         ShutdownHook.getInstance().doShutdown(val, announceInterval, ShutdownHook.ShutdownMode.RESTART);
/* 135 */         PacketSendUtility.sendMessage(admin, "Server will restart in " + val + " seconds.");
/*     */       }
/* 137 */       catch (ArrayIndexOutOfBoundsException e)
/*     */       {
/* 139 */         PacketSendUtility.sendMessage(admin, "Numbers only!");
/*     */       }
/* 141 */       catch (NumberFormatException e)
/*     */       {
/* 143 */         PacketSendUtility.sendMessage(admin, "Numbers only!");
/*     */       }
/*     */     
/* 146 */     } else if (params[0].equals("threadpool")) {
/*     */       
/* 148 */       List<String> stats = ThreadPoolManager.getInstance().getStats();
/* 149 */       for (String stat : stats)
/*     */       {
/* 151 */         PacketSendUtility.sendMessage(admin, stat.replaceAll("\t", ""));
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\AESystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */