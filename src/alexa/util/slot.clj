(ns alexa.util.slot
  (:refer-clojure :rename {get core-get})
  (:require [clojure.string :as str]))

(defn str->keyword
  "Convert a slot string to a keyword by calling toLowerCase and replacing spaces with dashes."
  [s]
  (-> s
      (.toLowerCase)
      (str/replace #" " "-")
      (keyword)))
