/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.walkthrough;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.unknowndomain.alea.dice.standard.D12;
import net.unknowndomain.alea.pools.DicePool;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class WalkthroughRoll implements GenericRoll
{
    
    private final DicePool<D12> actionPool;
    private final int feature;
    private final int favorLevel;
    private final Set<WalkthroughModifiers> mods;
    
    public WalkthroughRoll(Integer feature, Integer medals, WalkthroughModifiers ... mod)
    {
        this(feature, medals, Arrays.asList(mod));
    }
    
    public WalkthroughRoll(Integer feature, Integer medals, Collection<WalkthroughModifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        int favorLevel = medals;
        if (mods.contains(WalkthroughModifiers.SKILL_LEVEL_NONE))
        {
            favorLevel = -1;
        }
        else
        {
            if (mods.contains(WalkthroughModifiers.SKILL_LEVEL_SILVER) || mods.contains(WalkthroughModifiers.SKILL_LEVEL_GOLD))
            {
                favorLevel += 1;
            }
        }
        if (mods.contains(WalkthroughModifiers.NEGATIVE_ATTRIBUTE))
        {
            favorLevel += -1;
        }
        int dice = 3;
        if (favorLevel == 0)
        {
            dice = 2;
        }
        this.actionPool = new DicePool<>(D12.INSTANCE, dice);
        this.feature = feature;
        this.favorLevel = favorLevel;
    }
    
    @Override
    public GenericResult getResult()
    {
        List<Integer> actionRes = this.actionPool.getResults();
        WalkthroughResults results = buildResults(actionRes);
        results.setVerbose(mods.contains(WalkthroughModifiers.VERBOSE));
        return results;
    }
    
    private WalkthroughResults buildResults(List<Integer> actionRes)
    {
        int mod = favorLevel -1;
        int o = -1;
        if (favorLevel < 0)
        {
            o = 1;
            mod = favorLevel +1 ;
        }
        final int ord = o;
        actionRes.sort((Integer o1, Integer o2) ->
        {
            return ord * o1.compareTo(o2);
        });
        Integer res = feature + actionRes.get(0) + actionRes.get(1) + mod;
        if (mods.contains(WalkthroughModifiers.SKILL_LEVEL_GOLD))
        {
            res += 2;
        }
        WalkthroughResults results = new WalkthroughResults(actionRes, res);
        return results;
    }
}
