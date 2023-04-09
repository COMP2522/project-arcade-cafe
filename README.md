# Galaga 0.5
### Project Arcade Cafe

<img width="958" alt="Screen Shot 2023-04-09 at 3 02 46 PM" src="https://user-images.githubusercontent.com/81988553/230798634-0967d6f7-bc78-415c-9639-4e67ffa3a56b.png">
<img width="957" alt="Screen Shot 2023-04-09 at 3 02 55 PM" src="https://user-images.githubusercontent.com/81988553/230798638-a725d177-4c37-4ae7-a227-22633b73f754.png">
<img width="946" alt="Screen Shot 2023-04-09 at 3 03 03 PM" src="https://user-images.githubusercontent.com/81988553/230798642-597a3f51-507a-43f6-b21e-ea1954a9d926.png">

# Quick Start 
<details>
  <summary>MongoDB Setup</summary>
  
  ## TODO 1: Sign up for a free MongoDB account
Go to [MongoDB](https://www.mongodb.com/) and sign up for a Cloud Atlas instance. You can follow the "Start Free" link. Once you have an account, navigate to the Database Deployment panel and select "Build a Database". Choose the free M0 deployment on the far right.

![image](https://user-images.githubusercontent.com/3506567/226447324-8b31650b-d628-4203-aee2-c9e359d0048b.png)

Now create a username and password. This should be different from your account username and password.
![image](https://user-images.githubusercontent.com/3506567/226447903-baf02032-b1ba-419b-b036-51f90bae960f.png)

Add whichever IP you'd like to connect from. I put `0.0.0.0` because I'm OK with anyone in the world accessing my database.

![image](https://user-images.githubusercontent.com/3506567/226448181-27fb4f6f-68aa-4fa0-a1a8-d2b3b02a651a.png)

Finish and close the setup.

## TODO 2: Connect to your database
On the main deployment page, you should now see your database and a "Connect" button which you should press now.

![image](https://user-images.githubusercontent.com/3506567/226448543-b3220819-b89e-4ad8-a034-42efc9d81326.png)

Then click "Connect your application". Select the Java driver code and copy it into a `main` method somewhere in your project or personal repo.

![image](https://user-images.githubusercontent.com/3506567/226448776-1ffd8aa5-6d7c-4533-8068-ecad49b7dbe9.png)
</details>
## Add a config.json file under src folder with the following

```json
{
  "DB_USERNAME": "<your_mongoDB_username>",
  "DB_PASSWORD": "<your_mongoDB_password>"
}
```
## Project Pitch

Project Arcade Cafe presents Galaga 0.5, the more entertaining version of the original Galaga. 

Control your spaceship to shoot bullets at the invading aliens, and collect powerups along the way!

To run the game, clone this repository into your local computer, open in IntelliJ and run the Window class.

**Requirement 1:** Galaga 0.5 utilizes PApplet for its visual interfaces.

**Requirement 2:** BulletManager utilizes asynchronous processing to iterate the bullet arraylist. This is to allow removal
of bullets from the arraylist while still iterating through it.

**Requirement 3:** After pausing the game and clicking the <Save & Exit> button, the game state is saved in a JSON file.
In addition, with every new wave of enemies, the game state is saved.

**Requirement 4:** BulletManager utilizes the iterator to iterate through the bullet arraylist.

**Requirement 5:** Different branches and git issues were used to track and document the progress of the project.


**Communication policies:** Project Arcade Cafe has a private discord group that is used to keep track of all 
communication, questions and requests. Meetings are also held on discord in the case that in-person is not possible.

**Roles and responsibilities:** Please see the individual pitches in the Initial Individual Pitch section.

**Milestones**:
1. All Sprites, classes that extend Sprite, Managers are implemented and functioning correctly.
2. Collisions and modifications to enemies and powerups are completed to increase difficulty of game.
3. Add menus, including start, pause, game over, and scoreboard.
4. Be able to save game state.

## Initial UML Diagrams (group, 1%)


## Initial GitHub Issues
Please see the closed GitHub Issues for reference.

## Code Contributions (individual, 15%)
The following lists the classes or tasks that each teammate worked on.

**Helen Liu:** Sprite, Enemy, EnemyManager, PauseMenu, Window

**Mylo Yu:** Window, Player, LevelManager, saving game as JSON file

**Samuel Chua:** Window, All visual aspects including background and animations

**Eric (Sungmok) Cho:** Bullet, BulletManager, Button, DatabaseHandler, GameOverMenu, GameState, MenuManager, 
ScoreboardMenu, ScoreManager, StartMenu, Window

**Mina (Sunmin) Park:** LivesManager, PowerUp, PowerUpManager, LevelManager, Window

### Initial individual pitch (1%)
A description of your individual feature that you plan to implement. 

**Helen Liu:** I will be handling the Sprite, Enemy and Enemy Manager for the project. 
For my part, I will be responsible for handling the physical attributes of the enemy hordes, their positions, 
what happens when they come into contact with the player, and how often they are "refreshed".

**Mylo Yu:** I will be developing player functions, window settings and functions, as well as saving all information 
about the current gamestate to a JSON file so a player can quit out of the game and return later. I will also develop 
the level manager class and its functions, which will manage all the enemies, bullets, player, and score.

**Samuel Chua:** I will be developing the code and graphics on the implementation of the background environment and 
visual effects. This entails how general images, objects and effects look, how they're created, drawn, and interact 
with window class, the start menu window, game screen window, and options page window. This includes making all the 
pixel art for the visual assets that will be used for the project.

**Eric (Sungmok) Cho:** I will work on the bullet, bullet manager, start menu, and button classes. Bullet class has 
integer type variables such as bulletDamage that indicates the damage of bullet, coordinates for the bullet, x and y, 
and speed of the bullet. Bullet manager manages a bullet object's instance like move, remove, and add. StartMenu has 
a few button objects that send users to different window when they click the buttons. Button object will have some 
variables that determine the font size, colour and properties of the button, such as its shape and colour.

**Mina (Sunmin) Park:** I will be working on the PowerUp and PowerUp Manager. When the player collides with a PowerUp 
sprite, the player can either get an increase in HP or the bullet fire rate, depending on the PowerUp sprite. The 
PowerUp Manager manages the spawn time and spawn area of the PowerUp sprite.

### Initial individual UML Diagrams (1%)
