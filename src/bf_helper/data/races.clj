(def races
  {:dwarf {:requirement [[:con >= 9]
                         [:cha <= 17]]
           :classes [:cleric :fighter :thief]
           :hit-die :any
           :saving-throws {:death-ray 4
                            :magic-wands 4
                            :paralysis 4
                            :dragon-breath 3
                            :spells 4}
           :languages [:common :dwarvish]}

   :halfling {:requirement [[:dex >= 9]
                            [:str <= 17]]
              :classes [:cleric :fighter :thief]
              :hit-die 6
              :saving-throws {:death-ray 4
                               :magic-wands 4
                               :paralysis 4
                               :dragon-breath 3
                               :spells 4}
              :languages [:common :halfling]}

   :elf {:requirement [[:int >= 9]
                       [:con <= 17]]
         :classes [:cleric :fighter :thief :magic-user :fighter+magic-user :thief+magic-user]
         :hit-die 6
         :saving-throws {:magic-wands 4
                          :paralysis 4
                          :spells 4}
         :languages [:common :elvish]}

   :human {:classes     [:cleric :fighter :thief :magic-user]
           :hit-die :any
           :languages [:common]}})
