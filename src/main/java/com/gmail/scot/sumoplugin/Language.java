package com.gmail.scot.sumoplugin;

public class Language {

    public static String Message_Prefix = "§6[§bSumo§6] §f";
    public static String Permission_Use = "sumo.use";

    public static String Message_Region_Deleted = "§aDu har fjernet regionen:§6 %regionName%";
    public static String Message_Region_Does_Not_Exist = "§cRegionen §6%regionName% §cfindes ikke!";

    public static String Message_Sumo_Is_Now_Started = "§6Sumo-Eventet §7er nu startet!";
    public static String Message_Sumo_Already_Started = Message_Prefix + "§cSumo er allerede igang!";

    public static String Message_Player_Joined = Message_Prefix + "§aSpilleren %player% tilsluttet sig §6sumo-eventet!";
    public static String Message_Player_Not_Playing = Message_Prefix + "§cSpilleren du prøver at kick er ikke med til eventet!";

    public static String Message_Click_To_Join = "§7Klik §aHER §7for at joine eller brug /sumo join kommandoen!";


    public static String Message_Already_Joined = Message_Prefix + "§cDu er allerede joinet!";
    public static String Message_Lost = Message_Prefix + "§cDu tabte og er derfor ude af eventet!";

    public static String Message_Set_Spawn_Completed = Message_Prefix + "§aDu har nu sat spawnet!";
    public static String Message_Set_Lose_Completed = Message_Prefix + "§aDu har nu sat lose locationen!";

    public static String Message_Set_Spawn_Exist = Message_Prefix + "§cSpawn locationen er allerede sat!";
    public static String Message_Set_Lose_Exist = Message_Prefix + "§cLose locationen er allerede sat!";


    public static String Message_Spawn_Most_Be_Set = Message_Prefix + "§cDu mangler at sætte spawn locationen!";
    public static String Message_Lost_Most_Be_Set = Message_Prefix + "§cDu mangler at sætte lose locationen!";

    public static String Message_Removed_Spawn_Location = Message_Prefix + "§cDu har nu slettet spawn locationen!";
    public static String Message_Removed_Lose_Location = Message_Prefix + "§cDu har nu slettet lose locationen!";

    public static String Message_Player_Tp_Spawn = Message_Prefix + "§aDu tp'et til spawn locationen!";
    public static String Message_Player_Tp_Lose = Message_Prefix + "§aDu tp'et til lose locationen!";
}
