(ns alexa.routes.create-class-character-router
  (:require [alexa.router :refer :all]
            [bf-helper.character-generator :as c-gen]
            [alexa.util.formatter :as formatter]
            [alexa.util.slot :as slot]
            [clojure.data.generators :as gen]
            [clojure.tools.logging :as log])
  (:import (java.util Random)))

(defmethod route "CreateClassCharacterIntent"
  [request]
  (binding [gen/*rnd* (Random.)]
    (if-let [clazz (get-in request [:slots :class])]
      (if-let [character (c-gen/make-character-class (slot/str->keyword clazz))]
        {:text (format "I rolled up your %s. I sent you a card with the stats."
                       (formatter/character-class character))
         :card {:title "Your character:"
                :content (formatter/character character)}}
        {:text (format "I'm sorry, I don't know how to make %s %s."
                       (formatter/article clazz)
                       clazz)})
      {:text "What class do you want?"
       :reprompt "What class do you want? For example you can say 'cleric'."})))
