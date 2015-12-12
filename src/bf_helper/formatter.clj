(ns bf-helper.formatter)

(load "data/strings")

(defn format-character
  [c]
  (str "Here's the character I rolled up.\n"
       (format "You're %s %s.\n" (strings (:race c)) (strings (:class c)))
       (let [as (:ability-scores c)]
         (format "Your ability scores are, Strength: %d, Dexterity: %d, Constitution: %d, Inteligence: %d, Wisdom: %d, Charisma: %d.\n"
                 (as :str) (as :dex) (as :con) (as :int) (as :wis) (as :cha)))
       (if-let [s (:saving-throws c)]
         (str "Your saving throws are, "
              (if-let [d (s :death-ray)]
                (format "Death Ray or Poison: %d, " d))
              (if-let [m (s :magic-wands)]
                (format "Magic Wands: %d, " m))
              (if-let [p (s :paralysis)]
                (format "Paralysis or Petrify: %d, " p))
              (if-let [d (s :dragon-breath)]
                (format "Dragon Breath: %d, " d))
              (if-let [sp (s :spells)]
                (format "Spells: %d.\n" sp))))
       (if-let [ab (:class-abilities c)]
         (format "You have the class ability \"%s\".\n" (strings (first ab))))
       (format "Your attack bonus is %d.\n" (c :attack-bonus))
       (format "You have %d gold." (c :money))))
