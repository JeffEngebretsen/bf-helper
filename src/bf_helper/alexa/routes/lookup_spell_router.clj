(ns bf-helper.alexa.routes.lookup-spell-router
  (:require [bf-helper.alexa.router :refer :all]
            [bf-helper.alexa.util.slot :as slot]
            [bf-helper.core :refer [spells]]
            [clojure.tools.logging :as log])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech SimpleCard)
           (com.amazon.speech.speechlet SpeechletResponse)))

(def str->spell {"charm person" :charm-person
                 "detect magic" :detect-magic
                 "floating disk" :floating-disk
                 "hold portal" :hold-portal
                 "light" :light
                 "dark" :light
                 "magic missle" :magic-missle
                 "magic mouth" :magic-mouth
                 "protect from evil" :protect-from-evil
                 "protect from good" :protect-from-evil
                 "read languages" :read-languages
                 "read magic" :read-magic
                 "shield" :shield
                 "sleep" :sleep
                 "ventriloquism" :ventriloquism})

(defmethod route "LookupSpellIntent"
  [_ slots]
  (let [spell-key (slot/get slots "Spell")]
    (if-let [spell (spells (str->spell spell-key))]
      (SpeechletResponse/newTellResponse
       (doto (new PlainTextOutputSpeech)
         (.setText (format "%s %s" (:name spell) (:description spell))))
       (doto (new SimpleCard)
         (.setTitle (:name spell))
         (.setContent (str (format "Range: %s\nDuration: %s\n" (:range spell) (:duration spell))
                        (if-let [magic-user-lvl (:magic-user-level spell)]
                          (format "Magic-User %s\n" magic-user-lvl))
                        (if-let [cleric-lvl (:cleric-level spell)]
                          (format "Cleric %s\n" cleric-lvl))
                        (:description spell)))))
      (SpeechletResponse/newTellResponse
       (doto (new PlainTextOutputSpeech)
         (.setText (format "I'm still learning this gaming system. Try asking about the spell %s again later."
                           spell-key)))))))
