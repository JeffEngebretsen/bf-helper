(ns bf-helper.speechlet
  (:require [clojure.tools.logging :as log]
            [bf-helper.gen-character :refer :all])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech)
           (com.amazon.speech.speechlet SpeechletResponse))
  (:gen-class
   :main false
   :implements [com.amazon.speech.speechlet.Speechlet]
   :prefix "speechlet-"))

(load "data/strings")

(defn- format-character
  [c]
  (str "Here's the character I rolled up.\n"
       (format "You're %s %s.\n" (strings (:race c)) (strings (:class c)))
       (let [as (:ability-scores c)]
         (format "Your ability scores are, Strength: %d, Dexterity %d, Constitution %d, Inteligence %d, Wisdom %d, Charisma %d.\n"
                 (as :str) (as :dex) (as :con) (as :int) (as :wis) (as :cha)))
       (if-let [s (:saving-throws c)]
         (str "Your saving throws are, "
              (if-let [d (s :death-ray)]
                (format "Death Ray or Poison %d, " d))
              (if-let [m (s :magic-wands)]
                (format "Magic Wands %d, " m))
              (if-let [p (s :paralysis)]
                (format "Paralysis or Petrify %d, " p))
              (if-let [d (s :dragon-breath)]
                (format "Dragon Breath %d, " d))
              (if-let [sp (s :spells)]
                (format "Spells %d.\n" sp))))
       (if-let [ab (:class-abilities c)]
         (format "You have the class ability \"%s\".\n" (strings (first ab))))
       (format "Your attack bonus is %d.\n" (c :attack-bonus))
       (format "You have %d gold." (c :money))))

(defn- make-plain-text-output-speech
  [text]
  (let [output (new PlainTextOutputSpeech)]
    (.setText output text)
    output))

(defn speechlet-onSessionStarted [this request session]
  (log/debug "Speechlet onSessionStart"))

(defn speechlet-onLaunch [this request session]
  (log/debug "Speechlet onLaunch"))

(defn speechlet-onIntent [this request session]
  (let [intent (.getIntent request)
        i-name (.getName intent)]
    (log/info (apply str (interpose "" ["Speechlet.onIntent" intent i-name]))))
  (-> make-character
      format-character
      make-plain-text-output-speech
      SpeechletResponse/newTellResponse))

(defn speechlet-onSessionEnded [this request session]
  (log/debug "Speechlet onSessionEnded"))

(let [c (make-character)]
  (prn c)
  (prn (format-character c)))
