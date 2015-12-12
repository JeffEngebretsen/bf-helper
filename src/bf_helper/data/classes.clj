(def classes
  {:cleric {:requirement [:wis >= 9]
            :hit-die 6
            :weapons [:blunt]
            :armor [:any :shield]
            :special-abilities [:turn-undead]}

   :fighter {:requirement [:str >= 9]
             :hit-die 8
             :weapons :any
             :armor [:any :shield]}

   :magic-user {:requirement [:int >= 9]
                :hit-die 4
                :weapons [:cudgel :dagger :walking-stalf]
                :armor [:none]}

   :thief {:requirement [:dex >= 9]
           :hit-die 4
           :weapons [:any]
           :armor [:leather]
           :special-abilities [:sneak-attack]}})

