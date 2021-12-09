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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.roll.LocalizedResult;

/**
 *
 * @author journeyman
 */
public class WalkthroughResults extends LocalizedResult
{
    private static final String BUNDLE_NAME = "net.unknowndomain.alea.systems.walkthrough.RpgSystemBundle";
    
    private final List<SingleResult<Integer>> diceResults;
    private final Integer totalResult;
    
    public WalkthroughResults(List<SingleResult<Integer>> diceResults, Integer totalResult)
    {
        List<SingleResult<Integer>> tmp = new ArrayList<>(diceResults.size());
        tmp.addAll(diceResults);
        this.diceResults = Collections.unmodifiableList(tmp);
        this.totalResult = totalResult;
    }
    
    public List<SingleResult<Integer>> getDiceResults()
    {
        return diceResults;
    }
    
    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        String indent = getIndent(indentValue);
        messageBuilder.append(translate("walkthrough.results.total", totalResult));
        messageBuilder.appendNewLine();
        if (verbose)
        {
            messageBuilder.append(indent).append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append(translate("walkthrough.results.diceResults")).append(" [ ");
            for (SingleResult<Integer> t : getDiceResults())
            {
                messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                messageBuilder.append(t.getValue()).append(") ");
            }
            messageBuilder.append("]").appendNewLine();
        }
    }


    @Override
    protected String getBundleName()
    {
        return BUNDLE_NAME;
    }
    
}
