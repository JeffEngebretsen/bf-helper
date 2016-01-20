(ns alexa.routes.lookup-save-router
  (:require [alexa.router :refer :all]))

(defmethod route "LookupSaveIntent"
  [_]
  {:text "I'm still learning this gaming system. Try asking about saves again later." })
