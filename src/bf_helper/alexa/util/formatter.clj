(ns bf-helper.alexa.util.formatter
  (:refer-clojure :rename {range core-range})
  (:require [clojure.string :as str]))

(load "/bf_helper/alexa/res/strings")

(defn article
  [noun]
  (if ((set "aeiou") (first noun)) "an" "a"))

(defn character-race-class
  [c]
  (format "%s %s" (strings (:race c)) (strings (:class c))))

(defn character-race
  [c]
  (strings (:race c)))

(defn character-class
  [c]
  (strings (:class c)))

(defn character
  [c]
  (str (format "You're %s %s.\n" (article (strings (:race c))) (character-race-class c))
       (let [as (:ability-scores c)]
         (format "Your ability scores are:\n  Strength %d\n  Dexterity %d\n  Constitution %d\n  Inteligence %d\n  Wisdom %d\n  Charisma %d\n\n"
                 (as :str) (as :dex) (as :con) (as :int) (as :wis) (as :cha)))
       (if-let [s (:saving-throws c)]
         (str "Your saving throws are:\n"
              (if-let [d (s :death-ray)]
                (format "  Death Ray or Poison %d\n" d))
              (if-let [m (s :magic-wands)]
                (format "  Magic Wands %d\n" m))
              (if-let [p (s :paralysis)]
                (format "  Paralysis or Petrify %d\n" p))
              (if-let [d (s :dragon-breath)]
                (format "  Dragon Breath %d\n" d))
              (if-let [sp (s :spells)]
                (format "  Spells %d\n\n" sp))))
       (if-let [ab (:class-abilities c)]
         (format "You have the class ability \"%s\".\n" (strings (first ab))))
       (format "Your attack bonus is %d.\n" (c :attack-bonus))
       (format "You have %d gold." (c :money))))

(defn range [s]
  (str/replace s #"'" " feet"))
