# rpgstats assistant

![icon](/pictures/icon.png)

&emsp;There are dozens of ways to have equipment and weapons in rpg setting, which have some stats, influencing a character. Moreover, character himself can have traits, doing the same. Sometimes, there is a need to emulate that stuff without usage of official ways, or even there are no ways - you just have imagined that for another pen&paper game with your friends.  
There is a chance, that this thingy can help you!

## Terms:

* CRS - certain rpg-system of stats and equipment

## Goals
### Main:
1. To formalize CRSs, invented by user, being able to use and re-use them
2. To help emulate and visualize stats of character in CRS equipped in certain way
### If I have nothing to do:
3. To streamline random drops & sources 
4. To accumulate and check statistics for existing games

## Contains:

* selfhosted db spitting jsons of stuff
* mobile app, that allows, in order of priority:

1. Character:
    * has CRS type, class, name, lvl
    * can create & view list of created
    * can equip and unequip items from its CRS
    * can view summary of CRS stats
    * can assign trais & talents to character
2. CRS:
    * can view already created
    * can add items to already created
    * can edit/create new, specify:
        * classes
        * stat types
        * items traits
        * class-item_trait limitations
        * items with stats
        * traits and talents
3. (non-essential) users system
    * copies db of approved immutable set of CRS
    * if admin, can mutate them
    * all stuff by non-admin added is saved locally
    * can request approval of admin to join default immutable set

4. (non-essential) drops system (for each CRS)
    * can add *analitycal* drops source, specify items and drop chances
    * can add *empirical* drop source, specify items but no chances
        * can log each individual drop, getting approx of drop chances
    * use random-drop generator for each source, based on drop chances
    * give tips for throwing dXX and table of drops

## Mockups:

[Mockplus](https://rp.mockplus.com/run/7Nj8yah2bn/gCAbzEb5Ma/MKw93MCLUL)

<details>
there will reside some screens
</details>

# Run it localy

### Backend

Start db and backend with docker compose:
```bash
docker-compose up
```

### Frontend

1. Open `android` folder in Android-Studio

2. Get your ip of your pc: 
* On unix:
```bash
ip a
```
* On windows:
```cmd
ipconfig
```

3. In `android/app/src/main/res/raw/config.properties` set `server_address` to your ip:
```properties
server_address=192.168.31.144
```

4. Run project in Android-Studio

5. Play with app
