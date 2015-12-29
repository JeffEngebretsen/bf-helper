(ns bf-helper.alexa.routes.lookup-spell-router
  (:require [bf-helper.alexa.router :refer :all]
            [bf-helper.alexa.util.slot :as slot]
            [bf-helper.core :refer [spells]]
            [bf-helper.alexa.util.formatter :as formatter]
            [clojure.tools.logging :as log])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech SimpleCard)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defmethod route "LookupSpellIntent"
  [_ slots]
  (let [spell-key (slot/get slots "Spell")]
    (if-let [spell (spells (slot/str->keyword spell-key))]
      (SpeechletResponse/newTellResponse
       (doto (new PlainTextOutputSpeech)
         (.setText (format "%s ... %s" (:name spell) (:description spell))))
       (doto (new SimpleCard)
         (.setTitle (:name spell))
         (.setContent (str (format "Range: %s\nDuration: %s\n" (formatter/range (:range spell)) (:duration spell))
                        (if-let [magic-user-lvl (:magic-user-level spell)]
                          (format "Magic-User %s\n" magic-user-lvl))
                        (if-let [cleric-lvl (:cleric-level spell)]
                          (format "Cleric %s\n" cleric-lvl))
                        (:description spell)))))
      (SpeechletResponse/newTellResponse
       (doto (new PlainTextOutputSpeech)
         (.setText (format "I'm still learning this gaming system. Try asking about the spell %s again later."
                           spell-key)))))))
