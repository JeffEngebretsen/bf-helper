(ns bf-helper.alexa.util.slot
  (:refer-clojure :rename {get core-get}))

(defn get
  "Get a slot form a map of slots."
  [slots slot]
  (-> slots
      (core-get slot)
      (.getValue)))
