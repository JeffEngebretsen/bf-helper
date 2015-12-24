(ns bf-helper.alexa.routes.create-character-router
  (:require [bf-helper.alexa.router :refer :all]
            [bf-helper.character-generator :as c-gen]
            [bf-helper.alexa.util.formatter :as formatter]
            [clojure.data.generators :as gen])
  (:import (com.amazon.speech.ui PlainTextOutputSpeech SimpleCard)
           (com.amazon.speech.speechlet SpeechletResponse)))

(defn- bundle-character
  [c]
  (let [speech (doto (new PlainTextOutputSpeech)
                  (.setText (format "I rolled up Your %s. I sent you a card with the stats." (formatter/character-race-class c))))
        card   (doto (new SimpleCard)
                  (.setTitle "Your character:")
                  (.setContent (formatter/character c)))]
    (SpeechletResponse/newTellResponse speech card)))

(defmethod route "CreateCharacterIntent"
  [_ _]
  (binding [gen/*rnd* (java.util.Random.)]
    (let [character (c-gen/make-character)]
      (bundle-character character))))
