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
import net.unknowndomain.alea.messages.MsgStyle;
import net.unknowndomain.alea.roll.GenericResult;

/**
 *
 * @author journeyman
 */
public class WalkthroughResults extends GenericResult
{
    private final List<Integer> diceResults;
    private final Integer totalResult;
    
    public WalkthroughResults(List<Integer> diceResults, Integer totalResult)
    {
        List<Integer> tmp = new ArrayList<>(diceResults.size());
        tmp.addAll(diceResults);
        this.diceResults = Collections.unmodifiableList(tmp);
        this.totalResult = totalResult;
    }
    
    public List<Integer> getDiceResults()
    {
        return diceResults;
    }
    
    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        String indent = getIndent(indentValue);
        messageBuilder.append("Result: ").append(totalResult);
        messageBuilder.appendNewLine();
        if (verbose)
        {
            messageBuilder.append(indent).append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append("Dice Results: ").append(" [ ");
            for (Integer t : getDiceResults())
            {
                messageBuilder.append(t).append(" ");
            }
            messageBuilder.append("]").appendNewLine();
        }
    }
    
}
