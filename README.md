#Basic Fantasy Helper
Basic Fantasy Helper is an Alexa Skill that helps those playing [Basic Fantasy RPG](basicfantasy.org). The Skill allows users to create new characters (the mortality rate can be high) and look up common spells and rules. The intent is to reduce time looking through rule books to allow more time playing.

The Skill has two main parts
- Character creation
- Information lookup

##Character Creation
Character creation can be tedious. Especially when you die mid session and everyone is waiting on you to get back in the game. The character creation Intents do the dice rolling for you, populating the needed fields, and delivers to you a finished character.

Character creation is divided into three parts. Each with it's own Intent.

1. CreateCharacterIntent - This is used to create a character with a random race and class.
2. CreateCharacterRaceIntent - This is used to create a character with the race defined and a random class that is permitted by the race.
3. CreateCharacterClassIntent - This is like the previous Intent except it defines the class instead of the race. The race is still picked from what is valid for the class.

These all allow for partial Intents. When a partial Intent is received the Skill will prompt the user to supply the missing information. If an invalid class or race is given the Skill will tell the user that it doesn't know how to create that race/class.

Character creation does not try to read the generated character to the user. Instead the Skill prompts the user to look in the companion app for their character.

####Known issues
Halfling is not in Alexa's voice recognition. No work has been done to alleviate this issue. A possible work around is to add 'half wing' and/or 'half ling' to the slots for race. Ling may work since it can be a surname, is a county in China, and is a [type of fish](https://en.wikipedia.org/wiki/Common_ling).

##Information Lookup
There are few things worse than a [rules lawyer](https://en.wikipedia.org/wiki/Rules_lawyer). What makes situations worse is not being able to quickly look up the actual rule when disagreements arise. That's why this Skill allows you to quickly look them up.

Information lookup is split into two parts. Again, each with it's own Intent.

1. LookupSpellIntent - This is used to look up spells. It gives caster levels, range, duration, etc. The Skill will read the description to the user while providing a card with all the information.
2. LookupRuleIntent - This is used to look up rules. The Skill reads the rule to the user and does not send a card to the user.

Again, these allow for partial Intents and will prompt the user for the missing information.


## License

Copyright © 2015 Jeff Engebretsen

Distributed under the MIT License.
