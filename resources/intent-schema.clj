{
  :intents [
    {
      :intent "CreateCharacterIntent"
    }
    {
      :intent "CreateRaceCharacterIntent"
      :slots [
        {
          :name "Race"
          :type "LIST_OF_RACES"
        }
      ]
    }
    {
      :intent "CreateClassCharacterIntent"
      :slots [
        {
          :name "Class"
          :type "LIST_OF_CLASSES"
        }
      ]
    }
    {
      :intent "LookupSpellIntent"
      :slots [
        {
          :name "Spell"
          :type "LIST_OF_SPELLS"
        }
      ]
    }
    {
      :intent "LookupRuleIntent"
      :slots [
        {
          :name "Rule"
          :type "LIST_OF_RULES"
        }
      ]
    }
    {
      :intent "LookupSaveIntent"
      :slots [
        {
          :name "Save"
          :type "LIST_OF_SAVES"
        }
        {
          :name "Level"
          :type "AMAZON.NUMBER"
        }
        {
          :name "Class"
          :type "LIST_OF_CLASSES"
        }
      ]
    }
    {
      :intent "AMAZON.HelpIntent"
    }
    {
      :intent "AMAZON.StopIntent"
    }
    {
      :intent "AMAZON.CancelIntent"
    }
  ]
}
