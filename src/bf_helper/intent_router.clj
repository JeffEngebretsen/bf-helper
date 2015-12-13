(ns bf-helper.intent-router
  (:require [clojure.tools.logging :as log]
            [bf-helper.gen-character :as c-gen]
            [bf-helper.formatter :as formatter]
            [clojure.data.generators :as gen])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech SimpleCard)
           (com.amazon.speech.speechlet SpeechletResponse)))

(def character-created-text "")

(def slot->keyword {"Race" :race
                    "Class" :class})

(def val->keyword {:race
                   {"dwarf" :dwarf
                    "halfling" :halfling
                    "elf" :elf
                    "human" :human}
                   :class
                   {"cleric" :cleric
                    "fighter" :fighter
                    "magic user" :magic-user
                    "thief" :thief}})

(defn make-plain-text-output-speech
  [text]
  (doto (new PlainTextOutputSpeech)
    (.setText text)))

(defn make-simple-card
  [title content]
  (doto (new SimpleCard)
    (.setTitle title)
    (.setContent content)))

(defn bundle-character
  [c]
  (let [speech (make-plain-text-output-speech
                (format "I rolled up %s. I sent you a card with the stats." (formatter/character-race-class c)))
        card (make-simple-card
              "Your character:"
              (formatter/character c))]
    (SpeechletResponse/newTellResponse speech card)))

(defn create-character
  [_]
  (binding [gen/*rnd* (java.util.Random.)]
    (-> (c-gen/make-character)
        (bundle-character))))

(defn create-race-character
  [{race :race}]
  (binding [gen/*rnd* (java.util.Random.)]
    (-> race
        (c-gen/make-character-race)
        (bundle-character))))

(defn create-class-character
  [{clazz :class}]
  (binding [gen/*rnd* (java.util.Random.)]
    (-> clazz
        (c-gen/make-character-class)
        (bundle-character))))

(def intent->f {"CreateCharacterIntent" create-character
                "CreateRaceCharacterIntent" create-race-character
                "CreateClassCharacterIntent" create-class-character})

(defn slots->map
  [slots]
  (into {} (map
            #(let [slot (val %)
                   k (slot->keyword (.getName slot))
                   v (get-in val->keyword [k (.toLowerCase (.getValue slot))])]
               (vec [k v])) slots)))

(defn route
  [i-name i-slots]
  (let [f (intent->f i-name)
        slots (slots->map i-slots)]
    (log/info (format "Intent: %s Slots: %s" i-name slots))
    (f slots)))
