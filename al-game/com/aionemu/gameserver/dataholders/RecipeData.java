package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
import com.aionemu.gameserver.skillengine.model.learn.SkillRace;
import gnu.trove.TIntObjectHashMap;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "recipe_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecipeData {
  @XmlElement(name = "recipe_template")
  protected List<RecipeTemplate> list;
  private TIntObjectHashMap<RecipeTemplate> recipeData;
  private final TIntObjectHashMap<ArrayList<RecipeTemplate>> learnTemplates = new TIntObjectHashMap();

  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.recipeData = new TIntObjectHashMap();
    for (RecipeTemplate it : this.list) {

      this.recipeData.put(it.getId().intValue(), it);
      if (it.getAutolearn().intValue() == 0)
        continue;
      addTemplate(it);
    }
    this.list = null;
  }

  public RecipeTemplate getRecipeTemplateById(int id) {
    return (RecipeTemplate) this.recipeData.get(id);
  }

  private void addTemplate(RecipeTemplate template) {
    SkillRace race = template.getRace();
    if (race == null) {
      race = SkillRace.ALL;
    }
    int hash = makeHash(race.ordinal(), template.getSkillid().intValue(), template.getSkillpoint().intValue());
    ArrayList<RecipeTemplate> value = (ArrayList<RecipeTemplate>) this.learnTemplates.get(hash);
    if (value == null) {

      value = new ArrayList<RecipeTemplate>();
      this.learnTemplates.put(hash, value);
    }

    value.add(template);
  }

  public RecipeTemplate[] getRecipeIdFor(Race race, int skillId, int skillPoint) {
    List<RecipeTemplate> newRecipes = new ArrayList<RecipeTemplate>();
    List<RecipeTemplate> raceSpecificTemplates = (List<RecipeTemplate>) this.learnTemplates
        .get(makeHash(race.ordinal(), skillId, skillPoint));

    List<RecipeTemplate> allRaceSpecificTemplates = (List<RecipeTemplate>) this.learnTemplates
        .get(makeHash(SkillRace.ALL.ordinal(), skillId, skillPoint));

    if (raceSpecificTemplates != null)
      newRecipes.addAll(raceSpecificTemplates);
    if (allRaceSpecificTemplates != null) {
      newRecipes.addAll(allRaceSpecificTemplates);
    }
    return newRecipes.<RecipeTemplate>toArray(new RecipeTemplate[newRecipes.size()]);
  }

  public int size() {
    return this.recipeData.size();
  }

  private static int makeHash(int race, int skillId, int skillLevel) {
    int result = race << 8;
    result = (result | skillId) << 8;
    return result | skillLevel;
  }
}
