(ns alexa.routes.lookup-rule-router
  (:require [alexa.router :refer :all]
            [bf-helper.core :refer [rules]]
            [alexa.util.slot :as slot]))

(defmethod route "LookupRuleIntent"
  [request]
  (if-let [rule-key (get-in request [:slots :rule])]
    (if-let [rule (rules (slot/str->keyword rule-key))]
      {:text (format "%s ... %s" (:name rule) (:description rule))
       :card {:title (:name rule)
              :content (:description rule)}}
      {:text (format "I'm still learning this gaming system. Try asking about %s again later."
                           rule-key)})
    {:text "What rule do you want me to tell you about?"
     :reprompt "What rule do you want me to tell you about? For example, you can say 'running'"}))
