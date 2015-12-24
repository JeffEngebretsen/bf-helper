(ns bf-helper.alexa.router)

(defmulti route
  (fn [i-name i-slots] i-name))
