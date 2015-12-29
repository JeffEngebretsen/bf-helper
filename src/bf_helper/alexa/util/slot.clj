(ns bf-helper.alexa.util.slot
  (:refer-clojure :rename {get core-get})
  (:require [clojure.string :as str]))

(defn get
  "Get a slot form a map of slots."
  [slots slot]
  (-> slots
      (core-get slot)
      (.getValue)))

(defn str->keyword
  "Convert a slot string to a keyword by calling toLowerCase and replacing spaces with dashes."
  [s]
  (-> s
      (.toLowerCase)
      (str/replace #" " "-")
      (keyword)))
