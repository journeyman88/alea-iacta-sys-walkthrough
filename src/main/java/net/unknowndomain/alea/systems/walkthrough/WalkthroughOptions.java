/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.walkthrough;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.systems.RpgSystemOptions;
import net.unknowndomain.alea.systems.annotations.RpgSystemData;
import net.unknowndomain.alea.systems.annotations.RpgSystemOption;


/**
 *
 * @author journeyman
 */
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.walkthrough.RpgSystemBundle")
public class WalkthroughOptions extends RpgSystemOptions
{
    @RpgSystemOption(name = "feature-level", shortcode = "f", description = "walkthrough.options.feature-level", argName = "featureLevel")
    private Integer featureLevel;
    @RpgSystemOption(name = "add-medals", shortcode = "m", description = "walkthrough.options.add-medals", argName = "numberOfMedals")
    private Integer medalsSpent;
    @RpgSystemOption(name = "negative-attribute", shortcode = "d", description = "walkthrough.options.negative-attribute")
    private boolean negativeAttribute;
    @RpgSystemOption(name = "no-skill", shortcode = "n", description = "walkthrough.options.skill.none")
    private boolean skillLevelNone;
    @RpgSystemOption(name = "bronze-level", shortcode = "b", description = "walkthrough.options.skill.bronze")
    private boolean skillLevelBronze;
    @RpgSystemOption(name = "silver-level", shortcode = "s", description = "walkthrough.options.skill.silver")
    private boolean skillLevelSilver;
    @RpgSystemOption(name = "gold-level", shortcode = "g", description = "walkthrough.options.skill.gold")
    private boolean skillLevelGold;
    
    @Override
    public boolean isValid()
    {
        return !(isHelp() || (featureLevel == null) || !(isSkillLevelNone() ^ isSkillLevelBronze() ^ isSkillLevelSilver() ^ isSkillLevelGold()));
    }

    public Integer getFeatureLevel()
    {
        return featureLevel;
    }

    public Integer getMedalsSpent()
    {
        return medalsSpent;
    }
    
    public boolean isNegativeAttribute()
    {
        return negativeAttribute;
    }

    public Collection<WalkthroughModifiers> getModifiers()
    {
        Set<WalkthroughModifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(WalkthroughModifiers.VERBOSE);
        }
        if (isNegativeAttribute())
        {
            mods.add(WalkthroughModifiers.NEGATIVE_ATTRIBUTE);
        }
        if (isSkillLevelNone())
        {
            mods.add(WalkthroughModifiers.SKILL_LEVEL_NONE);
        }
        if (isSkillLevelBronze())
        {
            mods.add(WalkthroughModifiers.SKILL_LEVEL_BRONZE);
        }
        if (isSkillLevelSilver())
        {
            mods.add(WalkthroughModifiers.SKILL_LEVEL_SILVER);
        }
        if (isSkillLevelGold())
        {
            mods.add(WalkthroughModifiers.SKILL_LEVEL_GOLD);
        }
        return mods;
    }

    public boolean isSkillLevelNone()
    {
        return skillLevelNone;
    }

    public boolean isSkillLevelBronze()
    {
        return skillLevelBronze;
    }

    public boolean isSkillLevelSilver()
    {
        return skillLevelSilver;
    }

    public boolean isSkillLevelGold()
    {
        return skillLevelGold;
    }
    
}