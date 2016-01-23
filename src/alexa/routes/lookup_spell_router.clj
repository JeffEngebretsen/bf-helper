(ns alexa.routes.lookup-spell-router
  (:require [alexa.router :refer :all]
            [alexa.util.slot :as slot]
            [bf-helper.core :refer [spells]]
            [alexa.util.formatter :as formatter]
            [clojure.tools.logging :as log]))

(defn make-card [spell]
  (str (format "Range: %s\nDuration: %s\n" (formatter/range (:range spell)) (:duration spell))
       (if-let [magic-user-lvl (:magic-user-level spell)]
         (format "Magic-User %s\n" magic-user-lvl))
       (if-let [cleric-lvl (:cleric-level spell)]
         (format "Cleric %s\n" cleric-lvl))
       (:description spell)))

(defmethod route "LookupSpellIntent"
  [request]
  (if-let [spell-key (get-in request [:slots :spell])]
    (if-let [spell (spells (slot/str->keyword spell-key))]
      {:text (format "%s ... %s" (:name spell) (:description spell))
       :card {:title (:name spell)
              :content (make-card spell)}}
      {:text (format "I'm still learning this gaming system. Try asking about the spell %s again later."
                           spell-key)})
    {:text "What spell would you like me to look up?"
     :reprompt "Whate spell would you like me to look up? For example you can ask for 'charm person'."}))
