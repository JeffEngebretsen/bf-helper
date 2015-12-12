(ns bf-helper.dice
  (:require [clojure.data.generators :as gen]))

(defn roll
  "Roll an s sided die n times."
  [n s]
  (reduce + (take n (repeatedly #(+ 1 (gen/uniform 0 s))))))
